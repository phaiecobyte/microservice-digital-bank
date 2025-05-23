package com.phai.customerservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;


@Data
@Entity(name = "tbl_identity_card")
public class IdentityCard {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    private String customerId;

    @NotBlank(message = "Identity card type cannot be blank")
    private String idType;

    @NotBlank(message = "Identity card number cannot be blank")
    private String idNumber;

    private LocalDate expiryDate;

    @NotBlank(message = "surname in Khmer cannot be blank")
    private String surNameKh;

    @NotBlank(message = "given name in Khmer cannot be blank")
    private String givenNameKh;

    @NotBlank(message = "surname in English cannot be blank")
    private String sureNameEn;

    @NotBlank(message = "given name in English cannot be blank")
    private String givenNameEn;

    @NotBlank(message = "gender cannot be blank")
    private String gender;

    @NotBlank(message = "nationality cannot be blank")
    private String nationality;

    @NotBlank(message = "document cannot be blank")
    private String documentUrl;

    private String status;
}
