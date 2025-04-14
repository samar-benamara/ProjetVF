package com.C_TechProject.Tier;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


import java.util.Optional;

// ===== GOF Abstract : Interface spécialisée pour PersonMorale =====

public interface PersonneMoraleRepository extends PersonRepository<PersonMorale> {
     Optional<PersonMorale> findByCode(String code);
}

