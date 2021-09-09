package com.foodgrid.accounts.command.internal.payload.dto.response;

import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.shared.model.Address;
import com.foodgrid.accounts.shared.model.Cost;
import com.foodgrid.accounts.shared.model.ItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratedBill {
    private String id;
    private Set<ItemModel> items;
    private Cost cost;
    private Address address;

    public GeneratedBill(BillCommandModel bill) {
        this.id = bill.getId();
        this.cost = bill.getCost();
        this.address = bill.getAddress();
        this.items = bill.getItems();
    }
}
