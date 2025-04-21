
package com.C_TechProject.Operation;

import com.C_TechProject.Tier.PersonMorale;
import com.C_TechProject.Tier.PersonPhysique;
import com.C_TechProject.bankAccount.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OperationRepositoryImpl implements IOperationRepository {

    private final OperationRepository operationRepository;


    @Override
    public Optional<Operation> findOperationById(Integer id) {
        return operationRepository.findOperationById(id);
    }

    @Override
    public List<Operation> findByBankAccount(BankAccount bankAccount) {
        return operationRepository.findByBankAccount(bankAccount);
    }

    @Override
    public List<Operation> findByPersonneMorale(PersonMorale personneMorale) {
        return operationRepository.findByPersonneMorale(personneMorale);
    }

    @Override
    public List<Operation> findByPersonnePhysique(PersonPhysique personPhysique) {
        return operationRepository.findByPersonnePhysique(personPhysique);
    }

    @Override
    public Operation save(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public void deleteById(Integer id) {
        operationRepository.deleteById(id);
    }
}
