package com.C_TechProject.bank;

import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class BankController {

    public final BankService bankService;


    @PostMapping("/addbank")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
       Bank newBank=bankService.addBank(bank);
        return new ResponseEntity<>(newBank, HttpStatus.CREATED);
    }
    @GetMapping("/banks")
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks = bankService.findAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }
    @GetMapping("/bank/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Integer id) {
        try {
            Bank bank = bankService.findBankById(id);
            return ResponseEntity.ok(bank);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/updatebank/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Integer id, @RequestBody Bank bank) {
        try {
            Bank updatedBank = bankService.updateBank(id, bank);
            return ResponseEntity.ok(updatedBank);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/deletebank/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Integer id) {
        try {
            bankService.deleteBank(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }




}
