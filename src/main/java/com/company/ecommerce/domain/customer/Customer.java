package com.company.ecommerce.domain.customer;

import com.company.ecommerce.domain.Aggregate;
import com.company.ecommerce.domain.DomainUtils;
import com.company.ecommerce.domain.country.Country;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;
import java.util.function.Predicate;

@Entity
public class Customer implements Aggregate {
    @EmbeddedId
    private CustomerId id;
    @Embedded
    private Name name;
    private String email;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    private Country country;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customerId")
    private Set<CreditCard> creditCards;

    public Customer() {
        super();
    }

    Customer(CustomerId customerId, Name name, String email, Country country) {
        setId(customerId);
        setEmail(email);
        setCountry(country);
        setName(name);
        creditCards = new HashSet<>();
    }

    public void addCreditCard(CreditCard creditCard) {
        findCreditCardByCardNumber(creditCard.cardNumber()).ifPresent(card -> {
            throw new IllegalArgumentException(String.format("The card with " +
                    "number %s is already added for the customer", creditCard.cardNumber()));
        });
        creditCards.add(creditCard);
    }

    public void removeCreditCardByNumber(String cardNumber) {
        CreditCard currentCard = findCreditCardByCardNumber(cardNumber).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("The card with " +
                    "number %s does not exist", cardNumber));
        });
        creditCards.remove(currentCard);
    }

    private Optional<CreditCard> findCreditCardByCardNumber(String cardNumber) {
        return creditCards.stream().filter(sameCardNumber(cardNumber)).findFirst();
    }

    private Predicate<CreditCard> sameCardNumber(String cardNumber) {
        return card -> card.cardNumber().equals(cardNumber);
    }

    private void setId(CustomerId id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Customer Id is Required");
        }
        this.id = id;
    }

    private void setName(Name name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("Customer Name is Required");
        }
        this.name = name;
    }

    private void setEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException("Email is Required");
        } else if (DomainUtils.validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email format is not ok");
        }
    }

    private void setCountry(Country country) {
        if (Objects.isNull(country)) {
            throw new IllegalArgumentException("Country is Required");
        }
        this.country = country;
    }

    public Name name() {
        if (Objects.isNull(name)) {
            return null;
        }
        return new Name(name.firstName(), name.lastName());
    }

    public String email() {
        return email;
    }

    public Country country() {
        if (Objects.isNull(country)) {
            return null;
        }
        return new Country(country.id(), country.name());
    }

    public CustomerId id() {
        if (Objects.isNull(id)) {
            return null;
        }
        return new CustomerId(id.id());
    }

    public Set<CreditCard> creditCards() {
        if (Objects.isNull(creditCards)) {
            return null;
        }
        return Collections.unmodifiableSet(creditCards);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
