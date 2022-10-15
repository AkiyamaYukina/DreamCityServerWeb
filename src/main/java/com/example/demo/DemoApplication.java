package com.example.demo;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.Dao")
public class DemoApplication {

    public static MqttClient client;
    public static MqttConnectOptions options;

    public static void main(String[] args) {
        try {
            client = new MqttClient("tcp://blog.yukinacloud.xyz:1883", "Android", new MemoryPersistence());

            options = new MqttConnectOptions();
            options.setCleanSession(true);
            options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName("admin");
            options.setPassword("public".toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(300);
            client.connect(options);
            client.subscribe("Server/RegisterCheck");

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception
                {
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

        SpringApplication.run(DemoApplication.class, args);
    }

}
