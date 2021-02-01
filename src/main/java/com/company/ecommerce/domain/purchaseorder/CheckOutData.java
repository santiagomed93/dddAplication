package com.company.ecommerce.domain.purchaseorder;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class CheckOutData {
    private String deliveryAddress;
    private String deliveryAddressNotes;
    private String invoiceAddress;
    private String creditCardNumber;

    public CheckOutData() {
        super();
    }

    CheckOutData(String deliveryAddress, String deliveryAddressNotes, String invoiceAddress, String creditCardNumber) {
        setDeliveryAddress(deliveryAddress);
        setDeliveryAddressNotes(deliveryAddressNotes);
        setInvoiceAddress(invoiceAddress);
        setCreditCardNumber(creditCardNumber);
    }

    public String deliveryAddress() {
        return deliveryAddress;
    }

    public String deliveryAddressNotes() {
        return deliveryAddressNotes;
    }

    public String invoiceAddress() {
        return invoiceAddress;
    }

    public String creditCardNumber() {
        return creditCardNumber;
    }

    private void setDeliveryAddress(String deliveryAddress) {
        if (StringUtils.isEmpty(deliveryAddress)) {
            throw new IllegalArgumentException("Delivery Address is Required");
        }
        this.deliveryAddress = deliveryAddress;
    }

    private void setDeliveryAddressNotes(String deliveryAddressNotes) {
        this.deliveryAddressNotes = deliveryAddressNotes;
    }

    private void setInvoiceAddress(String invoiceAddress) {
        if (StringUtils.isEmpty(invoiceAddress)) {
            throw new IllegalArgumentException("Invoice Address is Required");
        }
        this.invoiceAddress = invoiceAddress;
    }

    private void setCreditCardNumber(String creditCardNumber) {
        if (StringUtils.isEmpty(creditCardNumber)) {
            throw new IllegalArgumentException("Credit Card Number is Required");
        }
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckOutData that = (CheckOutData) o;
        return Objects.equals(deliveryAddress, that.deliveryAddress) &&
                Objects.equals(deliveryAddressNotes, that.deliveryAddressNotes) &&
                Objects.equals(invoiceAddress, that.invoiceAddress) &&
                Objects.equals(creditCardNumber, that.creditCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryAddress, deliveryAddressNotes, invoiceAddress, creditCardNumber);
    }
}
