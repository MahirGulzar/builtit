package com.example.demo.procurement.domain.repository;

import com.example.demo.procurement.domain.model.ConstructionSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionSiteRepository extends JpaRepository<ConstructionSite, Long> {
}
