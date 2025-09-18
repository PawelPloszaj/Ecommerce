package com.pl.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String city;
    private String country;
    private String postalCode;

}
