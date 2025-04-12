package com.C_TechProject.Operation;

import com.C_TechProject.Tier.PersonMorale;
import com.C_TechProject.Tier.PersonPhysique;
import com.C_TechProject.Tier.PersonneMoraleRepository;
import com.C_TechProject.Tier.PersonnePhysiqueRepository;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bank.BankRepository;
import com.C_TechProject.bankAccount.BankAccount;
import com.C_TechProject.bankAccount.BankAccountRepository;
import com.C_TechProject.legalEntity.LegalEntity;
import com.C_TechProject.legalEntity.LegalEntityRepository;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OperationService {
    private final OperationRepository operationRepository;
    private final BankRepository bankRepository;
    private final BankAccountRepository bankAccountRepository;
    private final LegalEntityRepository legalEntityRepository;
    private final PersonnePhysiqueRepository personnePhysiqueRepository;
    private final PersonneMoraleRepository personneMoraleRepository;

    public OperationService(OperationRepository operationRepository,
                            BankRepository bankRepository,
                            BankAccountRepository bankAccountRepository,
                            LegalEntityRepository legalEntityRepository,
                            PersonnePhysiqueRepository personnePhysiqueRepository,
                            PersonneMoraleRepository personneMoraleRepository) {
        this.operationRepository = operationRepository;
        this.bankRepository = bankRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.legalEntityRepository = legalEntityRepository;
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.personneMoraleRepository = personneMoraleRepository;
    }

    public Operation addOperation(OperationRequest operation) throws Exception {
        if (operation.getType() == null || operation.getEtat() == null || operation.getReglement() == null ||
                operation.getBank() == null || operation.getLegalEntity() == null || operation.getBankAccount() == null) {
            throw new IllegalArgumentException("Missing required fields in the Operation entity");
        }

        Bank bank = bankRepository.findByNameBanque(operation.getBank());
        LegalEntity legalEntity = legalEntityRepository.findByNameEntity(operation.getLegalEntity());
        BankAccount bankAccount = bankAccountRepository.findBankAccountByRib(operation.getBankAccount());
        PersonPhysique personPhysique = operation.getPersonnePhysique() != null ?
                personnePhysiqueRepository.findByCin(operation.getPersonnePhysique()) : null;
        PersonMorale personMorale = operation.getPersonneMorale() != null ?
                personneMoraleRepository.findByCode(operation.getPersonneMorale()) : null;

        Operation operationEntity = new Operation();
        operationEntity.setType(operation.getType());
        operationEntity.setEtat(operation.getEtat());
        operationEntity.setMontant(operation.getMontant());
        operationEntity.setReglement(operation.getReglement());
        operationEntity.setNumcheque(operation.getNumcheque());
        operationEntity.setBank(bank);
        operationEntity.setLegalEntity(legalEntity);
        operationEntity.setBankAccount(bankAccount);
        operationEntity.setPersonnePhysique(personPhysique);
        operationEntity.setPersonneMorale(personMorale);

        Operation savedOperation = operationRepository.save(operationEntity);
        return savedOperation;
    }

    public OperationResponse findOperationById(Integer id) {
        Optional<Operation> operation = operationRepository.findById(id);
        if (operation.isPresent()) {
            Operation op = operation.get();
            OperationResponse response = new OperationResponse();
            response.setId(op.getId());
            response.setType(op.getType());
            response.setEtat(op.getEtat());
            response.setMontant(op.getMontant());
            response.setReglement(op.getReglement());
            response.setNumcheque(op.getNumcheque() != null ? op.getNumcheque() : null);
            response.setBank(op.getBank().getNameBanque());
            response.setLegalEntity(op.getLegalEntity().getNameEntity());
            response.setBankAccount(op.getBankAccount().getRib());
            response.setPersonnePhysique(op.getPersonnePhysique() != null ? op.getPersonnePhysique().getCin() : null);
            response.setPersonneMorale(op.getPersonneMorale() != null ? op.getPersonneMorale().getCode() : null);
            response.setCreationDate(String.valueOf(op.getCreationDate()));

            return response;
        } else {
            throw new RuntimeException("Operation not found with ID: " + id);
        }
    }

    public List<OperationResponse> findAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        return operations.stream()
                .map(operation -> {
                    OperationResponse response = new OperationResponse();
                    response.setId(operation.getId());
                    response.setType(operation.getType());
                    response.setEtat(operation.getEtat());
                    response.setMontant(operation.getMontant());
                    response.setReglement(operation.getReglement());
                    response.setNumcheque(operation.getNumcheque());
                    response.setBank(operation.getBank().getNameBanque());
                    response.setLegalEntity(operation.getLegalEntity().getNameEntity());
                    response.setBankAccount(operation.getBankAccount().getRib());
                    response.setPersonnePhysique(operation.getPersonnePhysique() != null ?
                            operation.getPersonnePhysique().getCin() : null);
                    response.setPersonneMorale(operation.getPersonneMorale() != null ?
                            operation.getPersonneMorale().getCode() : null);
                    response.setCreationDate(String.valueOf(operation.getCreationDate()));

                    return response;
                })
                .collect(Collectors.toList());
    }

    public void deleteoperation(Integer id) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Operation not found with ID: " + id));
        operationRepository.delete(operation);
    }

    public Operation updateOperation(Integer id, OperationRequest newOperation) throws Exception {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Operation not found with ID: " + id));

        if (newOperation.getType() != null && !newOperation.getType().equals(operation.getType())) {
            operation.setType(newOperation.getType());
        }
        if (newOperation.getReglement() != null && !newOperation.getReglement().equals(operation.getReglement())) {
            operation.setReglement(newOperation.getReglement());
        }
        if (newOperation.getEtat() != null && !newOperation.getEtat().equals(operation.getEtat())) {
            operation.setEtat(newOperation.getEtat());
        }
        if (newOperation.getNumcheque() != null && !newOperation.getNumcheque().equals(operation.getNumcheque())) {
            operation.setNumcheque(newOperation.getNumcheque());
        }

        if (newOperation.getBank() != null && !newOperation.getBank().equals(operation.getBank().getNameBanque())) {
            Bank bank = bankRepository.findByNameBanque(newOperation.getBank());
            operation.setBank(bank);
        }
        if (newOperation.getLegalEntity() != null && !newOperation.getLegalEntity().equals(operation.getLegalEntity().getNameEntity())) {
            LegalEntity legalEntity = legalEntityRepository.findByNameEntity(newOperation.getLegalEntity());
            operation.setLegalEntity(legalEntity);
        }
        if (newOperation.getBankAccount() != null && !newOperation.getBankAccount().equals(operation.getBankAccount().getRib())) {
            BankAccount bankAccount = bankAccountRepository.findBankAccountByRib(newOperation.getBankAccount());
            operation.setBankAccount(bankAccount);
        }
        if (newOperation.getPersonnePhysique() != null &&
                (operation.getPersonnePhysique() == null || !newOperation.getPersonnePhysique().equals(operation.getPersonnePhysique().getCin()))) {
            PersonPhysique personPhysique = personnePhysiqueRepository.findByCin(newOperation.getPersonnePhysique());
            operation.setPersonnePhysique(personPhysique);
        }
        if (newOperation.getPersonneMorale() != null &&
                (operation.getPersonneMorale() == null || !newOperation.getPersonneMorale().equals(operation.getPersonneMorale().getCode()))) {
            PersonMorale personMorale = personneMoraleRepository.findByCode(newOperation.getPersonneMorale());
            operation.setPersonneMorale(personMorale);
        }

        return operationRepository.save(operation);
    }
}