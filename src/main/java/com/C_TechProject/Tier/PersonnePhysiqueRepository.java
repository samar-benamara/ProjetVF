package com.C_TechProject.Tier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnePhysiqueRepository extends JpaRepository<PersonnePhysique, Integer> {
    PersonnePhysique findByCin(String cin);
    PersonnePhysique findByRib(String rib);
}
