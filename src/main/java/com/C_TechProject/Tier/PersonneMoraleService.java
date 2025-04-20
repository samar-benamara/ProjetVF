package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.Operation.OperationRepository;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonneMoraleService implements TierService<PersonneMorale> {

    private final PersonneMoraleRepository personneMoraleRepository;
    private final OperationRepository operationRepository;

    public PersonneMorale ajouter(PersonneMorale personneMorale) {
        if (personneMoraleRepository.findByCode(personneMorale.getCode()) != null) {
            throw new IllegalArgumentException("Le code est déjà utilisé.");
        }

        if (personneMoraleRepository.findByRib(personneMorale.getRib()) != null) {
            throw new IllegalArgumentException("Le RIB est déjà utilisé.");
        }

        return personneMoraleRepository.save(personneMorale);
    }

    public PersonneMorale trouverParId(Integer id) {
        return personneMoraleRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Personne morale introuvable avec l'ID : " + id));
    }

    public PersonneMorale modifier(Integer id, PersonneMorale nouvelle) {
        PersonneMorale existante = personneMoraleRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Personne morale non trouvée avec l'ID : " + id));

        if (nouvelle.getNom() != null) existante.setNom(nouvelle.getNom());

        if (nouvelle.getCode() != null && !nouvelle.getCode().equals(existante.getCode())) {
            PersonneMorale autre = personneMoraleRepository.findByCode(nouvelle.getCode());
            if (autre != null && !autre.getId().equals(id)) {
                throw new IllegalArgumentException("Code déjà utilisé par une autre personne morale.");
            }
            existante.setCode(nouvelle.getCode());
        }

        if (nouvelle.getRib() != null && !nouvelle.getRib().equals(existante.getRib())) {
            PersonneMorale autre = personneMoraleRepository.findByRib(nouvelle.getRib());
            if (autre != null && !autre.getId().equals(id)) {
                throw new IllegalArgumentException("RIB déjà utilisé par une autre personne morale.");
            }
            existante.setRib(nouvelle.getRib());
        }

        if (nouvelle.getEmail() != null) existante.setEmail(nouvelle.getEmail());
        if (nouvelle.getContact() != null) existante.setContact(nouvelle.getContact());

        return personneMoraleRepository.save(existante);
    }

    public List<PersonneMorale> listerToutes() {
        return personneMoraleRepository.findAll();
    }

    public void supprimer(Integer id) {
        PersonneMorale personneMorale = personneMoraleRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Personne morale non trouvée avec l'ID : " + id));

        List<Operation> operations = operationRepository.findByPersonneMorale(personneMorale);
        if (!operations.isEmpty()) {
            throw new RuntimeException("Impossible de supprimer : des opérations sont liées à cette personne morale.");
        }

        personneMoraleRepository.delete(personneMorale);
    }
}
