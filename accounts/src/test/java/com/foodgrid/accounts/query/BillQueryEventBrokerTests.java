package com.foodgrid.accounts.query;

import com.foodgrid.accounts.command.external.payload.dto.AddressResponse;
import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.command.internal.payload.dto.request.BillRequest;
import com.foodgrid.accounts.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.accounts.query.internal.event.broker.BillQueryEventBroker;
import com.foodgrid.accounts.query.internal.event.handler.BillEventHandler;
import com.foodgrid.accounts.shared.model.AddressDetails;
import com.foodgrid.accounts.shared.model.Cost;
import com.foodgrid.accounts.shared.model.ItemModel;
import com.foodgrid.accounts.shared.model.Location;
import com.foodgrid.accounts.shared.payload.BillEventDTO;
import com.foodgrid.accounts.shared.payload.ItemsEventDTO;
import com.foodgrid.accounts.shared.utility.BillActivities;
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

@SpringBootTest(classes = {BillQueryEventBroker.class})
@AutoConfigureWebTestClient
class BillQueryEventBrokerTests {

    @MockBean
    private BillEventHandler billEventhandler;

    @Autowired
    private BillQueryEventBroker billQueryEventBroker;

    @Test
    @DisplayName("Tests consumeEvent BillQueryEventBroker method of BillQueryEventBroker")
    void billQueryEventBroker() {
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
        billCmdModel.setId("1");
        billCmdModel.setCost(new Cost(1233, 345, 32, 900, 45));
        var itemModel = new ItemModel("1", "test", 12.34, 1);
        billCmdModel.addItem(itemModel);
        billCmdModel.removeItem(new ItemRequest("1", "1"));
        billCmdModel.addItem(itemModel);
        doAnswer(invocationOnMock -> null).when(billEventhandler).consumeEvent(any());
        billQueryEventBroker.billEventBroker(new BillEventDTO(BillActivities.ADD_ITEM, billCmdModel, new ItemsEventDTO(itemModel)));

        Assertions.assertNotNull(billCmdModel.getId());
    }
}
