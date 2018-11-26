package com.spring.integration.listener;

import com.spring.integration.model.Person;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class JmsMessageListener implements MessageListener {

    @Override
    public synchronized void onMessage(Message message) {

        try {
            if (message instanceof TextMessage) {
                String msg = ((TextMessage) message).getText();
                System.out.println("MESSAGE RECEIVED :: " + msg);
            } else if (message instanceof MapMessage) {
                Person person = new Person(((MapMessage) message).getString("name"),((MapMessage) message).getInt("age"));
                System.out.println("MESSAGE RECEIVED :: " + person);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
