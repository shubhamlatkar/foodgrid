package com.foodgrid.restaurant.query.internal.event.broker;

import com.foodgrid.restaurant.query.internal.event.handler.MenuEventHandler;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EventBroker {

    private final MenuEventHandler menuEventHandler;

    @Autowired
    public EventBroker(MenuEventHandler menuEventHandler) {
        this.menuEventHandler = menuEventHandler;
    }

    @JmsListener(destination = "${event.restaurant.menu}")
    public void menuEventBroker(MenuEventDTO menu) {
        menuEventHandler.consumeMenuEvent(menu);
    }
}
