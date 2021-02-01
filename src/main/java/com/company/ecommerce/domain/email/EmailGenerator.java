package com.company.ecommerce.domain.email;

@FunctionalInterface
public interface EmailGenerator<T> {
    EmailMessage generate(T t);
}
