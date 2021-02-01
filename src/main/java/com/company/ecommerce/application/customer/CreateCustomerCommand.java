package com.company.ecommerce.application.customer;

public class CreateCustomerCommand {

    private String firstName;
    private String lastName;
    private String email;
    private long countryId;
    private String id;

    public CreateCustomerCommand() {
        super();
    }

    public CreateCustomerCommand(String firstName, String lastName, String email, long countryId, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.countryId = countryId;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
