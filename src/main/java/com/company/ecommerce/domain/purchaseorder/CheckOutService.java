package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.domain.cart.Cart;
import com.company.ecommerce.domain.cart.CartItem;
import com.company.ecommerce.domain.cart.CartRepository;
import com.company.ecommerce.domain.customer.CreditCard;
import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.event.DomainEventPublisher;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductId;
import com.company.ecommerce.domain.product.ProductRepository;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckOutService {

    private ProductRepository productRepository;
    private PurchaseOrderRepository purchaseOrderRepository;
    private CartRepository cartRepository;
    private DomainEventPublisher publisher;

    public CheckOutService(ProductRepository productRepository, PurchaseOrderRepository purchaseOrderRepository, DomainEventPublisher publisher, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.cartRepository = cartRepository;
        this.publisher = publisher;
    }

    public void checkOutCart(Cart cart, Customer customer, CheckOutData checkOutData) {
        boolean canCheckOut = canCheckOut(cart);
        boolean validCreditCard = validCreditCard(customer, checkOutData.creditCardNumber());
        if (canCheckOut && validCreditCard) {
            PurchaseOrder purchaseOrder = getPurchaseOrderFromCart(cart, customer, checkOutData);
            cart.clearCart();
            cartRepository.save(cart);
            purchaseOrderRepository.save(purchaseOrder);
            publisher.publishEvent(new PurchaseOrderCreated(purchaseOrder, customer));
        }
    }

    private boolean validCreditCard(Customer customer, String creditCardNumber) {
        Optional<CreditCard> result = customer.creditCards().stream().filter(creditCard -> creditCard.cardNumber().equals(creditCardNumber)).findFirst();
        if (result.isPresent()) {
            return true;
        } else {
            throw new IllegalArgumentException(String.format("Credit Card with number %s does not belongs to Customer %s", creditCardNumber, customer.name().fullName()));
        }
    }

    private boolean canCheckOut(Cart cart) {
        if (CollectionUtils.isEmpty(cart.items())) {
            throw new IllegalArgumentException(String.format("The cart with id %s is empty", cart.id().id()));
        } else {
            for (CartItem cartItem : cart.items()) {
                Product product = nonNullProduct(cartItem.productId().id());
                if (product.quantityOnStock() < cartItem.quantity()) {
                    throw new IllegalArgumentException(String.format("There is not enough items in stock for product %s", product.name()));
                }
            }
        }
        return true;
    }

    private PurchaseOrder getPurchaseOrderFromCart(Cart cart, Customer customer, CheckOutData checkOutData) {
        PurchaseOrderId purchaseOrderId = purchaseOrderRepository.generateId();
        return new PurchaseOrderBuilder().with($ -> {
            $.id = purchaseOrderId;
            $.customerId = customer.id();
            $.deliveryAddress = checkOutData.deliveryAddress();
            $.deliveryAddressNotes = checkOutData.deliveryAddressNotes();
            $.invoiceAddress = checkOutData.invoiceAddress();
            $.totalCost = cart.calculateTotalCost();
            $.totalTaxes = cart.calculateTotalTax();
            $.items = getPurchaseItemsFromCart(cart, purchaseOrderId);
        }).buildPurchaseOrder();
    }

    private Set<PurchaseItem> getPurchaseItemsFromCart(Cart cart, PurchaseOrderId purchaseOrderId) {
        return cart.items().stream().map(cartItem -> new PurchaseItem(cartItem, purchaseOrderId)).collect(Collectors.toSet());
    }

    private Product nonNullProduct(String productId) {
        ProductId id = new ProductId(productId);
        return productRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("The Product with id %s does not exist", productId));
        });
    }
}
