package com.foodgrid.accounts.command;

import com.foodgrid.accounts.command.external.payload.dto.AddressResponse;
import com.foodgrid.accounts.command.external.service.RestService;
import com.foodgrid.accounts.shared.model.AddressDetails;
import com.foodgrid.accounts.shared.model.Location;
import com.foodgrid.common.payload.dto.response.GetItemResponse;
import com.foodgrid.common.security.component.UserSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {RestService.class})
@AutoConfigureWebTestClient
class RestServiceTests {

    @MockBean
    @Qualifier("internal")
    private RestTemplate restTemplate;

    @MockBean
    private UserSession userSession;

    @Autowired
    private RestService restService;


    @Test
    @DisplayName("Tests getItemShort method of RestService")
    void getItemShort() {
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(new GetItemResponse("1", "test", 12.23)));
        Assertions.assertNotNull(restService.getItemShort("1", "1"));
    }

    @Test
    @DisplayName("Tests getAddress method of RestService")
    void getAddress() {
        when(userSession.getToken()).thenReturn("testToken");
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(ResponseEntity.ok(new AddressResponse(
                "1",
                "1",
                new Location(12.2, 12.2),
                "test",
                new AddressDetails("addressLineOne", "addressLineTwo", "123456", "city", "state"),
                false
        )));
        Assertions.assertNotNull(restService.getAddress("1"));
    }
}
