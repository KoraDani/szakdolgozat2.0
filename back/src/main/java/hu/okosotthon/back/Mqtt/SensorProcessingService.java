package hu.okosotthon.back.Mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.okosotthon.back.Device.DeviceService;
import hu.okosotthon.back.Device.Devices;
import hu.okosotthon.back.Measurment.Measurement;
import hu.okosotthon.back.Measurment.MeasurementService;
import hu.okosotthon.back.Sensor.Sensor;
import hu.okosotthon.back.Sensor.SensorService;
import hu.okosotthon.back.scheduleTask.ScheduleTaskDTO;
import hu.okosotthon.back.scheduleTask.ScheduleTaskService;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommand;
import hu.okosotthon.back.tasmotaCommand.TasmotaCommandService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SensorProcessingService implements MqttMessageListener {

    private final SensorService sensorService;
    private final MeasurementService measurementService;
    private final DeviceService deviceService;
    private final ScheduleTaskService scheduleTaskService;
    private final TasmotaCommandService tasmotaCommandService;
    private final MqttMessagingService mqttMessagingService;

    List<ScheduleTaskDTO> scheduleTask = new ArrayList<>();


    @Autowired
    public SensorProcessingService(SensorService sensorService, MeasurementService measurementService, DeviceService deviceService, ScheduleTaskService scheduleTaskService, TasmotaCommandService tasmotaCommandService, MqttMessagingService mqttMessagingService) {
        this.sensorService = sensorService;
        this.measurementService = measurementService;
        this.deviceService = deviceService;
        this.scheduleTaskService = scheduleTaskService;
        this.tasmotaCommandService = tasmotaCommandService;
        this.mqttMessagingService = mqttMessagingService;
    }

    @Override
    public void handleMessage(String topic, MqttMessage message) {
        this.scheduleTask = this.scheduleTaskService.getAllScheduleForCondition();
        if (topic.startsWith("tele/")) {
            List<Sensor> sensorList = this.sensorService.getAll();
            String tmp = topic.replace("tele/", "").replace("/SENSOR", "");
            Devices d = this.deviceService.getDeviceByTopic(tmp);
            try {
                HashMap<Object, Object> result = new ObjectMapper().readValue(message.getPayload(), HashMap.class);
                for (Map.Entry<Object, Object> val : result.entrySet()) {
                    for (Sensor s : sensorList) {
                        //TODO ki kell próbálni a Measurment savet
                        if (s.getSensorName().equals(val.getKey())) {
                            System.out.println("GSON " + val.getValue());
                            LinkedHashMap<Object, Object> sensor = (LinkedHashMap<Object, Object>) val.getValue();
                            for (Map.Entry<Object, Object> senVal : sensor.entrySet()) {
                                this.measurementService.save(
                                        new Measurement(
                                                senVal.getKey().toString(),
                                                senVal.getValue().toString(),
                                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString(),
                                                d
                                        ));
                                checkCondition(senVal.getKey().toString() + " " + senVal.getValue().toString());
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void checkCondition(String status) {

        this.scheduleTask.forEach(task -> {
            Devices targetDevice = this.deviceService.getDeviceById(task.getTargetDeviceId());

            TasmotaCommand tasmotaCommand = this.tasmotaCommandService.getCommandById(task.getCommand_id());
            String[] tmp = status.split(" ");
//            System.out.println(tmp[0] + " " + tmp[1]);
//            System.out.println(task.getCondition());
            switch (task.getCondition()) {
                case "lt":
                    if (tmp[0].equals(task.getWhichValue()) && Float.parseFloat(tmp[1]) < task.getWhen()) {
                        this.mqttMessagingService.publish("cmnd/" + targetDevice.getTopic() + "/" + tasmotaCommand.getCommand(), tasmotaCommand.getArgument());
                        System.out.println("LT message send");
                    }
                    break;
                case "gt":
                    if (tmp[0].equals(task.getWhichValue()) && Float.parseFloat(tmp[1]) > task.getWhen()) {
                        this.mqttMessagingService.publish("cmnd/" + targetDevice.getTopic() + "/" + tasmotaCommand.getCommand(), tasmotaCommand.getArgument());
                        System.out.println("GT message send");
                    }
                    break;
                case "eq":
                    if (tmp[0].equals(task.getWhichValue()) && Float.parseFloat(tmp[1]) == task.getWhen()) {
                        this.mqttMessagingService.publish("cmnd/" + targetDevice.getTopic() + "/" + tasmotaCommand.getCommand(), tasmotaCommand.getArgument());
                        System.out.println("EQ message send");
                    }
                    break;
            }
        });

    }
}
