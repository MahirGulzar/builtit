package com.example.demo.procurement.domain.repository;

import com.example.demo.procurement.domain.model.PlantHireRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantHireRequestRepository extends JpaRepository<PlantHireRequest, Long> {
    PlantHireRequest findById(long id);
}
