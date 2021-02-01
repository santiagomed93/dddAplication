package com.company.ecommerce.application.cart;

public class CartCheckOutCommand {

    private String deliveryAddress;
    private String deliveryAddressNotes;
    private String invoiceAddress;
    private String creditCardNumber;
    private String customerId;

    public CartCheckOutCommand() {
        super();
    }

    public CartCheckOutCommand(String deliveryAddress, String deliveryAddressNotes, String invoiceAddress, String creditCardNumber, String customerId) {
        this.deliveryAddress = deliveryAddress;
        this.deliveryAddressNotes = deliveryAddressNotes;
        this.invoiceAddress = invoiceAddress;
        this.creditCardNumber = creditCardNumber;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddressNotes() {
        return deliveryAddressNotes;
    }

    public void setDeliveryAddressNotes(String deliveryAddressNotes) {
        this.deliveryAddressNotes = deliveryAddressNotes;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
