package com.C_TechProject.Tier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneMoraleRepository extends JpaRepository<PersonneMorale, Integer> {
    PersonneMorale findByCode(String code);
    PersonneMorale findByRib(String rib);
}
