package hu.okosotthon.back.Mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MqttConnectionService {

    private final MqttClient mqttClient;
    private final MqttCallbackHandler callbackHandler;

    private final String broker = "tcp://192.168.0.28:1883";
    //A ClientId az lehetne aki ben van jelentkezve
//    private final String clientId = "SpringBootSubscriber";
//    private MqttClient mqttClient;

    @Autowired
    public MqttConnectionService(MqttCallbackHandler callbackHandler, List<MqttMessageListener> listeners) throws MqttException {
        this.callbackHandler = callbackHandler;
        this.mqttClient = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());

        mqttClient.setCallback(callbackHandler);
        mqttClient.connect();

        callbackHandler.setMqttClient(mqttClient);
        listeners.forEach(callbackHandler::addListener);
    }

    public MqttClient getClient() {
        return mqttClient;
    }

}
