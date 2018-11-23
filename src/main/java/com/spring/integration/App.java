package com.spring.integration;

import com.spring.integration.config.JMSConfiguration;
import com.spring.integration.constants.JMSConstant;
import com.spring.integration.model.Person;
import com.spring.integration.utils.JMSReceiver;
import com.spring.integration.utils.JMSSender;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URI;
import java.util.HashMap;

public class App {

    private static JMSSender jmsSender;
    private static JMSReceiver jmsReceiver;
    private static AnnotationConfigApplicationContext context;
    private static BrokerService broker;

    static {
        try {
            broker = BrokerFactory.createBroker(new URI("broker:(" + JMSConstant.BROKER_URL + ")"));
            broker.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        context = new AnnotationConfigApplicationContext(JMSConfiguration.class);
        jmsSender = (JMSSender) context.getBean(JMSSender.class);
        jmsReceiver = (JMSReceiver) context.getBean(JMSReceiver.class);
    }

    public static void main(String[] args) throws Exception {

        try {
            jmsSender.sendMessage("Hi");
            jmsSender.sendMessage("Hello JMS World!");
            jmsReceiver.receiveTextMessage();
            jmsReceiver.receiveTextMessage();

            //send map message
            jmsSender.sendMessage(new Person("yogesh",23));
            jmsReceiver.receivePersonMessage();
        } finally {
            broker.stop();
            context.close();
        }
    }

}
