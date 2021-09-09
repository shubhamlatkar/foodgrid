package com.foodgrid.restaurant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {RestaurantApplication.class})
@AutoConfigureWebTestClient
class RestaurantApplicationTests {

    @Autowired
    private RestaurantApplication restaurantApplication;

    @Test
    @DisplayName("Tests redirect method of UserApplication")
    void redirect() {
        Assertions.assertNotNull(restaurantApplication.redirect());
    }

}