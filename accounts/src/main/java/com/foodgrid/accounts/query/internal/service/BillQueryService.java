package com.foodgrid.accounts.query.internal.service;

import com.foodgrid.accounts.query.internal.model.aggregate.BillQueryModel;
import com.foodgrid.accounts.query.internal.repository.BillQueryRepository;
import com.foodgrid.common.exception.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillQueryService {

    private final BillQueryRepository billQueryRepository;

    @Autowired
    public BillQueryService(BillQueryRepository billQueryRepository) {
        this.billQueryRepository = billQueryRepository;
    }

    public BillQueryModel getBill(String billId) {
        var bill = billQueryRepository.findById(billId).orElse(null);
        if (bill == null)
            throw new NotFoundException("Not found bill for id " + billId);
        log.info("Bill query get for bill id : {}", billId);
        return bill;
    }
}
