package com.spring.integration.utils;

import com.spring.integration.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.HashMap;
import java.util.Map;

@Component
public class JMSSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination destination;

    public void sendMessage(final Object object) {

        if (object instanceof String) {
            sendTextMessage((String) object);
        } else if (object instanceof Person) {
            sendPersonMessage((Person) object);
        }

    }

    public void sendTextMessage(final String msg) {
        System.out.println("Sender sends :: " + msg);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    public void sendPersonMessage(final Person person) {
        System.out.println("Sender sends :: " + person);
        Map map = new HashMap();
        map.put("name", person.getName());
        map.put("age", person.getAge());
        jmsTemplate.convertAndSend(destination,person);
    }
}
