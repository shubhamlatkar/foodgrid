package com.foodgrid.user.command;

import com.foodgrid.common.event.outbound.AuthenticationEvent;
import com.foodgrid.common.event.service.AuthenticationEventHandlerImplementation;
import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.user.command.external.event.handler.AuthenticationEventHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthenticationEventHandler.class})
@AutoConfigureWebTestClient
class AuthenticationEventHandlerTests {
    @MockBean
    private AuthenticationEventHandlerImplementation authenticationEventHandlerImplementation;

    @MockBean
    private UserSession userSession;

    @Autowired
    private AuthenticationEventHandler authenticationEventHandler;

    @Test
    @DisplayName("Tests authConsumer method of AuthenticationEventHandler")
    void authConsumer() {
        when(userSession.getUserId()).thenReturn("1");
        var event = new AuthenticationEvent(true, List.of(new UserAuthEventDTO(UserTypes.RESTAURANT, "1", "test", "test", UserActivities.LOGIN, "test", "RESTAURANT", "1234567890", "test@test.com")));
        doAnswer(invocationOnMock -> null)
                .when(authenticationEventHandlerImplementation).authConsumer(any());
        authenticationEventHandler.authConsumer(event);
        Assertions.assertNotNull(userSession.getUserId());
    }

}
