package com.C_TechProject.Tier;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonnePhysiqueRepository extends JpaRepository<PersonPhysique,Integer> {
    Optional<PersonPhysique> findPersonneById(Integer id);
    PersonPhysique findByCin(String cin);
    PersonPhysique findByRib(String rib);

}
