package com.foodgrid.accounts.command.internal.model.aggregate;

import com.foodgrid.accounts.command.external.payload.dto.AddressResponse;
import com.foodgrid.accounts.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.accounts.command.internal.payload.dto.request.BillRequest;
import com.foodgrid.accounts.shared.model.Address;
import com.foodgrid.accounts.shared.model.Cost;
import com.foodgrid.accounts.shared.model.ItemModel;
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
public class BillCommandModel {
    @Id
    private String id;
    private Address address;
    private Set<ItemModel> items;
    private Cost cost;
    private String username;
    private String restaurantId;
    private String paymentId;

    public BillCommandModel(BillRequest billRequest, AddressResponse addressResponse, String username) {
        this.address = new Address();
        this.address.setLocation(addressResponse.getLocation());
        this.address.setAddressDetails(addressResponse.getAddressDetails());
        this.address.setName(addressResponse.getName());
        this.username = username;
        this.restaurantId = billRequest.getRestaurantId();
    }

    public BillCommandModel addItem(ItemModel item) {
        if (items == null)
            items = new HashSet<>();
        items.add(item);
        return this;
    }

    public BillCommandModel removeItem(ItemRequest item) {
        if (items != null)
            items.removeIf(i -> i.getId().equals(item.getItemId()));
        return this;
    }
}
