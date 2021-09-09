package com.foodgrid.accounts.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cost {
    private double total;
    private double serviceCharge;
    private double donation;
    private double restaurantCharge;
    private double tax;
}
