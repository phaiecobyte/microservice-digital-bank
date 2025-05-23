package com.phai.customerservice.repositories;

import com.phai.customerservice.models.IdentityCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IdentityCardRepository extends JpaRepository<IdentityCard,Long> {
    boolean existsByIdNumber(String idNumber);
    boolean existsByIdNumberAndCustomerId(String idNumber, String customerId);
    List<IdentityCard> findByStatusAndExpiryDateBefore(String status, LocalDate date);
    @Query("SELECT i FROM tbl_identity_card i WHERE i.customerId = :customerId ORDER BY i.id DESC")
    List<IdentityCard> findAllByCustomerIdOrderByIdDesc(@Param("customerId") String customerId);
    IdentityCard findByCustomerIdAndStatus(String customerId, String status);
}
