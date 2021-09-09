package com.foodgrid.common.event.configuration;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

@Configuration
@EnableJms
public class EventConfiguration {

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory factory
                = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);

        return factory;
    }

    @Value("${event.user.address}")
    private String addressTopic;

    @Value("${event.accounts.bill}")
    private String billTopic;

    @Value("${event.restaurant.menu}")
    private String menuTopic;

    @Value("${event.authentication}")
    private String authenticationTopic;

    @Value("${event.user.cart}")
    private String cartTopic;

    @Bean
    @Qualifier("addressTopic")
    public Topic addressTopic() {
        return new ActiveMQTopic(addressTopic);
    }

    @Bean
    @Qualifier("authenticationTopic")
    public Topic authenticationTopic() {
        return new ActiveMQTopic(authenticationTopic);
    }

    @Bean
    @Qualifier("menuTopic")
    public Topic menuTopic() {
        return new ActiveMQTopic(menuTopic);
    }

    @Bean
    @Qualifier("cartTopic")
    public Topic cartTopic() {
        return new ActiveMQTopic(cartTopic);
    }

    @Bean
    @Qualifier("billTopic")
    public Topic billTopic() {
        return new ActiveMQTopic(billTopic);
    }
}
