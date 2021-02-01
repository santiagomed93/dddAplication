package com.company.ecommerce.domain.product;

import com.company.ecommerce.TestDomainObjectsFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenAllPropertiesShouldBeEmpty() {
        Product product = new Product();

        assertThat(product.id()).isEqualTo(null);
        assertThat(product.name()).isEqualTo(null);
        assertThat(product.description()).isEqualTo(null);
        assertThat(product.quantityOnStock()).isEqualTo(0);
        assertThat(product.price()).isEqualTo(0);
    }

    @Test
    public void whenDecreaseQuantityInStockWithValidQuantity_thenProductShouldBeUpdated() {
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        product.decreaseQuantityInStock(50);

        assertThat(product.quantityOnStock()).isEqualTo(50);
    }

    @Test
    public void whenDecreaseQuantityInStockWithAmountEqualsToCurrentTotal_thenProductShouldBeUpdated() {
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        product.decreaseQuantityInStock(100);

        assertThat(product.quantityOnStock()).isEqualTo(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDecreaseQuantityInStockWithAmountGraterThanAvailable_thenProductBeUpdated() {
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        product.decreaseQuantityInStock(200);
    }

    @Test
    public void whenUpdatingProductWithValidData_thenProductShouldBeUpdated() {
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        product.update("NewName", "NewDesc", 20, 1000);

        assertThat(product.name()).isEqualTo("NewName");
        assertThat(product.description()).isEqualTo("NewDesc");
        assertThat(product.quantityOnStock()).isEqualTo(1000);
        assertThat(product.price()).isEqualTo(20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingProductWithInvalidName_thenValidationShouldBeThrown() {
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        product.update("", "NewDesc", 20, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingProductWithInvalidDescription_thenValidationShouldBeThrown() {
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        product.update("NewName", null, 20, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingProductWithInvalidPrice_thenValidationShouldBeThrown() {
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        product.update("NewName", "NewDesc", -20, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingProductWithInvalidQuantiry_thenValidationShouldBeThrown() {
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        product.update("NewName", "NewDesc", 20, -1000);
    }
}