package com.foodgrid.accounts.command.internal.payload.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {
    @NotNull
    private List<String> items;
    @NotNull
    private String restaurantId;

    private String addressId;
}
