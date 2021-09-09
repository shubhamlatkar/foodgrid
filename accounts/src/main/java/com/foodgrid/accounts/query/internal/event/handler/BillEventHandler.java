package com.foodgrid.accounts.query.internal.event.handler;

import com.foodgrid.accounts.query.internal.model.aggregate.BillQueryModel;
import com.foodgrid.accounts.query.internal.repository.BillQueryRepository;
import com.foodgrid.accounts.shared.payload.BillEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BillEventHandler {

    private final BillQueryRepository billQueryRepository;

    @Autowired
    public BillEventHandler(BillQueryRepository billQueryRepository) {
        this.billQueryRepository = billQueryRepository;
    }

    public void consumeEvent(BillEventDTO bill) {
        switch (bill.getActivity()) {
            case GENERATE:
                generateBill(bill);
                break;
            case UPDATE:
                updateBill(bill);
                break;
            case DELETE:
                deleteBill(bill);
                break;
            case ADD_ITEM:
                addItem(bill);
                break;
            case REMOVE_ITEM:
                removeItem(bill);
                break;
            default:
                break;
        }
    }

    private void generateBill(BillEventDTO billEventDTO) {
        var bill = new BillQueryModel(billEventDTO);
        billQueryRepository.save(bill);
        log.info("Bill query generated bill: {}", billEventDTO);
    }

    private void updateBill(BillEventDTO billEventDTO) {
        billQueryRepository.save(new BillQueryModel(billEventDTO));
        log.info("Bill query updated bill: {}", billEventDTO);
    }

    private void deleteBill(BillEventDTO billEventDTO) {
        billQueryRepository.deleteById(billEventDTO.getId());
        log.info("Bill query generated bill: {}", billEventDTO);
    }

    private void addItem(BillEventDTO billEventDTO) {
        var bill = billQueryRepository.findById(billEventDTO.getId()).orElse(null);
        assert bill != null;
        billQueryRepository.save(bill.addItem(billEventDTO.getItem()));
        log.info("Bill query added item to bill: {}", billEventDTO);
    }

    private void removeItem(BillEventDTO billEventDTO) {
        var bill = billQueryRepository.findById(billEventDTO.getId()).orElse(null);
        assert bill != null;
        billQueryRepository.save(bill.removeItem(billEventDTO.getItem()));
        log.info("Bill query removed item from bill: {}", billEventDTO);
    }
}