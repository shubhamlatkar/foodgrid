package com.foodgrid.accounts.command.internal.repository;

import com.foodgrid.accounts.command.internal.model.aggregate.BillCommandModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillCommandRepository extends MongoRepository<BillCommandModel, String> {

}
