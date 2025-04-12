package com.C_TechProject.bank;

import com.C_TechProject.bankAccount.BankAccount;
import com.C_TechProject.bankAccount.BankAccountRepository;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankService {
private final BankRepository bankRepository;
    private final BankAccountRepository bankAccountRepository;


    public Bank addBank(Bank bank) {

    return bankRepository.save(bank);
    }
    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    public Bank findBankById(Integer id) {
        Optional<Bank> bank = bankRepository.findById(id);
        if (bank.isPresent()) {
            return bank.get();
        } else {
            throw new RuntimeException("Bank not found with ID: " + id);
        }
    }
    public Bank updateBank(Integer id, Bank newBank) {
        Bank bank = bankRepository.findBankById(id)
                .orElseThrow(() -> new UserNotFoundException("Bank not found with ID: " + id));

        if (newBank.getCode() != null && !newBank.getCode().equals(bank.getCode())) {
            List<Bank> banksWithSameCode = bankRepository.findBankByCode(newBank.getCode());
            banksWithSameCode.removeIf(b -> b.getId().equals(id));
            if (!banksWithSameCode.isEmpty()) {
                throw new UserNotFoundException("Bank code already exists in other banks");
            }
            bank.setCode(newBank.getCode());
        }
        if (newBank.getNameBanque() != null && !newBank.getNameBanque().equals(bank.getNameBanque())) {
            List<Bank> banksWithSameName = bankRepository.findBankByNameBanque(newBank.getNameBanque());
            banksWithSameName.removeIf(b -> b.getId().equals(id));
            if (!banksWithSameName.isEmpty()) {
                throw new UserNotFoundException("Bank name already exists in other banks");
            }
            bank.setNameBanque(newBank.getNameBanque());
        }

        bankRepository.save(bank);
        return bank;
    }

    public void deleteBank(Integer id) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Bank not found with ID: " + id));

        List<BankAccount> bankAccounts = bankAccountRepository.findByBank(bank);
        if (!bankAccounts.isEmpty()) {
            throw new RuntimeException("Cannot delete bank with related bank accounts.");
        }

        bankRepository.delete(bank);
    }




}
