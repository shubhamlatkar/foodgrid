package com.foodgrid.accounts.query.internal.event.broker;

import com.foodgrid.accounts.query.internal.event.handler.BillEventHandler;
import com.foodgrid.accounts.shared.payload.BillEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class BillQueryEventBroker {

    private final BillEventHandler billEventhandler;

    @Autowired
    public BillQueryEventBroker(BillEventHandler billEventhandler) {
        this.billEventhandler = billEventhandler;
    }

    @JmsListener(destination = "${event.accounts.bill}")
    public void billEventBroker(BillEventDTO bill) {
        billEventhandler.consumeEvent(bill);
    }
}
