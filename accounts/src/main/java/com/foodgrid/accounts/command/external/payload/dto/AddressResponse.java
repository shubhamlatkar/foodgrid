package com.foodgrid.accounts.command.external.payload.dto;

import com.foodgrid.accounts.shared.model.AddressDetails;
import com.foodgrid.accounts.shared.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private String id;
    private String userId;
    private Location location;
    private String name;
    private AddressDetails addressDetails;
    private Boolean isSelected;
}
