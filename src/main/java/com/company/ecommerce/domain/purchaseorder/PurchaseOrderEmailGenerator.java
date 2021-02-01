package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.domain.email.EmailGenerator;
import com.company.ecommerce.domain.email.EmailMessage;

public class PurchaseOrderEmailGenerator {

    private static final String PURCHASE_ORDER_MESSAGE = "Dear %s \nThe purchase order with id %s has been created. Here are the details:\n\n" +
            "Delivery Address: %s\n" +
            "Delivery Address Notes: %s\n" +
            "Invoice Address: %s \n\n" +
            "See you soon!!!!";
    private static final String PURCHASE_ORDER_SUBJECT = "Purchase Order Information";

    public static final EmailGenerator<PurchaseOrderCreated> purchaseOrderCreatedEmailMessage = purchaseOrderCreated -> {
        String body = String.format(PURCHASE_ORDER_MESSAGE,
                purchaseOrderCreated.customer().name().fullName(),
                purchaseOrderCreated.purchaseOrder().id().id(),
                purchaseOrderCreated.purchaseOrder().deliveryAddress(),
                purchaseOrderCreated.purchaseOrder().deliveryAddressNotes(),
                purchaseOrderCreated.purchaseOrder().invoiceAddress());
        return new EmailMessage(purchaseOrderCreated.customer().email(), PURCHASE_ORDER_SUBJECT, body);
    };

}
