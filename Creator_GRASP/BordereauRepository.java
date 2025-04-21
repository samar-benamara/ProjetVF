package com.C_TechProject.Bordereau;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BordereauRepository extends JpaRepository<Bordereau,Integer> {
    Bordereau getBordereauById(Integer id);

}
