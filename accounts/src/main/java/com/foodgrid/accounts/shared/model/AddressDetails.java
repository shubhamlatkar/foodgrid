package com.foodgrid.accounts.shared.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDetails {
    private String addressLineOne;
    private String addressLineTwo;
    @NotNull
    @Size(max = 6, min = 6)
    private String pin;
    private String city;
    private String state;
}
