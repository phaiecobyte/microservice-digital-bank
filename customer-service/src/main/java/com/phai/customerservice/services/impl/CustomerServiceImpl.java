package com.phai.customerservice.services.impl;

import com.phai.customerservice.constant.Status;
import com.phai.customerservice.exception.AppException;
import com.phai.customerservice.models.Customer;
import com.phai.customerservice.models.IdentityCard;
import com.phai.customerservice.repositories.CustomerRepository;
import com.phai.customerservice.repositories.IdentityCardRepository;
import com.phai.customerservice.services.CustomerService;
import com.phai.customerservice.services.FileService;
import com.phai.customerservice.validation.CustomerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.phai.customerservice.util.Generator.generateUniqueCustomerId;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final IdentityCardRepository identityCardRepository;
    private final CustomerValidator customerValidator;
    private final IdentityCardServiceImpl identityCardServiceImpl;
    private final FileService fileService;

    @Transactional
    @Override
    public Customer register(Customer customer) throws AppException {
        log.info("Starting customer registration process");

        //Validate all business rules
        customerValidator.validateForRegistration(customer);

        //Generate customer ID if not provided
        if(customer.getCustomerId() == null || customer.getCustomerId() == ""){
            customer.setCustomerId(generateUniqueCustomerId());
        }

        //Set audit fields
        customer.setCreatedAt(LocalDateTime.now());
        customer.setStatus(Status.ACT.toString());

        //set relationship for identity card if present
        if(customer.getIdentityCard() != null){
            customer.getIdentityCard().setCustomerId(customer.getCustomerId());
            customer.getIdentityCard().setStatus(Status.ACT.toString());
        }
        if(customer.getAddress() != null){
            customer.getAddress().setCustomerId(customer.getCustomerId());
        }
        if(customer.getPlaceOfBirth() != null){
            customer.getPlaceOfBirth().setCustomerId(customer.getCustomerId());
        }

        //Save and return customer
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer register successfully with customer id: {}",customer.getCustomerId());
        return savedCustomer;
    }

    @Transactional
    @Override
    public Customer updateIdentityCard(String customerId, IdentityCard newIdCard){
        log.info("Updating identity card for customer:{}", customerId);

        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,
                        "Customer is not found with ID"+customerId));

        if(newIdCard.getExpiryDate() == null || newIdCard.getExpiryDate().isBefore(LocalDate.now())){
            throw new AppException("New identity card must have a valid expiration date");
        }
        if(identityCardRepository.existsByIdNumberAndCustomerId(newIdCard.getIdNumber(), customerId)){
            throw new AppException("Identity card number already used by another customer");
        }

        IdentityCard currentIdentityCard = customer.getIdentityCard();
        if(currentIdentityCard != null){
            identityCardServiceImpl.updateStatus(currentIdentityCard, Status.IAC);
            log.info("Marked previous identity card {} as inactive", currentIdentityCard.getIdNumber());
        }
        newIdCard.setCustomerId(customerId);
        newIdCard.setStatus(Status.ACT.toString());
        newIdCard = identityCardRepository.save(newIdCard);

        customer.setIdentityCard(newIdCard);
        customer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Identity card updated successfully for customer:{}", customerId);

        return updatedCustomer;
    }

    @Override
    public List<IdentityCard> getIdentityCardByCustomer(String customerId) throws AppException{
        log.info("Retrieving identity card by customer");
        customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->new AppException("Customer is not found"));
        List<IdentityCard> identityCards = identityCardRepository.findAllByCustomerIdOrderByIdDesc(customerId);
        log.info("Retrieved identity cards by customer");
        return identityCards;
    }

    @Override
    public Optional<Customer> getCustomerById(String customerId) throws AppException{
        log.info("Processing get customer by customer id {}", customerId);
        customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->new AppException("Customer not found"));

        log.info("Retrieved customer {}",customerId);
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public Page<Customer> getAllCustomer(Pageable pageable) throws AppException{
        log.info("Retrieving all customers");
        Page<Customer> customers = customerRepository.findAll(pageable);
        log.info("Retrieved all customers");
        return customers;
    }

    @Transactional
    @Override
    public Customer updateProfilePhoto(String customerId, MultipartFile photo) throws AppException {
        log.info("Updating profile photo for customer: {}", customerId);

        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,
                        "Customer not found with ID: " + customerId));

        // Delete old photo if exists
        if (customer.getImgUrl() != null && !customer.getImgUrl().isEmpty()) {
            try {
                fileService.deleteFile(customer.getImgUrl());
            } catch (Exception e) {
                log.warn("Failed to delete old profile photo: {}", e.getMessage());
                // Continue with uploading new photo even if deletion fails
            }
        }

        // Upload new photo
        String photoUrl = fileService.uploadFile(photo, "profile-photos");

        // Update customer
        customer.setImgUrl(photoUrl);
        customer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Profile photo updated successfully for customer: {}", customerId);

        return updatedCustomer;
    }
}
