package com.spring.integration.convertor;

import com.spring.integration.model.Person;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

@Component
public class PersonMessageConvertor implements MessageConverter {

    @Override
    public javax.jms.Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {
        Person person = (Person) o;
        MapMessage message = session.createMapMessage();
        message.setString("name", person.getName());
        message.setInt("age", person.getAge());
        return message;
    }

    @Override
    public Object fromMessage(javax.jms.Message message) throws JMSException, MessageConversionException {
        MapMessage mapMessage = (MapMessage) message;
        Person person = new Person(mapMessage.getString("name"), mapMessage.getInt("age"));
        return person;
    }

}
