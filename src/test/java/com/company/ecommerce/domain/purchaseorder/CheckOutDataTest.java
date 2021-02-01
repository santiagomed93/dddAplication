package com.company.ecommerce.domain.purchaseorder;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckOutDataTest {

    @Test
    public void whenCreatingNameWithValidData_theNameShouldBeCreated() {
        CheckOutData checkOutData = new CheckOutData();
        assertThat(checkOutData.deliveryAddress()).isEqualTo(null);
        assertThat(checkOutData.deliveryAddressNotes()).isEqualTo(null);
        assertThat(checkOutData.invoiceAddress()).isEqualTo(null);
        assertThat(checkOutData.creditCardNumber()).isEqualTo(null);
    }
}