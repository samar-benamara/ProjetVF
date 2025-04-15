package com.C_TechProject.Tier;


import java.util.Optional;


public interface PersonnePhysiqueRepository extends PersonRepository<PersonPhysique> {
     Optional<PersonPhysique> findByCin(String cin);
}

