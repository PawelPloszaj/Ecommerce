package com.pl.Ecommerce.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateClientRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits")
    private String phoneNumber;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    private String apartmentNumber;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be XX-XXX")
    private String postalCode;
}
