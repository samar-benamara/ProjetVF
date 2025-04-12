package com.C_TechProject.bankAccount;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.Operation.OperationRepository;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bank.BankRepository;
import com.C_TechProject.legalEntity.LegalEntity;
import com.C_TechProject.legalEntity.LegalEntityRepository;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;
    private final LegalEntityRepository legalEntityRepository;

    private final OperationRepository operationRepository;

    public BankAccount addBankAccount(BankAccount bankAccount) {
        Bank bank = bankAccount.getBank();
        LegalEntity legalEntity = bankAccount.getLegalEntity();
        String rib = bankAccount.getRib();


        Optional<Bank> optionalBank = bankRepository.findById(bank.getId());
        if (optionalBank.isEmpty()) {
            throw new RuntimeException("Bank not found");
        }
        Bank existingBank = optionalBank.get();

        Optional<LegalEntity> optionalLegalEntity = legalEntityRepository.findById(legalEntity.getId());
        if (optionalLegalEntity.isEmpty()) {
            throw new RuntimeException("Legal entity not found");
        }
        LegalEntity existingLegalEntity = optionalLegalEntity.get();

        String bankCode = existingBank.getCode();
        if (!rib.substring(0, 2).equals(bankCode)) {
            throw new RuntimeException("Invalid RIB: First two characters do not match bank code");
        }
        BankAccount newBankAccount = new BankAccount(rib, existingBank, existingLegalEntity);
        return bankAccountRepository.save(newBankAccount);
    }
    public BankAccount updateBankAccount(Integer id, BankAccount newBankAccount) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Bank account not found with ID: " + id));
        String newRib = newBankAccount.getRib();
        String bankCode = newBankAccount.getBank().getCode();

        List<BankAccount> existingBankAccounts = bankAccountRepository.findByRib(newRib);
        existingBankAccounts.removeIf(ba -> ba.getId().equals(id));
        if (!existingBankAccounts.isEmpty()) {
            throw new RuntimeException("Error updating bank account: RIB already exists in another bank account");
        }

        // Check if the first two characters of the RIB match the bank code
        if (!newRib.startsWith(bankCode)) {
            throw new ArrayStoreException("Error updating bank account: RIB must start with the bank code");
        }

        // Update the fields of the bank account
        bankAccount.setRib(newRib);
        // Update the fields of the bank account
        bankAccount.setRib(newBankAccount.getRib());

        // Update the bank
        Bank newBank = newBankAccount.getBank();
        Optional<Bank> optionalBank = bankRepository.findById(newBank.getId());
        if (optionalBank.isEmpty()) {
            throw new UserNotFoundException("Bank not found");
        }
        Bank existingBank = optionalBank.get();
        bankAccount.setBank(existingBank);

        // Update the legal entity
        LegalEntity newLegalEntity = newBankAccount.getLegalEntity();
        Optional<LegalEntity> optionalLegalEntity = legalEntityRepository.findById(newLegalEntity.getId());
        if (optionalLegalEntity.isEmpty()) {
            throw new UserNotFoundException("Legal entity not found");
        }
        LegalEntity existingLegalEntity = optionalLegalEntity.get();
        bankAccount.setLegalEntity(existingLegalEntity);

        // Save the updated bank account
        return bankAccountRepository.save(bankAccount);
    }


    public void deleteBankAccount(Integer id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Bank account not found with ID: " + id));

        List<Operation> operations = operationRepository.findByBankAccount(bankAccount);
        if (!operations.isEmpty()) {
            throw new RuntimeException("Cannot delete bank account with related operations.");
        }

        bankAccountRepository.delete(bankAccount);
    }

    public BankAccount findBankAccountById(Integer id) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        if (bankAccount.isPresent()) {
            return bankAccount.get();
        } else {
            throw new RuntimeException("Bank account not found with ID: " + id);
        }
    }


    public List<BankAccount> findAllAccounts() {
        return bankAccountRepository.findAll();
    }
}
