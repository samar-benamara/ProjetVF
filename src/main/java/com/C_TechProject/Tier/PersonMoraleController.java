package com.C_TechProject.Tier;

import com.C_TechProject.bank.Bank;
import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PersonMoraleController {
    public final PersonMoraleService personMoraleService;
    @PostMapping("/addcompany")
    public ResponseEntity<PersonMorale> addCompany(@RequestBody PersonMorale personMorale) {
        try {
            PersonMorale newCompany = personMoraleService.addCompany(personMorale);
            return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<PersonMorale> getCompanyById(@PathVariable Integer id) {
        try {
            PersonMorale personMorale = personMoraleService.findCompanybyid(id);
            return ResponseEntity.ok(personMorale);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/updatecompany/{id}")
    public ResponseEntity<PersonMorale> updateCompany(@PathVariable Integer id, @RequestBody PersonMorale personMorale) {
        try {
            PersonMorale updatedPersonMorale = personMoraleService.updatePersonMorale(id, personMorale);
            return ResponseEntity.ok(updatedPersonMorale);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/companies")
    public ResponseEntity<List<PersonMorale>> getAllCompanies() {
        List<PersonMorale> personMorales = personMoraleService.findAllCompanies();
        return new ResponseEntity<>(personMorales, HttpStatus.OK);
    }
    @DeleteMapping("/deletecompany/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {
        try {
            personMoraleService.deleteCompany(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    }


}
