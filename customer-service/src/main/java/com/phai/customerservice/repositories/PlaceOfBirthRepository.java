package com.phai.customerservice.repositories;

import com.phai.customerservice.models.PlaceOfBirth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceOfBirthRepository extends JpaRepository<PlaceOfBirth,Long> {
}
