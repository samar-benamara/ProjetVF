package com.C_TechProject.Tier;

import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tier/personne-morale")
@RequiredArgsConstructor
public class PersonneMoraleController {

    private final PersonneMoraleService personneMoraleService;

    @PostMapping("/ajouter")
    public ResponseEntity<PersonneMorale> ajouterPersonneMorale(@RequestBody PersonneMorale personneMorale) {
        try {
            // Toute logique d'ajout ici ou dans le service
            PersonneMorale saved = personneMoraleService.ajouter(personneMorale);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneMorale> consulterPersonneMorale(@PathVariable Integer id) {
        try {
            PersonneMorale personneMorale = personneMoraleService.trouverParId(id);
            return ResponseEntity.ok(personneMorale);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<PersonneMorale> modifierPersonneMorale(@PathVariable Integer id, @RequestBody PersonneMorale personneMorale) {
        try {
            // Toute logique de modification ici ou dans le service
            PersonneMorale updated = personneMoraleService.modifier(id, personneMorale);
            return ResponseEntity.ok(updated);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/liste")
    public ResponseEntity<List<PersonneMorale>> listerPersonnesMorales() {
        List<PersonneMorale> liste = personneMoraleService.listerToutes();
        return new ResponseEntity<>(liste, HttpStatus.OK);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerPersonneMorale(@PathVariable Integer id) {
        try {
            personneMoraleService.supprimer(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
