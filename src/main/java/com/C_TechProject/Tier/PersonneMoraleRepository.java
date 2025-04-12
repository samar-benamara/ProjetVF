package com.C_TechProject.Tier;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonneMoraleRepository extends JpaRepository<PersonMorale,Integer> {
    Optional<PersonMorale> findCompanyById(Integer id);
    PersonMorale findByCode(String code);
    PersonMorale findByRib(String rib);
}
