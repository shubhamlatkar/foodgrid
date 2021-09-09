package com.foodgrid.accounts.command;

import com.foodgrid.accounts.command.external.event.broker.BillCommandEventBroker;
import com.foodgrid.accounts.command.external.payload.dto.AddressResponse;
import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.command.internal.payload.dto.request.BillRequest;
import com.foodgrid.accounts.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.accounts.shared.model.AddressDetails;
import com.foodgrid.accounts.shared.model.Cost;
import com.foodgrid.accounts.shared.model.ItemModel;
import com.foodgrid.accounts.shared.model.Location;
import com.foodgrid.accounts.shared.payload.BillEventDTO;
import com.foodgrid.accounts.shared.utility.BillActivities;
import com.foodgrid.common.security.component.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Topic;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BillCommandEventBroker.class})
@AutoConfigureWebTestClient
class BillCommandEventBrokerTests {

    @MockBean
    private JmsMessagingTemplate jmsMessagingTemplate;
    @MockBean
    private UserSession userSession;
    @MockBean
    @Qualifier("billTopic")
    private Topic billTopic;

    @Autowired
    private BillCommandEventBroker billCommandEventBroker;

    @Test
    @DisplayName("Tests sendMenuEvent method of MenuEventBroker")
    void sendMenuEvent() {
        when(userSession.getUserId()).thenReturn("1");
        var address = new AddressResponse(
                "1",
                "1",
                new Location(12.2, 12.2),
                "test",
                new AddressDetails("addressLineOne", "addressLineTwo", "123456", "city", "state"),
                false
        );
        var billRequest = new BillRequest(List.of("1"), "1", "1");
        var billCmdModel = new BillCommandModel(billRequest, address, "test");
        billCmdModel.setCost(new Cost(1233, 345, 32, 900, 45));
        billCmdModel.addItem(new ItemModel("1", "test", 12.34, 1));
        billCmdModel.removeItem(new ItemRequest("1", "1"));
        billCmdModel.addItem(new ItemModel("1", "test", 12.34, 1));
        var itemRequest = new BillEventDTO(BillActivities.ADD_ITEM, billCmdModel, null);
        billCommandEventBroker.publish(itemRequest);
        Assertions.assertNotNull(userSession.getUserId());
    }
}
