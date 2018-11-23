package com.spring.integration.utils;

import com.spring.integration.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.TextMessage;

@Component
public class JMSReceiver {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination destination;

    public String receiveTextMessage() throws JMSException {
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        String message = textMessage.getText();
        System.out.println("Receiver receives :: "+message);
        textMessage.acknowledge();
        return message;
    }

    public Person receivePersonMessage() throws JMSException {
        Person person=(Person) jmsTemplate.receiveAndConvert(destination);
        System.out.println("Receiver receives :: "+person);
        return person;
    }
}