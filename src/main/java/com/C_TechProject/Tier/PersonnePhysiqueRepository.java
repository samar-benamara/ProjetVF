package com.C_TechProject.Tier;


import java.util.Optional;

// ===== GOF Abstract : Interface spécialisée pour PersonPhysique =====

public interface PersonnePhysiqueRepository extends PersonRepository<PersonPhysique> {
    static Optional<PersonPhysique> findByCin(String cin);
}

