package com.foodgrid.accounts.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Location location;
    private String name;
    private AddressDetails addressDetails;
}
