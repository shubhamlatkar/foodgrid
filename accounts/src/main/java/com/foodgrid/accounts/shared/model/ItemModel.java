package com.foodgrid.accounts.shared.model;

import com.foodgrid.accounts.shared.payload.ItemsEventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel {
    private String id;
    private String name;
    private Double value;
    private Integer quantity;

    public ItemModel(ItemsEventDTO i) {
        this.id = i.getId();
        this.name = i.getName();
        this.value = i.getValue();
        this.quantity = i.getQuantity();
    }
}
