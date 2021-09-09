package com.foodgrid.accounts.query.internal.model.aggregate;

import com.foodgrid.accounts.shared.model.*;
import com.foodgrid.accounts.shared.payload.BillEventDTO;
import com.foodgrid.accounts.shared.payload.ItemsEventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillQueryModel {
    @Id
    private String id;
    private Address address;
    private Set<ItemModel> items;
    private Cost cost;
    private String username;
    private String restaurantId;
    private String paymentId;

    public BillQueryModel(BillEventDTO bill) {
        this.id = bill.getId();
        this.address = new Address();
        this.address.setName(bill.getName());
        this.address.setAddressDetails(new AddressDetails(bill.getAddressLineOne(), bill.getAddressLineTwo(), bill.getPin(), bill.getCity(), bill.getState()));
        this.address.setLocation(new Location(bill.getX(), bill.getY()));
        this.username = bill.getUsername();
        this.cost = new Cost(bill.getTotal(), bill.getServiceCharge(), bill.getDonation(), bill.getRestaurantCharge(), bill.getTax());
        this.restaurantId = bill.getRestaurantId();
        this.paymentId = bill.getPaymentId();
        this.items = new HashSet<>();
        for (ItemsEventDTO i : bill.getItems())
            items.add(new ItemModel(i));
    }

    public BillQueryModel addItem(ItemsEventDTO item) {
        if (items == null)
            items = new HashSet<>();
        items.add(new ItemModel(item));
        return this;
    }

    public BillQueryModel removeItem(ItemsEventDTO item) {
        if (items != null)
            items.removeIf(i -> i.getId().equals(item.getId()));
        return this;
    }
}
