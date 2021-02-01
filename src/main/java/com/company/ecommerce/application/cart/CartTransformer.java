package com.company.ecommerce.application.cart;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.cart.Cart;
import com.company.ecommerce.domain.cart.CartItem;

import java.util.Set;
import java.util.stream.Collectors;

public class CartTransformer {

    private static final Transformer<CartItem, CartItemDto> toCartItemDto = cartItem -> {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartId(cartItem.cartId().id());
        cartItemDto.setCost(cartItem.cost());
        cartItemDto.setProductId(cartItem.productId().id());
        cartItemDto.setQuantity(cartItem.quantity());
        cartItemDto.setTax(cartItem.tax());
        return cartItemDto;
    };

    private static final Transformer<Set<CartItem>, Set<CartItemDto>> toCartItemDtoList = cartItemList -> cartItemList.stream()
            .map(toCartItemDto::transform)
            .collect(Collectors.toSet());

    public static final Transformer<Cart, CartDto> toCartDto = cart -> {
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.id().id());
        cartDto.setCustomerId(cart.customerId().id());
        cartDto.setTotalCost(cart.calculateTotalCost());
        cartDto.setTotalTax(cart.calculateTotalTax());
        Set<CartItemDto> cartItemDtoList = toCartItemDtoList.transform(cart.items());
        cartDto.setItems(cartItemDtoList);
        return cartDto;
    };
}
