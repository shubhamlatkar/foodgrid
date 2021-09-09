package com.foodgrid.restaurant.query;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.restaurant.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.restaurant.query.internal.event.broker.EventBroker;
import com.foodgrid.restaurant.query.internal.event.handler.MenuEventHandler;
import com.foodgrid.restaurant.shared.payload.MenuEventDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {EventBroker.class})
@AutoConfigureWebTestClient
class EventBrokerTests {

    @MockBean
    private MenuEventHandler menuEventHandler;

    @MockBean
    private UserSession userSession;

    @Autowired
    private EventBroker eventBroker;

    @Test
    @DisplayName("Tests addressBroker method of EventBroker")
    void addressBroker() {
        when(userSession.getUserId()).thenReturn("1");
        var itemRequest = new ItemRequest("testName", 12.23, "testIngredients", "testComments", 9, 14, 19, 23);
        var menuEvent = new MenuEventDTO(itemRequest, "1", "1", "1", 4.3f, CrudActions.ADD);
        eventBroker.menuEventBroker(menuEvent);
        Assertions.assertNotNull(userSession.getUserId());
    }
}
