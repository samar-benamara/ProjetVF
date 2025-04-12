package com.C_TechProject.bank;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank,Integer> {
    Optional<Bank> findBankById(Integer id);
  List<Bank> findBankByCode(String code);
  List <Bank> findBankByNameBanque(String nameBanque);


    Bank findByNameBanque(String nameBanque);
}



