package com.foodgrid.accounts.command.internal.model.aggregate;

import com.foodgrid.accounts.shared.utility.PaymentMethods;
import com.foodgrid.accounts.shared.utility.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCommandModel {
    @Id
    private String id;
    private PaymentStatus status;
    private String billId;
    private String transactionId;
    private String bankId;
    private String clientId;
    private String upidId;
    private String cardNumber;
    private PaymentMethods paymentMethod;
}
