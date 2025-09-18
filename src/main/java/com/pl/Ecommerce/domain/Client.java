package com.pl.Ecommerce.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ClientId;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits")
    private String phoneNumber;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @Nullable
    private String apartmentNumber;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be in the format XX-XXX")
    private String postalCode;
}
