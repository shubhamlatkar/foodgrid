package com.foodgrid.notification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
class NotificationApplicationTests {
    @Autowired
    private NotificationApplication notificationApplication;

    @Test
    @DisplayName("Tests main method of NotificationApplication")
    void main() {
        var args = new String[]{"1"};
        NotificationApplication.main(args);
        Assertions.assertNotNull(args);
    }

}