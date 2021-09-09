package com.foodgrid.common.unit;

import com.foodgrid.common.security.component.DeletedUsers;
import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {UserSession.class, DeletedUsers.class})
@AutoConfigureWebTestClient
class CustomBeanTest {

    @Autowired
    private DeletedUsers deletedUsers;

    @Autowired
    private UserSession userSession;

    @Test
    void testDeleteUserBean() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        deletedUsers.addUser(tempUser);
        Assertions.assertNotNull(deletedUsers.getUsers());

    }

    @Test
    void testUserSessionBean() {
        userSession.setUserId("testId");
        userSession.setToken("test");
        Assertions.assertNotNull(userSession.getUserId());
        Assertions.assertNotNull(userSession.getToken());

    }
}
