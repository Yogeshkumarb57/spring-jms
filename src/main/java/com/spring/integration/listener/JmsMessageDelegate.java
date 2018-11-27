package com.spring.integration.listener;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class JmsMessageDelegate implements MessageDelegate {
    @Override
    public void handleMessage(String message) {
        System.out.println("JmsMessageDelegate.String_Message --> MESSAGE RECEIVED :: " + message);
    }

    @Override
    public void handleMessage(Map message) {
        System.out.println("JmsMessageDelegate.Map_Message --> MESSAGE RECEIVED :: " + message);
    }

    @Override
    public void handleMessage(byte[] message) {
        System.out.println("JmsMessageDelegate.Byte_Message --> MESSAGE RECEIVED :: " + message);
    }

    @Override
    public void handleMessage(Serializable message) {
        System.out.println("JmsMessageDelegate.Serializable_Message --> MESSAGE RECEIVED :: " + message);
    }
}
