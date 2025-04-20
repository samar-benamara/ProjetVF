package com.C_TechProject.Operation;

import com.C_TechProject.Tier.PersonneMorale;
import com.C_TechProject.Tier.PersonnePhysique;
import com.C_TechProject.bankAccount.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository <Operation,Integer>  {
    Optional<Operation> findOperationById(Integer id);


    List<Operation> findByBankAccount(BankAccount bankAccount);
    List<Operation> findByPersonneMorale(PersonneMorale personneMorale);
    List<Operation> findByPersonnePhysique(PersonnePhysique personnePhysique);




}
