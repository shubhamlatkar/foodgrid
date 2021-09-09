package com.foodgrid.user.command.external.event.handler;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.event.service.AuthenticationEventHandlerImplementation;
import com.foodgrid.common.utility.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthenticationEventHandler {

    private final AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation;

    @Autowired
    public AuthenticationEventHandler(AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation) {
        this.authenticationEventHandlerImplementation = authenticationEventHandlerImplementation;
    }

    @JmsListener(destination = "${event.authentication}")
    public void authConsumer(AuthenticationEvent event) {
        event.setUsers(
                event.getUsers()
                        .stream()
                        .filter(userAuthEventDTO -> Boolean.FALSE.equals(userAuthEventDTO.getUserType().equals(UserTypes.USER)))
                        .collect(Collectors.toList())
        );
        authenticationEventHandlerImplementation.authConsumer(event);
    }
}
