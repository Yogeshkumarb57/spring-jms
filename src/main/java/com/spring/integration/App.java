package com.spring.integration;

import com.spring.integration.config.JMSConfiguration;
import com.spring.integration.constants.JMSConstant;
import com.spring.integration.utils.JMSReceiver;
import com.spring.integration.utils.JMSSender;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URI;

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
        System.out.println("Hello JMS World!");

        try {
            jmsSender.sendMessage("Hi");
            jmsSender.sendMessage("Hi Receiver");
            jmsReceiver.receiveMessage();
            jmsReceiver.receiveMessage();
        } finally {
            broker.stop();
            context.close();
        }
    }

}
