package com.foodgrid.accounts.command.internal.service;

import com.foodgrid.accounts.command.external.event.broker.BillCommandEventBroker;
import com.foodgrid.accounts.command.external.service.RestService;
import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.accounts.command.internal.payload.dto.request.BillRequest;
import com.foodgrid.accounts.command.internal.payload.dto.response.GeneratedBill;
import com.foodgrid.accounts.command.internal.repository.BillCommandRepository;
import com.foodgrid.accounts.shared.model.Cost;
import com.foodgrid.accounts.shared.model.ItemModel;
import com.foodgrid.accounts.shared.payload.BillEventDTO;
import com.foodgrid.accounts.shared.payload.ItemsEventDTO;
import com.foodgrid.accounts.shared.utility.BillActivities;
import com.foodgrid.common.exception.exceptions.InvalidDataException;
import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import com.foodgrid.common.security.component.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class BillCommandService {

    private final RestService restService;
    private final BillCommandRepository billCommandRepository;
    private final UserSession userSession;
    private final BillCommandEventBroker billEventBroker;

    @Autowired
    public BillCommandService(RestService restService, BillCommandRepository billCommandRepository, UserSession userSession, BillCommandEventBroker billEventBroker) {
        this.restService = restService;
        this.billCommandRepository = billCommandRepository;
        this.userSession = userSession;
        this.billEventBroker = billEventBroker;
    }

    public GeneratedBill generateBill(BillRequest billRequest, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid data");
        var bill = billCommandRepository.save(getBill(billRequest));
        log.info("Bill Command generated bill :{}", bill);

        billEventBroker.publish(new BillEventDTO(BillActivities.GENERATE, bill, null));

        return new GeneratedBill(bill);
    }

    private Cost calculateCost(Set<ItemModel> items) {
        var cost = new Cost();
        cost.setDonation(3.0);
        var total = items.stream().mapToDouble(ItemModel::getValue).sum();
        cost.setRestaurantCharge(total);
        var service = (total * 18) / 100;
        cost.setServiceCharge(service);
        var tax = (service * 18) / 100;
        cost.setTax(tax);
        cost.setTotal(total + service + 3.0 + tax);
        return cost;
    }

    private BillCommandModel getBill(BillRequest billRequest) {
        var address = restService.getAddress(billRequest.getAddressId());
        var bill = new BillCommandModel(billRequest, address, userSession.getUsername());
        var items = new HashSet<ItemModel>();
        for (String item : billRequest.getItems())
            items.add(restService.getItemShort(bill.getRestaurantId(), item));
        bill.setItems(items);
        bill.setCost(calculateCost(items));
        return bill;
    }

    public GenericIdResponse deleteBill(String billId) {
        var bill = billCommandRepository.findById(billId).orElse(null);
        if (bill != null && Boolean.TRUE.equals(bill.getUsername().equals(userSession.getUsername())))
            billCommandRepository.deleteById(billId);
        else throw new InvalidDataException("Invalid id: " + billId);
        log.info("Bill Command deleted bill with id :{}", billId);
        billEventBroker.publish(new BillEventDTO(BillActivities.DELETE, bill, null));
        return new GenericIdResponse(billId, "Deleted...");
    }

    public GeneratedBill updateBill(String billId, BillRequest billRequest, BindingResult result) {
        var existingBill = billCommandRepository.findById(billId).orElse(null);
        if (result.hasErrors() || existingBill == null || Boolean.FALSE.equals(existingBill.getUsername().equals(userSession.getUsername())))
            throw new InvalidDataException("Invalid data");

        var bill = getBill(billRequest);
        bill.setId(billId);

        billCommandRepository.save(bill);

        billEventBroker.publish(new BillEventDTO(BillActivities.UPDATE, bill, null));
        log.info("Bill Command updated bill with id :{}", billId);
        return new GeneratedBill(bill);
    }

    public GeneratedBill addItem(String billId, ItemRequest itemRequest, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid bill data");

        var bill = billCommandRepository.findById(billId).orElse(null);

        if (bill == null || Boolean.FALSE.equals(bill.getUsername().equals(userSession.getUsername())))
            throw new InvalidDataException("Invalid bill id: " + billId);

        if (bill.getItems().stream().anyMatch(i -> i.getId().equals(itemRequest.getItemId())) || Boolean.FALSE.equals(bill.getUsername().equals(userSession.getUsername())))
            throw new InvalidDataException("Invalid request");

        var item = restService.getItemShort(itemRequest.getRestaurantId(), itemRequest.getItemId());
        billCommandRepository.save(bill.addItem(item));

        billEventBroker.publish(new BillEventDTO(BillActivities.ADD_ITEM, bill, new ItemsEventDTO(item)));
        log.info("Bill Command add item {} to bill with id :{}", itemRequest, billId);
        return new GeneratedBill(bill);
    }

    public GeneratedBill removeItem(String billId, ItemRequest itemRequest, BindingResult result) {
        if (result.hasErrors())
            throw new InvalidDataException("Invalid bill data");

        var bill = billCommandRepository.findById(billId).orElse(null);

        if (bill == null || Boolean.FALSE.equals(bill.getUsername().equals(userSession.getUsername())))
            throw new InvalidDataException("Invalid bill id: " + billId);

        var item = bill.getItems().stream().filter(i -> i.getId().equals(itemRequest.getItemId())).findFirst().orElse(null);

        if (item != null) {
            bill = billCommandRepository.save(bill.removeItem(itemRequest));
            billEventBroker.publish(new BillEventDTO(BillActivities.REMOVE_ITEM, bill, new ItemsEventDTO(item)));
        } else throw new InvalidDataException("Invalid request");


        log.info("Bill Command removed item {} from bill with id :{}", itemRequest, billId);
        return new GeneratedBill(bill);
    }
}
