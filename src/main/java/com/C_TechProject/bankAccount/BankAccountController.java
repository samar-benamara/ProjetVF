package com.C_TechProject.bankAccount;

import com.C_TechProject.bank.Bank;
import com.C_TechProject.bank.BankService;
import com.C_TechProject.legalEntity.LegalEntity;
import com.C_TechProject.legalEntity.LegalEntityService;
import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankService bankService;
    private final LegalEntityService legalEntityService;


    @PostMapping("/addbankaccount")
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            BankAccount newBankAccount = bankAccountService.addBankAccount(bankAccount);
            return new ResponseEntity<>(newBankAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updatebankaccount/{id}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable Integer id, @RequestBody BankAccount bankAccount) {
        try {
            BankAccount updatedBankAccount = bankAccountService.updateBankAccount(id, bankAccount);
            return ResponseEntity.ok(updatedBankAccount);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ArrayStoreException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


        @DeleteMapping("/deletebankaccount/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Integer id) {
        try {
            bankAccountService.deleteBankAccount(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @GetMapping("/bankaccount/{id}")
    public ResponseEntity<BankAccount> getBankAccountById(@PathVariable Integer id) {
        try {
            BankAccount bankAccount = bankAccountService.findBankAccountById(id);
            return ResponseEntity.ok(bankAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        List<BankAccount> Accounts = bankAccountService.findAllAccounts();
        return new ResponseEntity<>(Accounts, HttpStatus.OK);
    }
}
