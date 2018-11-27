package com.spring.integration.config;

import com.spring.integration.constants.JMSConstant;
import com.spring.integration.listener.MessageDelegate;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Queue;

//To enable support for @JmsListener annotations, you can add @EnableJms to one of your @Configuration classes
//@EnableJms

@Configuration
@ComponentScan("com.spring.integration")
public class JMSConfiguration {

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private MessageListener jmsMessageListener;

    @Autowired
    private SessionAwareMessageListener jmsSessonAwareMessageListener;

    @Autowired
    private MessageDelegate messageDelegate;


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
    public MessageListenerAdapter messageListenerAdapter(){
        MessageListenerAdapter messageListenerAdapter=new MessageListenerAdapter();
        messageListenerAdapter.setDelegate(messageDelegate);
        return messageListenerAdapter;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(){
        DefaultMessageListenerContainer container=new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestination(queue());

        //set message listener
        //container.setMessageListener(jmsMessageListener);

        //set spring seasion aware message listener
        //container.setMessageListener(jmsSessonAwareMessageListener);

        //set message listner adapter
        container.setMessageListener(messageListenerAdapter());

        container.setMessageConverter(messageConverter);

        //enable local active session resource transactions
        //container.setSessionTransacted(true);

        container.initialize();
        container.start();
        return container;
    }


    //for supporting annotated jms listener infrastructure
    /*@Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setDestinationResolver(destinationResolver());
        factory.setSessionTransacted(true);
        factory.setConcurrency("3-10");
        return factory;
    }*/
}
