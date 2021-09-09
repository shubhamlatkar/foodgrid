package com.foodgrid.user;


import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.utility.UserTypes;
import com.foodgrid.user.command.internal.model.aggregate.AddressCommandModel;
import com.foodgrid.user.command.internal.model.aggregate.CartCommandModel;
import com.foodgrid.user.query.internal.model.aggregate.AddressQueryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
//@EnableEurekaClient
@ComponentScan("com.foodgrid")
@EnableMongoRepositories("com.foodgrid")
@EntityScan("com.foodgrid")
@EnableScheduling
@Controller
@Slf4j
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }


    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner initData(MongoTemplate mongoTemplate) {
        return user -> {
            try {
                mongoTemplate.dropCollection(AddressQueryModel.class);
                mongoTemplate.dropCollection(AddressCommandModel.class);
                mongoTemplate.dropCollection(CartCommandModel.class);
                userDetailsService.initDatabase(mongoTemplate);
                Set<String> roles = new HashSet<>();
                roles.add("ROLE_USER");
                userDetailsService.saveUser(new SignUp("testUser", "testUser@test.com", roles, "test", "12345678902", UserTypes.USER));
            } catch (Exception e) {
                log.error("Mongo DB not available");
            }
        };
    }

    @GetMapping(value = "/member/**")
    public String redirect() {
        return "forward:/";
    }
}
