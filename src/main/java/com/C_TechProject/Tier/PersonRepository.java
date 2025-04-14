package com.C_TechProject.Tier;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
// ===== GOF Abstract : Interface générique pour tous types de Person =====

public interface PersonRepository<T extends Person> extends JpaRepository<T, Integer> {
    Optional<T> findByRib(String rib);
}
