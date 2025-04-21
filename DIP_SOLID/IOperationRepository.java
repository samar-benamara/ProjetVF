

package com.C_TechProject.Operation;

import com.C_TechProject.Bordereau.Bordereau;
import com.C_TechProject.Tier.PersonMorale;
import com.C_TechProject.Tier.PersonPhysique;
import com.C_TechProject.bankAccount.BankAccount;

import java.util.List;
import java.util.Optional;

public interface IOperationRepository {
    Optional<Operation> findOperationById(Integer id);
    List<Operation> findByBankAccount(BankAccount bankAccount);
    List<Operation> findByPersonneMorale(PersonMorale personneMorale);
    List<Operation> findByPersonnePhysique(PersonPhysique personnePhysique);
    Operation save(Operation operation);
    void deleteById(Integer id);
}
