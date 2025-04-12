package com.C_TechProject.bankAccount;

import com.C_TechProject.bank.Bank;
import com.C_TechProject.legalEntity.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount,Integer> {

    Optional<BankAccount> findBankAccountById(Integer id);

    List<BankAccount> findByBank(Bank bank);
    List<BankAccount> findByLegalEntity(LegalEntity legalEntity);

    List<BankAccount> findByRib(String rib);
 BankAccount findBankAccountByRib(String rib);

}
