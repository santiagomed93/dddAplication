package com.company.ecommerce.application;

@FunctionalInterface
public interface Transformer<T, U> {
    U transform(final T t);
}
