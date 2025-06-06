package com.phai.customerinfoservice.repositories;

import com.phai.customerinfoservice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
   boolean existsCustomerByCustomerId(String customerId);
   boolean existsByEmail(String email);
   boolean existsByPhone(String phone);
   Optional<Customer> findByCustomerId(String customerId);
}
