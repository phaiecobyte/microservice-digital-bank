package com.phai.customerinfoservice.services;

import com.phai.customerinfoservice.exception.AppException;
import com.phai.customerinfoservice.models.Customer;
import com.phai.customerinfoservice.models.IdentityCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer register(Customer customer) throws AppException;
    Customer updateIdentityCard(String customerId, IdentityCard newIdCard) throws AppException;
    List<IdentityCard> getIdentityCardByCustomer(String customerId) throws AppException;
    Optional<Customer> getCustomerById(String customerId) throws AppException;
    Page<Customer> getAllCustomer(Pageable pageable) throws AppException;
    Customer updateProfilePhoto(String customerId, MultipartFile photo) throws AppException;
}