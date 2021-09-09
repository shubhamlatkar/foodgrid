package com.foodgrid.accounts.shared.payload;

import com.foodgrid.accounts.shared.model.ItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private Double value;
    private Integer quantity;

    public ItemsEventDTO(ItemModel i) {
        this.id = i.getId();
        this.name = i.getName();
        this.value = i.getValue();
        this.quantity = i.getQuantity();
    }
}
