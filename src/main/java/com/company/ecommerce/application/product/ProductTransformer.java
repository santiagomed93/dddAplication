package com.company.ecommerce.application.product;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductTransformer {

    public static final Transformer<Product, ProductDto> toProductDto = product -> {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.id().id());
        productDto.setName(product.name());
        productDto.setDescription(product.description());
        productDto.setPrice(product.price());
        productDto.setQuantityOnStock(product.quantityOnStock());
        return productDto;
    };

    public static final Transformer<List<Product>, List<ProductDto>> toProductDtoList = productList -> productList.stream()
            .map(toProductDto::transform)
            .collect(Collectors.toList());

}
