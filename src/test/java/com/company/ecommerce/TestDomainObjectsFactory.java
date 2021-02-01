package com.company.ecommerce;

import com.company.ecommerce.application.cart.CartCheckOutCommand;
import com.company.ecommerce.application.customer.AddCreditCardCommand;
import com.company.ecommerce.application.customer.CreateCustomerCommand;
import com.company.ecommerce.domain.cart.Cart;
import com.company.ecommerce.domain.cart.CartId;
import com.company.ecommerce.domain.cart.CartItem;
import com.company.ecommerce.domain.country.Country;
import com.company.ecommerce.domain.countrytax.CountryTax;
import com.company.ecommerce.domain.countrytax.CountryTaxId;
import com.company.ecommerce.domain.countrytax.TaxType;
import com.company.ecommerce.domain.customer.*;
import com.company.ecommerce.domain.event.DomainEventPublisher;
import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductBuilder;
import com.company.ecommerce.domain.product.ProductId;
import com.company.ecommerce.domain.purchaseorder.*;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestDomainObjectsFactory {

    public static Product createDefaultProduct() {
        ProductId productId = new ProductId("1");
        return new ProductBuilder().with($ -> {
            $.id = productId;
            $.description = "Description";
            $.name = "Product";
            $.price = 100;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    public static Product createProductWithCustomId(String customId) {
        ProductId productId = new ProductId(customId);
        return new ProductBuilder().with($ -> {
            $.id = productId;
            $.description = "Description";
            $.name = "Product";
            $.price = 100;
            $.quantityOnStock = 100;
        }).buildProduct();
    }

    public static Cart createDefaultCart() {
        CartId cartId = new CartId("1");
        Customer customer = createDefaultCustomer();
        return new Cart(cartId, customer.id());
    }

    public static Cart createCartWithCustomId(String cartIdValue, String customerIdValue) {
        CartId cartId = new CartId(cartIdValue);
        CustomerId customerId = new CustomerId(customerIdValue);
        return new Cart(cartId, customerId);
    }


    public static Cart createCartWithItems() {
        Cart cart = createDefaultCart();
        cart.addCartItem(createCartItemWithCustomId(cart.id(), new ProductId("1")));
        cart.addCartItem(createCartItemWithCustomId(cart.id(), new ProductId("2")));
        return cart;
    }

    public static CartItem createDefaultCartItem() {
        Cart cart = createDefaultCart();
        Product product = createDefaultProduct();
        return new CartItem(cart.id(), product.id(), 100, 7, 2);
    }

    private static CartItem createCartItemWithCustomId(CartId cartId, ProductId productId) {
        return new CartItem(cartId, productId, 100, 7, 2);
    }

    public static Customer createDefaultCustomer() {
        DomainEventPublisher publisher = Mockito.mock(DomainEventPublisher.class);
        CustomerId customerId = new CustomerId("1");
        Country country = createDefaultCountry();
        return new CustomerBuilder().with($ -> {
            $.id = customerId;
            $.country = country;
            $.email = "h@h.com";
            $.firstName = "Test";
            $.lastName = "Test";
        }).buildCustomer(publisher);
    }

    public static Country createDefaultCountry() {
        return new Country(1, "Colombia");
    }

    public static CountryTax createDefaultCountryTax() {
        Country country = createDefaultCountry();
        CountryTaxId countryTaxId = new CountryTaxId(country.id(), TaxType.BUSINESS);
        return new CountryTax(countryTaxId, 1);
    }

    public static CountryTax createCountryTaxWithCustomTaxType(TaxType taxType) {
        Country country = createDefaultCountry();
        CountryTaxId countryTaxId = new CountryTaxId(country.id(), taxType);
        return new CountryTax(countryTaxId, 5);
    }

    public static CreateCustomerCommand createCustomerCommand() {
        return new CreateCustomerCommand("James", "Bond", "007@m6.com", 1, "1");
    }

    public static CreditCard createCreditCardWithCustomCardNumberAndCustomerId(String cardNumber, CustomerId customerId) {
        return new CreditCardBuilder().with($ -> {
            $.active = true;
            $.cardNumber = cardNumber;
            $.customerId = customerId;
            $.nameOnCard = "TEST NAME";
            $.expiry = "10/99";
        }).buildCreditCard();
    }

    public static Customer createDefaultCustomerWithCreditCards() {
        Customer customer = createDefaultCustomer();
        customer.addCreditCard(createCreditCardWithCustomCardNumberAndCustomerId("1234567890", customer.id()));
        customer.addCreditCard(createCreditCardWithCustomCardNumberAndCustomerId("0987654321", customer.id()));
        return customer;
    }

    public static AddCreditCardCommand createAddCreditCardCommand() {
        AddCreditCardCommand addCreditCardCommand = new AddCreditCardCommand();
        addCreditCardCommand.setActive(true);
        addCreditCardCommand.setCardNumber("77777777777");
        addCreditCardCommand.setCustomerId("1");
        addCreditCardCommand.setExpiry("10/99");
        addCreditCardCommand.setNameOnCard("TEST NAME");
        return addCreditCardCommand;
    }

    public static Set<PurchaseItem> createDefaultPurchaseItemsList() {
        PurchaseOrderId purchaseOrderId = new PurchaseOrderId("1");
        ProductId firstProduct = new ProductId("1");
        ProductId secondProduct = new ProductId("1");
        List<PurchaseItem> list = Arrays.asList(new PurchaseItem(firstProduct, purchaseOrderId, 5), new PurchaseItem(secondProduct, purchaseOrderId, 5));
        return new HashSet<>(list);
    }

    public static PurchaseOrder createDefaultPurchaseOrder() {
        PurchaseOrderId defaultPurchaseOrderId = new PurchaseOrderId("1");
        CustomerId defaultCustomerId = new CustomerId("1");
        Set<PurchaseItem> defaultItemsSet = TestDomainObjectsFactory.createDefaultPurchaseItemsList();
        return new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = defaultItemsSet;
        }).buildPurchaseOrder();
    }

    public static CheckOutData createDefaultCheckOutData() {
        return new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.creditCardNumber = "1234567890";
        }).buildCheckOutData();
    }

    public static CheckOutData createCheckOutDataWithCustomCreditCardNumber(String creditCardNumber) {
        return new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.creditCardNumber = creditCardNumber;
        }).buildCheckOutData();
    }

    public static CartCheckOutCommand createCartCheckOutCommand() {
        CartCheckOutCommand cartCheckOutCommand = new CartCheckOutCommand();
        cartCheckOutCommand.setCreditCardNumber("1234567890");
        cartCheckOutCommand.setDeliveryAddress("Delivery Address");
        cartCheckOutCommand.setDeliveryAddressNotes("Delivery Address Notes");
        cartCheckOutCommand.setInvoiceAddress("Invoice Address");
        cartCheckOutCommand.setCustomerId("1");
        return cartCheckOutCommand;
    }
}
