package com.foodgrid.accounts.query;

import com.foodgrid.accounts.command.external.payload.dto.AddressResponse;
import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.command.internal.payload.dto.request.BillRequest;
import com.foodgrid.accounts.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.accounts.query.internal.event.handler.BillEventHandler;
import com.foodgrid.accounts.query.internal.model.aggregate.BillQueryModel;
import com.foodgrid.accounts.query.internal.repository.BillQueryRepository;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest(classes = {BillEventHandler.class})
@AutoConfigureWebTestClient
class BillEventHandlerTests {

    @MockBean
    private BillQueryRepository billQueryRepository;

    @Autowired
    private BillEventHandler billEventHandler;

    @Test
    @DisplayName("Tests generateBill method of BillEventHandler")
    void generateBill() {
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

        var event = new BillEventDTO(BillActivities.GENERATE, billCmdModel, new ItemsEventDTO(itemModel));
        var billQuery = new BillQueryModel(event);

        doAnswer(invocationOnMock -> null).when(billQueryRepository).save(any());

        billEventHandler.consumeEvent(event);

        Assertions.assertNotNull(billCmdModel.getId());
    }

    @Test
    @DisplayName("Tests updateBill method of BillEventHandler")
    void updateBill() {
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

        var event = new BillEventDTO(BillActivities.UPDATE, billCmdModel, new ItemsEventDTO(itemModel));
        var billQuery = new BillQueryModel(event);

        doAnswer(invocationOnMock -> null).when(billQueryRepository).save(any());

        billEventHandler.consumeEvent(event);

        Assertions.assertNotNull(billCmdModel.getId());
    }

    @Test
    @DisplayName("Tests deleteBill method of BillEventHandler")
    void deleteBill() {
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

        var event = new BillEventDTO(BillActivities.DELETE, billCmdModel, new ItemsEventDTO(itemModel));
        var billQuery = new BillQueryModel(event);

        doAnswer(invocationOnMock -> null).when(billQueryRepository).deleteById(anyString());

        billEventHandler.consumeEvent(event);

        Assertions.assertNotNull(billCmdModel.getId());
    }

    @Test
    @DisplayName("Tests addItem method of BillEventHandler")
    void addItem() {
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

        var event = new BillEventDTO(BillActivities.ADD_ITEM, billCmdModel, new ItemsEventDTO(itemModel));
        var billQuery = new BillQueryModel(event);

        doAnswer(invocationOnMock -> null).when(billQueryRepository).deleteById(anyString());
        doAnswer(invocationOnMock -> Optional.of(billQuery)).when(billQueryRepository).findById(anyString());

        billEventHandler.consumeEvent(event);

        Assertions.assertNotNull(billCmdModel.getId());
    }

    @Test
    @DisplayName("Tests removeItem method of BillEventHandler")
    void removeItem() {
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

        var event = new BillEventDTO(BillActivities.REMOVE_ITEM, billCmdModel, new ItemsEventDTO(itemModel));
        var billQuery = new BillQueryModel(event);

        doAnswer(invocationOnMock -> null).when(billQueryRepository).deleteById(anyString());
        doAnswer(invocationOnMock -> Optional.of(billQuery)).when(billQueryRepository).findById(anyString());

        billEventHandler.consumeEvent(event);

        Assertions.assertNotNull(billCmdModel.getId());
    }
}
