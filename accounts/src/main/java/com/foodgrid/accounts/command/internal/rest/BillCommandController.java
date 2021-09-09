package com.foodgrid.accounts.command.internal.rest;

import com.foodgrid.accounts.command.internal.payload.dto.request.ItemRequest;
import com.foodgrid.accounts.command.internal.payload.dto.request.BillRequest;
import com.foodgrid.accounts.command.internal.payload.dto.response.GeneratedBill;
import com.foodgrid.accounts.command.internal.service.BillCommandService;
import com.foodgrid.common.payload.dto.response.GenericIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/${endpoint.service}/${endpoint.version}")
public class BillCommandController {

    private final BillCommandService billCommandService;

    @Autowired
    public BillCommandController(BillCommandService billCommandService) {
        this.billCommandService = billCommandService;
    }

    @PutMapping("/${endpoint.accounts.bill}")
    public ResponseEntity<GeneratedBill> generateBill(@Valid @RequestBody BillRequest billRequest, BindingResult result) {
        return ResponseEntity.ok(billCommandService.generateBill(billRequest, result));
    }

    @DeleteMapping("/${endpoint.accounts.bill}/{billId}")
    public ResponseEntity<GenericIdResponse> deleteBillById(@PathVariable String billId) {
        return ResponseEntity.ok(billCommandService.deleteBill(billId));
    }

    @PatchMapping("/${endpoint.accounts.bill}/{billId}")
    public ResponseEntity<GeneratedBill> updateBill(@PathVariable String billId, @Valid @RequestBody BillRequest billRequest, BindingResult result) {
        return ResponseEntity.ok(billCommandService.updateBill(billId, billRequest, result));
    }

    @PutMapping("/${endpoint.accounts.bill}/{billId}")
    public ResponseEntity<GeneratedBill> addItem(@PathVariable String billId, @Valid @RequestBody ItemRequest itemRequest, BindingResult result) {
        return ResponseEntity.ok(billCommandService.addItem(billId, itemRequest, result));
    }

    @PatchMapping("/${endpoint.accounts.bill}/${endpoint.accounts.item}/{billId}")
    public ResponseEntity<GeneratedBill> removeItem(@PathVariable String billId, @Valid @RequestBody ItemRequest itemRequest, BindingResult result) {
        return ResponseEntity.ok(billCommandService.removeItem(billId, itemRequest, result));
    }
}
