package com.example.demo.procurement.domain.repository;

import com.example.demo.procurement.domain.model.PlantHireRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantHireRequestRepository extends JpaRepository<PlantHireRequest, Long> {
    PlantHireRequest findById(long id);

    @Query("select phr from PlantHireRequest phr where phr.purchaseOrder.href = ?1")
    PlantHireRequest findPHRbyRef(String ref);
}
