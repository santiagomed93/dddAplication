package com.company.ecommerce.domain.email;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailMessageTest {

    @Test
    public void whenCreatingEmailMessageWithDefaultConstructor_thenAllPropertiesShouldBeNull() {
        EmailMessage emailMessage = new EmailMessage();

        assertThat(emailMessage.to()).isEqualTo(null);
        assertThat(emailMessage.body()).isEqualTo(null);
        assertThat(emailMessage.subject()).isEqualTo(null);
    }

    @Test
    public void whenCreatingEmailMessageWithValidData_thenAllPropertiesShouldNotBeNull() {
        EmailMessage emailMessage = new EmailMessage("h@h.com", "Email Test", "A message");

        assertThat(emailMessage.to()).isEqualTo("h@h.com");
        assertThat(emailMessage.body()).isEqualTo("A message");
        assertThat(emailMessage.subject()).isEqualTo("Email Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingEmailMessageWithNullTo_thenValidationShouldBeThrown() {
        new EmailMessage(null, "Email Test", "A message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingEmailMessageWithEmptyTo_thenValidationShouldBeThrown() {
        new EmailMessage("", "Email Test", "A message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingEmailMessageWithInvalidToEmail_thenValidationShouldBeThrown() {
        new EmailMessage("h@h", "Email Test", "A message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingEmailMessageWithNullSubject_thenValidationShouldBeThrown() {
        new EmailMessage("h@h.com", null, "A message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingEmailMessageWithEmptySubject_thenValidationShouldBeThrown() {
        new EmailMessage("h@h.com", "", "A message");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingEmailMessageWithNullBody_thenValidationShouldBeThrown() {
        new EmailMessage("h@h.com", "Email Test", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingEmailMessageWithEmptyBody_thenValidationShouldBeThrown() {
        new EmailMessage("h@h.com", "Email Test", "");
    }
}