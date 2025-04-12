package com.C_TechProject.legalEntity;


import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class LegalEntityController {
    public final LegalEntityService legalEntityService;

    @PostMapping("/addentity")
    public ResponseEntity<LegalEntity> createEntity(@RequestBody LegalEntity legalEntity) {
        LegalEntity newEntity=legalEntityService.addEntity(legalEntity);
        return new ResponseEntity<>(legalEntity, HttpStatus.CREATED);
    }
    @GetMapping("/entities")
    public ResponseEntity<List<LegalEntity>> getAllEntities() {
        List<LegalEntity> entities = legalEntityService.findAllEntities();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }
    @GetMapping("/entity/{id}")
    public ResponseEntity<LegalEntity> getLegalEntityById(@PathVariable Integer id) {
        try {
            LegalEntity legalEntity = legalEntityService.findById(id);
            return new ResponseEntity<>(legalEntity, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/updateentity/{id}")
    public ResponseEntity<LegalEntity> updateEntity(@PathVariable Integer id, @RequestBody LegalEntity entity) {
        try {
            LegalEntity updatedEntity =legalEntityService.updateLegalEntity(id, entity);
            return ResponseEntity.ok(updatedEntity);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/deletelegalentity/{id}")
    public ResponseEntity<Void> deleteLegalEntity(@PathVariable Integer id) {
        try {
            legalEntityService.deleteLegalEntity(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }



}
