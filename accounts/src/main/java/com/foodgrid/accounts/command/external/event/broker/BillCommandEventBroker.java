package com.foodgrid.accounts.command.external.event.broker;

import com.foodgrid.accounts.shared.payload.BillEventDTO;
import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
@Slf4j
public class BillCommandEventBroker {

    @Autowired
    @Qualifier("billTopic")
    private Topic billTopic;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void publish(BillEventDTO bill) {
        try {
            this.jmsMessagingTemplate.convertAndSend(billTopic, bill);
            log.info("Bill event: {}", bill);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}