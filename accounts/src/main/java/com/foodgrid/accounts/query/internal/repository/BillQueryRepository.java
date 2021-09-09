package com.foodgrid.accounts.query.internal.repository;

import com.foodgrid.accounts.query.internal.model.aggregate.BillQueryModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillQueryRepository extends MongoRepository<BillQueryModel, String> {

}
