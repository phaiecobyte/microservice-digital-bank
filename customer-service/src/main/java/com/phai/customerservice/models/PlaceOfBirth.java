package com.phai.customerservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "tbl_place_of_birth")
public class PlaceOfBirth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String customerId;

    @NotNull
    @NotBlank(message = "Village cannot be blank")
    private String village;

    @NotNull
    @NotBlank(message = "Commune cannot be blank")
    private String commune;

    @NotNull
    @NotBlank(message = "District cannot be blank")
    private String district;

    @NotNull
    @NotBlank(message="Province cannot be blank")
    private String province;

    @NotNull
    @NotBlank(message = "Country cannot be null")
    private String country;

    private String description;
}
