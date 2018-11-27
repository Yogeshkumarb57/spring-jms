package com.spring.integration.listener;

import com.spring.integration.model.Person;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class JmsSessonAwareMessageListener implements SessionAwareMessageListener {

    @Override
    public synchronized void onMessage(Message message, Session session) throws JMSException {
        try {

            System.out.println("SESSION ACK_MODE:: "+session.getAcknowledgeMode());
            System.out.println("SESSION MESSAGE LISTNER:: "+session.getMessageListener());
            System.out.println("SESSION TRANSACTED:: "+session.getTransacted());

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
