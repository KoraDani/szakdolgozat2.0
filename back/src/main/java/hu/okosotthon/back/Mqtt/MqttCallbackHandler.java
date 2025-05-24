package hu.okosotthon.back.Mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MqttCallbackHandler implements MqttCallback {

    private final List<MqttMessageListener> listeners = new ArrayList<>();
    private MqttClient mqttClient; // just a reference, not the service


    public void addListener(MqttMessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("MQTT Connection lost: " + cause.getMessage());

        new Thread(() -> {
            while (!mqttClient.isConnected()) {
                try {
                    System.out.println("Attempting to reconnect to MQTT broker...");
                        mqttClient.reconnect();
                    System.out.println("Reconnected to MQTT broker.");
                } catch (Exception e) {
                    System.out.println("Reconnect failed, retrying in 5 seconds: " + e.getMessage());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        for (MqttMessageListener listener : listeners) {
            listener.handleMessage(topic, message);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }
}
