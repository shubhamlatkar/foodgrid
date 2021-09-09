package com.foodgrid.accounts.query.internal.rest;

import com.foodgrid.accounts.query.internal.model.aggregate.BillQueryModel;
import com.foodgrid.accounts.query.internal.service.BillQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class BillQueryController {

    private final BillQueryService billQueryService;

    @Autowired
    public BillQueryController(BillQueryService billQueryService) {
        this.billQueryService = billQueryService;
    }

    @GetMapping("/${endpoint.accounts.bill}/{billId}")
    public ResponseEntity<BillQueryModel> getBillById(@PathVariable String billId) {
        return ResponseEntity.ok(billQueryService.getBill(billId));
    }
}
