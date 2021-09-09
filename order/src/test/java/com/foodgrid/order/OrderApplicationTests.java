package com.foodgrid.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderApplicationTests {
    @Autowired
    private OrderApplication orderApplication;

    @Test
    @DisplayName("Tests redirect method of OrderApp")
    void redirect() {
        Assertions.assertNotNull(orderApplication.getDefault());
    }
}