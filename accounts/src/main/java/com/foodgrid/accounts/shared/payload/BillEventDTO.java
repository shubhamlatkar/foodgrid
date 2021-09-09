package com.foodgrid.accounts.shared.payload;

import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.shared.model.ItemModel;
import com.foodgrid.accounts.shared.utility.BillActivities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillEventDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private BillActivities activity;
    private String id;
    private Double x;
    private Double y;
    private String name;
    private String addressLineOne;
    private String addressLineTwo;
    private String pin;
    private String city;
    private String state;
    private Set<ItemsEventDTO> items;
    private double total;
    private double serviceCharge;
    private double donation;
    private double restaurantCharge;
    private double tax;
    private String username;
    private String restaurantId;
    private String paymentId;
    private ItemsEventDTO item;

    public BillEventDTO(BillActivities activity, BillCommandModel bill, ItemsEventDTO item) {
        this.activity = activity;
        if (bill != null) {
            this.id = bill.getId();
            this.restaurantId = bill.getRestaurantId();
            this.paymentId = bill.getPaymentId();
            this.total = bill.getCost().getTotal();
            this.serviceCharge = bill.getCost().getServiceCharge();
            this.donation = bill.getCost().getDonation();
            this.restaurantCharge = bill.getCost().getRestaurantCharge();
            this.tax = bill.getCost().getTax();
            this.x = bill.getAddress().getLocation().getX();
            this.y = bill.getAddress().getLocation().getY();
            this.name = bill.getAddress().getName();
            this.addressLineOne = bill.getAddress().getAddressDetails().getAddressLineOne();
            this.addressLineTwo = bill.getAddress().getAddressDetails().getAddressLineTwo();
            this.pin = bill.getAddress().getAddressDetails().getPin();
            this.state = bill.getAddress().getAddressDetails().getState();
            this.city = bill.getAddress().getAddressDetails().getCity();
            this.username = bill.getUsername();
            this.items = new HashSet<>();
            for (ItemModel i : bill.getItems())
                items.add(new ItemsEventDTO(i));
        }
        if (item != null)
            this.item = item;
    }
}
