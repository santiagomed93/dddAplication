package com.company.ecommerce.domain.product;

import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Serializable {

    private String productId;

    public ProductId() {
        super();
    }

    public ProductId(String productId) {
        setProductId(productId);
    }

    private void setProductId(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw new IllegalArgumentException("Product Id is required");
        }
        this.productId = productId;
    }

    public String id() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId1 = (ProductId) o;
        return Objects.equals(productId, productId1.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
