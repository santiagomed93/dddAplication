package com.company.ecommerce.presentation.cart;

import com.company.ecommerce.application.cart.*;
import com.company.ecommerce.presentation.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping("/carts/customer/{customerId}")
    public void createCartForCustomer(@PathVariable String customerId) {
        service.createCartForCustomer(customerId);
    }

    @GetMapping("/carts/customer/{customerId}")
    public ResponseEntity<GenericResponse<CartDto>> findCustomerCart(@PathVariable String customerId) {
        CartDto cartDto = service.findCustomerCart(customerId, CartTransformer.toCartDto);
        GenericResponse<CartDto> response = new GenericResponse<>(cartDto, "Cart Found");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/carts/{id}")
    public void deleteCart(@PathVariable String id) {
        service.deleteCart(id);
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<GenericResponse<CartDto>> findCartById(@PathVariable String id) {
        CartDto cartDto = service.findCartById(id, CartTransformer.toCartDto);
        GenericResponse<CartDto> response = new GenericResponse<>(cartDto, "Cart Found");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/carts/{id}/cart-items")
    public void addCartItem(@RequestBody CartItemDto cartItemDto, @PathVariable String id) {
        service.addCartItem(id, cartItemDto.getProductId(), cartItemDto.getQuantity());
    }

    @DeleteMapping("/carts/{id}/cart-items/{productId}")
    public void removeCartItemByProductId(@PathVariable String id, @PathVariable String productId) {
        service.removeCartItemByProductId(id, productId);
    }

    @DeleteMapping("/carts/{id}/cart-items")
    public void clearCart(@PathVariable String id) {
        service.clearCart(id);
    }

    @PatchMapping("/carts/{cartId}/cart-items/quantity")
    public void updateCartItemQuantity(@RequestBody CartItemDto cartItemDto, @PathVariable String cartId) {
        service.updateCartItemQuantity(cartId, cartItemDto.getProductId(), cartItemDto.getQuantity());
    }

    @PostMapping("/carts/customer/{customerId}/checkout")
    public void checkoutCart(@RequestBody CheckOutDataDto checkOutDataDto, @PathVariable String customerId) {
        CartCheckOutCommand cartCheckOutCommand = new CartCheckOutCommand();
        cartCheckOutCommand.setCustomerId(customerId);
        cartCheckOutCommand.setInvoiceAddress(checkOutDataDto.getInvoiceAddress());
        cartCheckOutCommand.setDeliveryAddressNotes(checkOutDataDto.getDeliveryAddressNotes());
        cartCheckOutCommand.setDeliveryAddress(checkOutDataDto.getDeliveryAddress());
        cartCheckOutCommand.setCreditCardNumber(checkOutDataDto.getCreditCardNumber());

        service.checkOutCart(cartCheckOutCommand);
    }
}
