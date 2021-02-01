package com.company.ecommerce.application.cart;

public class CheckOutDataDto {
    private String deliveryAddressNotes;
    private String invoiceAddress;
    private String creditCardNumber;
    private String deliveryAddress;

    public CheckOutDataDto() {
        super();
    }

    public CheckOutDataDto(String deliveryAddressNotes, String invoiceAddress, String creditCardNumber, String deliveryAddress) {
        this.deliveryAddressNotes = deliveryAddressNotes;
        this.invoiceAddress = invoiceAddress;
        this.creditCardNumber = creditCardNumber;
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
