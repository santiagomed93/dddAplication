package com.company.ecommerce.presentation.product;

import com.company.ecommerce.application.product.CreateProductCommand;
import com.company.ecommerce.application.product.ProductDto;
import com.company.ecommerce.application.product.ProductService;
import com.company.ecommerce.application.product.ProductTransformer;
import com.company.ecommerce.presentation.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/products")
    public void createProduct(@RequestBody ProductDto productDto) {
        CreateProductCommand createProductCommand = new CreateProductCommand();
        createProductCommand.setDescription(productDto.getDescription());
        createProductCommand.setName(productDto.getName());
        createProductCommand.setPrice(productDto.getPrice());
        createProductCommand.setQuantityOnStock(productDto.getQuantityOnStock());
        service.createProduct(createProductCommand);
    }

    @PutMapping("/products/{id}")
    public void updateProductName(@RequestBody ProductDto productDto, @PathVariable String id) {
        service.updateProduct(id, productDto.getName(), productDto.getDescription(), productDto.getPrice(), productDto.getQuantityOnStock());
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<GenericResponse<ProductDto>> findProductById(@PathVariable String id) {
        ProductDto productDto = service.findProductById(id, ProductTransformer.toProductDto);
        GenericResponse<ProductDto> response = new GenericResponse<>(productDto, "Product Found");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<GenericResponse<List<ProductDto>>> findAllProducts() {
        List<ProductDto> productDtoList = service.findAllProducts(ProductTransformer.toProductDtoList);
        GenericResponse<List<ProductDto>> response = new GenericResponse<>(productDtoList, "Here are all the products");
        return ResponseEntity.ok(response);
    }
}
