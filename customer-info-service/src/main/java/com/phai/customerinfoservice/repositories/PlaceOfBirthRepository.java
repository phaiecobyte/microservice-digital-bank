package com.phai.customerinfoservice.repositories;

import com.phai.customerinfoservice.models.PlaceOfBirth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceOfBirthRepository extends JpaRepository<PlaceOfBirth,Long> {
}
