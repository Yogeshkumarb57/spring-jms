package com.spring.integration.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

//no need to create separate message listener, container, registering listener with container.
//@JmsListener creates message listener, container, registering listener with container automatically.

@Component
public class AnnotatedJmsMessageListener {

    @JmsListener(destination = "queue")
    public void handleMessage(String message) {
        System.out.println("JmsMessageDelegate.String_Message --> MESSAGE RECEIVED :: " + message);
    }

    @JmsListener(destination = "queue")
    public void handleMessage(Map message) {
        System.out.println("JmsMessageDelegate.Map_Message --> MESSAGE RECEIVED :: " + message);
    }

    @JmsListener(destination = "queue")
    public void handleMessage(byte[] message) {
        System.out.println("JmsMessageDelegate.Byte_Message --> MESSAGE RECEIVED :: " + message);
    }

    @JmsListener(destination = "queue")
    public void handleMessage(Serializable message) {
        System.out.println("JmsMessageDelegate.Serializable_Message --> MESSAGE RECEIVED :: " + message);
    }
}
