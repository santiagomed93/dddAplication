package com.company.ecommerce.domain.purchaseorder;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckOutDataBuilderTest {

    @Test
    public void whenCreatingNameWithDefaultConstructor_theAllPropertiesShouldBeNull() {
        CheckOutData checkOutData = new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.creditCardNumber = "1234567890";
        }).buildCheckOutData();

        assertThat(checkOutData.deliveryAddress()).isEqualTo("Delivery Address");
        assertThat(checkOutData.deliveryAddressNotes()).isEqualTo("Delivery Address Notes");
        assertThat(checkOutData.invoiceAddress()).isEqualTo("Invoice Address");
        assertThat(checkOutData.creditCardNumber()).isEqualTo("1234567890");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingCheckOutDataWithEmptyDeliveryAddress_thenValidationShouldBeThrown() {
        new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = "";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.creditCardNumber = "1234567890";
        }).buildCheckOutData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingCheckOutDataWithNullDeliveryAddress_thenValidationShouldBeThrown() {
        new CheckOutDataBuilder().with($ -> {
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.creditCardNumber = "1234567890";
        }).buildCheckOutData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingCheckOutDataWithEmptyInvoiceAddress_thenValidationShouldBeThrown() {
        new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "";
            $.creditCardNumber = "1234567890";
        }).buildCheckOutData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingCheckOutDataWithNullInvoiceAddress_thenValidationShouldBeThrown() {
        new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.creditCardNumber = "1234567890";
        }).buildCheckOutData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingCheckOutDataWithEmptyCreditCardNumber_thenValidationShouldBeThrown() {
        new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.creditCardNumber = "";
        }).buildCheckOutData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingCheckOutDataWithNullCreditCardNumber_thenValidationShouldBeThrown() {
        new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
        }).buildCheckOutData();
    }

}