package com.C_TechProject.Tier;

import com.C_TechProject.bank.Bank;
import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PersonPhysiqueController {
// comment
    public final PersonnePhysiqueService personnePhysiqueService;

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonPhysique> getPersonById(@PathVariable Integer id) {
        try {
            PersonPhysique personPhysique = personnePhysiqueService.findById(id);
            return ResponseEntity.ok(personPhysique);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/updateperson/{id}")
    public ResponseEntity<PersonPhysique> updatePerson(@PathVariable Integer id, @RequestBody PersonPhysique personPhysique) {
        try {
            PersonPhysique updatedPersonPhysique = personnePhysiqueService.update(id, personPhysique);
            return ResponseEntity.ok(updatedPersonPhysique);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/persons")
    public ResponseEntity<List<PersonPhysique>> getAllPersons() {
        List<PersonPhysique> personPhysiques = personnePhysiqueService.findAll();
        return new ResponseEntity<>(personPhysiques, HttpStatus.OK);
    }
    @DeleteMapping("/deleteperson/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        try {
            personnePhysiqueService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    }


}
