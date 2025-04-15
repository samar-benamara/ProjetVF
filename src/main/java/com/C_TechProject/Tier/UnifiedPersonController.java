package com.C_TechProject.Tier;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UnifiedPersonController {

    private final PersonFactory personFactory;
    private final PersonMoraleService personMoraleService;
    private final PersonnePhysiqueService personnePhysiqueService;

    @PostMapping("/addperson")
    public ResponseEntity<?> addPerson(@RequestBody PersonRequest request) {
        try {
            Person person = personFactory.createPerson(request);

            if (person instanceof PersonMorale) {
                personMoraleService.save((PersonMorale) person);
                return new ResponseEntity<>(person, HttpStatus.CREATED);
            } else if (person instanceof PersonPhysique) {
                personnePhysiqueService.save((PersonPhysique) person);
                return new ResponseEntity<>(person, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Type inconnu", HttpStatus.BAD_REQUEST);
            }

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
