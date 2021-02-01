package com.company.ecommerce.application.product;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductId;
import com.company.ecommerce.domain.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @TestConfiguration
    static class ProductServiceTestContextConfiguration {

        @Bean
        public ProductService productService() {
            return new ProductService();
        }
    }

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repository;

    private Product defaultProduct;

    @Before
    public void setUp() {
        defaultProduct = TestDomainObjectsFactory.createDefaultProduct();

        Mockito.when(repository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.of(defaultProduct));
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(defaultProduct));
        Mockito.when(repository.generateId()).thenReturn(new ProductId("1"));
    }

    @Test
    public void whenCreatingValidProduct_thenProductShouldBeCreated() {
        CreateProductCommand createProductCommand = new CreateProductCommand();
        createProductCommand.setDescription("Description");
        createProductCommand.setName("Product");
        createProductCommand.setPrice(100);
        createProductCommand.setQuantityOnStock(100);
        service.createProduct(createProductCommand);

        Mockito.verify(repository).save(Mockito.any(Product.class));
        Mockito.verify(repository).generateId();
    }

    @Test
    public void whenDeletingValidProduct_thenProductShouldBeDeleted() {
        service.deleteProduct("1");

        Mockito.verify(repository).remove(Mockito.any(ProductId.class));
        Mockito.verify(repository).findById(Mockito.any(ProductId.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeletingCountryTaxWithInvalidId_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.empty());

        service.deleteProduct("1");
    }

    @Test
    public void whenDecreasingQuantityInStockForValidProduct_thenProductShouldBeUpdated() {
        service.decreaseQuantityStock("1", 50);

        Mockito.verify(repository).findById(Mockito.any(ProductId.class));
        Mockito.verify(repository).save(Mockito.any(Product.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDecreasingStockInQuantityForProductWithInvalidId_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.empty());

        service.decreaseQuantityStock("1", 50);
    }

    @Test
    public void whenUpdatingProductDataForValidProduct_thenProductShouldBeUpdated() {
        service.updateProduct("1", "New Name", "NewDesc", 10, 1000);

        Mockito.verify(repository).findById(Mockito.any(ProductId.class));
        Mockito.verify(repository).save(Mockito.any(Product.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenUpdatingProductNameForProductThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.empty());

        service.updateProduct("1", "New Name", "", 10, 1000);
    }

    @Test
    public void whenFindingProductWithValidId_thenProductShouldBeReturned() {
        ProductDto productDto = service.findProductById("1", ProductTransformer.toProductDto);

        assertThat(productDto.getId()).isEqualTo(defaultProduct.id().id());
        assertThat(productDto.getName()).isEqualTo(defaultProduct.name());
        assertThat(productDto.getDescription()).isEqualTo(defaultProduct.description());
        assertThat(productDto.getPrice()).isEqualTo(defaultProduct.price());
        assertThat(productDto.getQuantityOnStock()).isEqualTo(defaultProduct.quantityOnStock());
        Mockito.verify(repository).findById(Mockito.any(ProductId.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindingProductWithInvalidId_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.empty());

        service.findProductById("1", ProductTransformer.toProductDto);
    }

    @Test
    public void whenFindingAllProducts_thenAllProductsShouldBeReturned() {
        List<ProductDto> products = service.findAllProducts(ProductTransformer.toProductDtoList);

        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenFindingAllProductsForEmptyRepository_thenProductListShouldBeEmpty() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        List<ProductDto> products = service.findAllProducts(ProductTransformer.toProductDtoList);

        assertThat(products).isEmpty();
        assertThat(products.size()).isEqualTo(0);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }
}