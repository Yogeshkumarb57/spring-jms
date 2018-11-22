package com.spring.integration.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class JMSReceiver {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination destination;

    public String receiveMessage() throws JMSException {
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        String message = textMessage.getText();
        System.out.println("Receiver receives :: "+message);
        textMessage.acknowledge();
        return message;
    }
}