package com.company.ecommerce.presentation.customer;

import com.company.ecommerce.application.customer.*;
import com.company.ecommerce.presentation.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/customers")
    public void createCustomer(@RequestBody CustomerDto customerDto) {
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand();
        createCustomerCommand.setCountryId(customerDto.getCountryId());
        createCustomerCommand.setEmail(customerDto.getEmail());
        createCustomerCommand.setFirstName(customerDto.getFirstName());
        createCustomerCommand.setId(customerDto.getId());
        createCustomerCommand.setLastName(customerDto.getLastName());
        service.createCustomer(createCustomerCommand);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<GenericResponse<CustomerDto>> findCustomerById(@PathVariable String id) {
        CustomerDto customerDto = service.findCustomerById(id, CustomerTransformer.toCustomerDto);
        GenericResponse<CustomerDto> response = new GenericResponse<>(customerDto, "Customer Found");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        service.deleteCustomerById(id);
    }

    @GetMapping("/customers")
    public ResponseEntity<GenericResponse<List<CustomerDto>>> findAllCustomers() {
        List<CustomerDto> customerDtoList = service.findAllCostumers(CustomerTransformer.toCustomerDtoList);
        GenericResponse<List<CustomerDto>> response = new GenericResponse<>(customerDtoList, "Here are all the customers");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/customers/{id}/credit-cards")
    public void addCreditCard(@RequestBody CreditCardDto creditCardDto, @PathVariable String id) {
        AddCreditCardCommand addCreditCardCommand = new AddCreditCardCommand();
        addCreditCardCommand.setCardNumber(creditCardDto.getCardNumber());
        addCreditCardCommand.setActive(creditCardDto.isActive());
        addCreditCardCommand.setCustomerId(creditCardDto.getCustomerId());
        addCreditCardCommand.setExpiry(creditCardDto.getExpiry());
        addCreditCardCommand.setNameOnCard(creditCardDto.getNameOnCard());
        service.addCreditCard(addCreditCardCommand);
    }

    @DeleteMapping("/customers/{id}/credit-cards/{cardNumber}")
    public void removeCreditCardByNumber(@PathVariable String id, @PathVariable String cardNumber) {
        service.removeCreditCardByNumber(id, cardNumber);
    }
}
