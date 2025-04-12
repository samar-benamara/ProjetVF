package com.C_TechProject.legalEntity;


import com.C_TechProject.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LegalEntityRepository extends JpaRepository<LegalEntity,Integer> {

    Optional<LegalEntity> findLegalEntityById(Integer id);
    List<LegalEntity> findEntityByCode(String code);
    List <LegalEntity> findEntityByNameEntity(String nameBanque);
    LegalEntity findByNameEntity(String nameBanque);


}
