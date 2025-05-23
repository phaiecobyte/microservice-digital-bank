package com.phai.customerservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Data
@Entity(name = "tbl_customer")
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false)
    private String customerId;

    /**
     * Personal Information
     */


    @NotBlank(message = "Phone cannot be blank")
    @Column(length = 25,unique = true)
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please provide a valid email address")
    @Column(length = 100,unique = true)
    private String email;

    @NotBlank(message = "Marital status cannot be blank")
    @Column(length = 25)
    private String maritalStatus;

    @Column(nullable = true)
    private String imgUrl;
    @Column(nullable = true)
    private String referralCode;

    /**
     * Financial Information
     */
    @NotBlank(message = "Source income cannot be blank")
    @Column(length = 55)
    private String sourceIncome;

    @NotBlank(message = "monthly income cannot be blak")
    private String monthlyIncome;

    @NotBlank(message = "Field of work cannot be blank")
    @Column(length = 55)
    private String fieldOfWork;

    @NotNull
    @NotBlank(message = "Occupation cannot be blank")
    @Column(length = 55)
    private String occupation;

    @NotBlank(message = "Purpose cannot be blank")
    @Column(length = 55)
    private String purpose;

    /**
     * Audit Information
     */
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private String status;

    /**
     * Relationship
     */
    // one customer can have one active identity card
    // expired identity card will be inactive but still exist in the database
    @OneToOne(cascade = CascadeType.ALL)
    private IdentityCard identityCard;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    private PlaceOfBirth placeOfBirth;
}
