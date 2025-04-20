package com.C_TechProject.Tier;

import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tier/personne-physique")
@RequiredArgsConstructor
public class PersonnePhysiqueController {

    private final PersonnePhysiqueService personnePhysiqueService;

    @PostMapping("/ajouter")
    public ResponseEntity<PersonnePhysique> ajouter(@RequestBody PersonnePhysique personnePhysique) {
        try {
            PersonnePhysique saved = personnePhysiqueService.ajouter(personnePhysique);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<PersonnePhysique> modifier(@PathVariable Integer id, @RequestBody PersonnePhysique personnePhysique) {
        try {
            PersonnePhysique updated = personnePhysiqueService.modifier(id, personnePhysique);
            return ResponseEntity.ok(updated);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonnePhysique> consulter(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(personnePhysiqueService.trouverParId(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/liste")
    public ResponseEntity<List<PersonnePhysique>> lister() {
        return ResponseEntity.ok(personnePhysiqueService.listerToutes());
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Integer id) {
        try {
            personnePhysiqueService.supprimer(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
