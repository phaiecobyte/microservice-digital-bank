package com.phai.customerservice.validation;

import com.phai.customerservice.exception.AppException;
import com.phai.customerservice.models.Customer;
import com.phai.customerservice.models.IdentityCard;
import com.phai.customerservice.repositories.CustomerRepository;
import com.phai.customerservice.repositories.IdentityCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerValidator {
    private final CustomerRepository customerRepository;
    private final IdentityCardRepository identityCardRepository;

    public void validateForRegistration(Customer customer){
        if(customer == null){
            throw new AppException("Customer cannot be null");
        }
        validateCustomerId(customer);
        validateEmail(customer);
        validatePhone(customer);
        validateIdentityCard(customer);
    }

    private void validateCustomerId(Customer customer){
        if(customer.getCustomerId() != null){
            if(customerRepository.existsCustomerByCustomerId(customer.getCustomerId())){
                log.error("CustomerId is taken {}", customer.getCustomerId());
                throw new AppException("CustomerId is taken");
            }
        }
    }
    private void validateEmail(Customer customer){
        if(customer.getEmail() != null){
            if(customerRepository.existsByEmail(customer.getEmail())){
                log.error("Email is already exists");
                throw new AppException("Email is already exists");
            }
        }
    }
    private void validatePhone(Customer customer){
        // validate phone number pattern
        if(customer.getPhone() != null && !customer.getPhone().matches("^[0-9]+$")){
            log.error("Invalid phone number format");
            throw new AppException("Invalid phone number format");
        }
        
        // check if phone number already exists
        if(customer.getPhone() != null){
            if(customerRepository.existsByPhone(customer.getPhone())){
                log.error("Phone is already exists");
                throw new AppException("Phone is already exists");
            }
        }
    }
    private void validateIdentityCard(Customer customer){
        IdentityCard identityCard = customer.getIdentityCard();
        if(identityCard == null){
            return;
        }
        if(identityCard.getIdNumber() != null){
            if(identityCardRepository.existsByIdNumber(identityCard.getIdNumber())){
                log.error("Identity card already used with another customer:{}", identityCard.getIdNumber());
                throw new AppException("Identity card already used with another customer");
            }
        }

        if(identityCard.getExpiryDate() != null){
            LocalDate today = LocalDate.now();
            if(identityCard.getExpiryDate().isBefore(today)){
                log.error("Identity card expired on: {}", identityCard.getExpiryDate());
                throw new AppException("Identity card expired");
            }
        }
    }
}
