package com.company.ecommerce.domain.purchaseorder;

import java.util.function.Consumer;

public class CheckOutDataBuilder {
    public String deliveryAddress;
    public String deliveryAddressNotes;
    public String invoiceAddress;
    public String creditCardNumber;

    public CheckOutDataBuilder with(
            Consumer<CheckOutDataBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public CheckOutData buildCheckOutData() {
        return new CheckOutData(deliveryAddress, deliveryAddressNotes, invoiceAddress, creditCardNumber);
    }
}
