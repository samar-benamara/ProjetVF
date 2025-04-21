package com.C_TechProject.Operation;

import com.C_TechProject.Bordereau.Bordereau;
import com.C_TechProject.Tier.PersonMorale;
import com.C_TechProject.Tier.PersonPhysique;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bankAccount.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository <Operation,Integer>  {
    Optional<Operation> findOperationById(Integer id);


    List<Operation> findByBankAccount(BankAccount bankAccount);
    List<Operation> findByPersonneMorale(PersonMorale personneMorale);
    List<Operation> findByPersonnePhysique(PersonPhysique personnePhysique);




}
