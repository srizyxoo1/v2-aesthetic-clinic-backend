package com.v2aesthetic.backend.repository;

import com.v2aesthetic.backend.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}