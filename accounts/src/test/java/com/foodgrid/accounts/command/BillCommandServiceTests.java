package com.foodgrid.accounts.command;

import com.foodgrid.accounts.command.external.event.broker.BillCommandEventBroker;
import com.foodgrid.accounts.command.external.payload.dto.AddressResponse;
import com.foodgrid.accounts.command.external.service.RestService;
import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.command.internal.payload.dto.request.BillRequest;
import com.foodgrid.accounts.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.accounts.command.internal.repository.BillCommandRepository;
import com.foodgrid.accounts.command.internal.service.BillCommandService;
import com.foodgrid.accounts.shared.model.AddressDetails;
import com.foodgrid.accounts.shared.model.Cost;
import com.foodgrid.accounts.shared.model.ItemModel;
import com.foodgrid.accounts.shared.model.Location;
import com.foodgrid.common.security.component.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BillCommandService.class})
@AutoConfigureWebTestClient
class BillCommandServiceTests {

    @MockBean
    private RestService restService;
    @MockBean
    private BillCommandRepository billCommandRepository;
    @MockBean
    private UserSession userSession;
    @MockBean
    private BillCommandEventBroker billEventBroker;

    @Autowired
    private BillCommandService billCommandService;

    @Test
    @DisplayName("Tests generateBill method of BillCommandService")
    void addressBroker() {
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
        when(restService.getAddress(anyString())).thenReturn(address);
        when(restService.getItemShort(anyString(), anyString())).thenReturn(itemModel);
        when(userSession.getUsername()).thenReturn("test");
        when(billCommandRepository.save(any())).thenReturn(billCmdModel);
        doAnswer(invocationOnMock -> null).when(billEventBroker).publish(any());
        var result = new BindingResults();

        Assertions.assertNotNull(billCommandService.generateBill(billRequest, result));
    }


    @Test
    @DisplayName("Tests deleteBill method of BillCommandService")
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
        when(restService.getAddress(anyString())).thenReturn(address);
        when(restService.getItemShort(anyString(), anyString())).thenReturn(itemModel);
        when(userSession.getUsername()).thenReturn("test");
        when(billCommandRepository.findById("1")).thenReturn(java.util.Optional.of(billCmdModel));
        doAnswer(invocationOnMock -> null).when(billCommandRepository).deleteById("1");
        doAnswer(invocationOnMock -> null).when(billEventBroker).publish(any());
        var result = new BindingResults();

        Assertions.assertNotNull(billCommandService.deleteBill("1"));
    }

    @Test
    @DisplayName("Tests updateBill method of BillCommandService")
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
        when(restService.getAddress(anyString())).thenReturn(address);
        when(restService.getItemShort(anyString(), anyString())).thenReturn(itemModel);
        when(userSession.getUsername()).thenReturn("test");
        when(billCommandRepository.findById("1")).thenReturn(java.util.Optional.of(billCmdModel));
        doAnswer(invocationOnMock -> null).when(billCommandRepository).save(any());
        doAnswer(invocationOnMock -> null).when(billEventBroker).publish(any());
        var result = new BindingResults();

        Assertions.assertNotNull(billCommandService.updateBill("1", billRequest, result));
    }

    @Test
    @DisplayName("Tests addItem method of BillCommandService")
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
        var itemRequest = new ItemRequest("1", "1");
        billCmdModel.removeItem(itemRequest);
        billCmdModel.addItem(itemModel);
        when(restService.getAddress(anyString())).thenReturn(address);
        when(restService.getItemShort(anyString(), anyString())).thenReturn(itemModel);
        when(userSession.getUsername()).thenReturn("test");
        when(billCommandRepository.findById("1")).thenReturn(java.util.Optional.of(billCmdModel));
        doAnswer(invocationOnMock -> null).when(billCommandRepository).save(any());
        doAnswer(invocationOnMock -> null).when(billEventBroker).publish(any());
        var result = new BindingResults();

        Assertions.assertNotNull(billCommandService.addItem("1", new ItemRequest("2", "2"), result));
    }

    @Test
    @DisplayName("Tests removeItem method of BillCommandService")
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
        var itemRequest = new ItemRequest("1", "1");
        billCmdModel.removeItem(itemRequest);
        billCmdModel.addItem(itemModel);
        when(restService.getAddress(anyString())).thenReturn(address);
        when(restService.getItemShort(anyString(), anyString())).thenReturn(itemModel);
        when(userSession.getUsername()).thenReturn("test");
        when(billCommandRepository.findById("1")).thenReturn(java.util.Optional.of(billCmdModel));
        doAnswer(invocationOnMock -> billCmdModel).when(billCommandRepository).save(any());
        doAnswer(invocationOnMock -> null).when(billEventBroker).publish(any());
        var result = new BindingResults();

        Assertions.assertNotNull(billCommandService.removeItem("1", itemRequest, result));
    }
}
