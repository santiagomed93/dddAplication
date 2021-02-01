package com.company.ecommerce.domain.product;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductBuilderTest {

    private ProductId defaultProductId = new ProductId("productid");

    @Test
    public void whenBuildingProductWithValidData_thenProductShouldBeCreated() {
        Product product = new ProductBuilder().with($ -> {
            $.id = defaultProductId;
            $.description = "Description";
            $.name = "Product";
            $.price = 100;
            $.quantityOnStock = 100;
        }).buildProduct();

        assertThat(product.id()).isEqualTo(defaultProductId);
        assertThat(product.name()).isEqualTo("Product");
        assertThat(product.description()).isEqualTo("Description");
        assertThat(product.quantityOnStock()).isEqualTo(100);
        assertThat(product.price()).isEqualTo(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithoutData_thenValidationShouldBeThrown() {
        new ProductBuilder().buildProduct();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithIdEqualsToNull_thenValidationShouldBeThrown() {
        new ProductBuilder().with($ -> {
            $.description = "Description";
            $.name = "Product";
            $.price = 100;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithEmptyName_thenValidationShouldBeThrown() {
        new ProductBuilder().with($ -> {
            $.id = defaultProductId;
            $.description = "Description";
            $.name = "";
            $.price = 100;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithNullName_thenValidationShouldBeThrown() {
        new ProductBuilder().with($ -> {
            $.id = defaultProductId;
            $.description = "Description";
            $.price = 100;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithEmptyDescription_thenValidationShouldBeThrown() {
        new ProductBuilder().with($ -> {
            $.id = defaultProductId;
            $.description = "";
            $.name = "Product";
            $.price = 100;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithNullDescription_thenValidationShouldBeThrown() {
        new ProductBuilder().with($ -> {
            $.id = defaultProductId;
            $.name = "Product";
            $.price = 100;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithPriceEqualsToZero_thenValidationShouldBeThrown() {
        new ProductBuilder().with($ -> {
            $.id = defaultProductId;
            $.description = "Description";
            $.name = "Product";
            $.price = 0;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithNegativePrice_thenValidationShouldBeThrown() {
        new ProductBuilder().with($ -> {
            $.id = defaultProductId;
            $.description = "Description";
            $.name = "Product";
            $.price = -100;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingProductWithNegativeQuantityInStock_thenValidationShouldBeThrown() {
        new ProductBuilder().with($ -> {
            $.id = defaultProductId;
            $.description = "Description";
            $.name = "Product";
            $.price = 100;
            $.quantityOnStock = -100;
        }).buildProduct();
    }

}