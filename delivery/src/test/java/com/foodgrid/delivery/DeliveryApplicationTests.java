package com.foodgrid.delivery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
class DeliveryApplicationTests {

    @Autowired
    private DeliveryApplication deliveryApplication;

    @Test
    @DisplayName("Tests main method of deliveryApplication")
    void main() {
        var args = new String[]{"1"};
        DeliveryApplication.main(args);
        Assertions.assertNotNull(args);
    }

    @Test
    @DisplayName("Tests redirect method of deliveryApplication")
    void redirect() {
        Assertions.assertNotNull(deliveryApplication.redirect());
    }
}