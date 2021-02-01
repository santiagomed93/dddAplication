package com.company.ecommerce.application.customer;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.customer.CreditCard;
import com.company.ecommerce.domain.customer.Customer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerTransformer {

    private static final Transformer<CreditCard, CreditCardDto> toCreditCardDto = creditCard -> {
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setActive(creditCard.isActive());
        creditCardDto.setCardNumber(creditCard.cardNumber());
        creditCardDto.setCustomerId(creditCard.customerId().id());
        creditCardDto.setExpiry(creditCard.expire());
        creditCardDto.setNameOnCard(creditCard.nameOnCard());
        return creditCardDto;
    };

    private static final Transformer<Set<CreditCard>, Set<CreditCardDto>> toCreditCardDtoList = creditCardList -> creditCardList.stream()
            .map(toCreditCardDto::transform)
            .collect(Collectors.toSet());

    public static final Transformer<Customer, CustomerDto> toCustomerDto = customer -> {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.id().id());
        customerDto.setCountryId(customer.country().id());
        customerDto.setEmail(customer.email());
        customerDto.setFirstName(customer.name().firstName());
        customerDto.setLastName(customer.name().lastName());
        customerDto.setFullName(customer.name().fullName());
        Set<CreditCardDto> creditCardDtoList = toCreditCardDtoList.transform(customer.creditCards());
        customerDto.setCreditCards(creditCardDtoList);
        return customerDto;
    };

    public static final Transformer<List<Customer>, List<CustomerDto>> toCustomerDtoList = customerList -> customerList.stream()
            .map(toCustomerDto::transform)
            .collect(Collectors.toList());

}
