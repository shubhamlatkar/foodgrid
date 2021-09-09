package com.foodgrid.accounts;

import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import com.foodgrid.accounts.query.internal.model.aggregate.BillQueryModel;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
@CrossOrigin("*")
@ComponentScan("com.foodgrid")
@EnableMongoRepositories("com.foodgrid")
@EntityScan("com.foodgrid")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
@Slf4j
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @Bean
    CommandLineRunner initData(MongoTemplate mongoTemplate) {
        return user -> {
            try {
                mongoTemplate.dropCollection(BillCommandModel.class);
                mongoTemplate.dropCollection(BillQueryModel.class);
                userDetailsService.initDatabase(mongoTemplate);
            } catch (Exception e) {
                log.error("Mongo DB not available");
            }
        };
    }
}
