package com.foodgrid.accounts.command.external.service;

import com.foodgrid.accounts.command.external.payload.dto.AddressResponse;
import com.foodgrid.accounts.shared.model.ItemModel;
import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.payload.dto.response.GetItemResponse;
import com.foodgrid.common.security.component.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
public class RestService {

    @Autowired
    @Qualifier("internal")
    private RestTemplate restTemplate;

    @Autowired
    private UserSession userSession;

    public ItemModel getItemShort(String restaurantId, String itemId) {
        ResponseEntity<GetItemResponse> shortItemResponse = restTemplate.getForEntity(
                "http://restaurant/api/v1/public/short/menu/item?restaurantId=" + restaurantId + "&itemId=" + itemId,
                GetItemResponse.class
        );
        var shortItem = shortItemResponse.getBody();

        if (shortItem == null)
            throw new InternalServerErrorException("Error caught in getItemShort");

        log.info("Item received from restaurant service: {}", shortItem);
        return new ItemModel(shortItem.getId(), shortItem.getName(), shortItem.getValue(), 1);

    }

    public AddressResponse getAddress(String addressId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + userSession.getToken());
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<AddressResponse> addressResponse = restTemplate.exchange(
                "http://user/api/v1/address/" + addressId + "",
                HttpMethod.GET,
                entity,
                AddressResponse.class
        );

        var address = addressResponse.getBody();
        if (address == null)
            throw new InternalServerErrorException("Error caught in getting address");

        log.info("Address received from user service: {}", address);
        return address;
    }
}

