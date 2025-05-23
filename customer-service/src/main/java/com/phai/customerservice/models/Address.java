package com.phai.customerservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "tbl_address")
public class Address {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;
    private String customerId;

    @NotBlank(message = "Village cannot be null")
    private String village;

    @NotBlank(message = "Commune cannot be null")
    private String commune;

    @NotBlank(message = "District cannot be null")
    private String district;

    @NotBlank(message = "Province cannot be null")
    private String province;

    @NotBlank(message = "Country cannot be null")
    private String country;

    private String description;
    private String postalCode;

}
