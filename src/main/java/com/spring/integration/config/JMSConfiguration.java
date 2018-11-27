package com.spring.integration.config;

import com.spring.integration.constants.JMSConstant;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Queue;

@Configuration
@ComponentScan("com.spring.integration")
public class JMSConfiguration {

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private MessageListener jmsMessageListener;

    @Autowired
    private SessionAwareMessageListener jmsSessonAwareMessageListener;


    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(JMSConstant.BROKER_URL);
        return connectionFactory;
    }

    @Bean
    public Queue queue() {
        ActiveMQQueue queue = new ActiveMQQueue(JMSConstant.QUEUE_NAME);
        return queue;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setReceiveTimeout(10000);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(){
        DefaultMessageListenerContainer container=new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestination(queue());
        container.setMessageListener(jmsMessageListener);
        container.setMessageConverter(messageConverter);
        container.initialize();
        container.start();
        return container;
    }
}
