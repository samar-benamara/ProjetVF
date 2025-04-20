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
    public class PersonnePhysiqueService implements TierService<PersonnePhysique> {

    private final PersonnePhysiqueRepository personnePhysiqueRepository;
    private final OperationRepository operationRepository;

    public PersonnePhysique ajouter(PersonnePhysique personnePhysique) {
        if (personnePhysiqueRepository.findByCin(personnePhysique.getCin()) != null) {
            throw new IllegalArgumentException("Le CIN est déjà utilisé.");
        }
        if (personnePhysiqueRepository.findByRib(personnePhysique.getRib()) != null) {
            throw new IllegalArgumentException("Le RIB est déjà utilisé.");
        }
        return personnePhysiqueRepository.save(personnePhysique);
    }

    public PersonnePhysique trouverParId(Integer id) {
        return personnePhysiqueRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Personne physique introuvable avec l'ID : " + id));
    }

    public PersonnePhysique modifier(Integer id, PersonnePhysique nouvelle) {
        PersonnePhysique existante = personnePhysiqueRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Personne physique non trouvée avec l'ID : " + id));

        if (nouvelle.getNom() != null) existante.setNom(nouvelle.getNom());
        if (nouvelle.getPrenom() != null) existante.setPrenom(nouvelle.getPrenom());

        if (nouvelle.getCin() != null && !nouvelle.getCin().equals(existante.getCin())) {
            PersonnePhysique autre = personnePhysiqueRepository.findByCin(nouvelle.getCin());
            if (autre != null && !autre.getId().equals(id)) {
                throw new IllegalArgumentException("CIN déjà utilisé.");
            }
            existante.setCin(nouvelle.getCin());
        }

        if (nouvelle.getRib() != null && !nouvelle.getRib().equals(existante.getRib())) {
            PersonnePhysique autre = personnePhysiqueRepository.findByRib(nouvelle.getRib());
            if (autre != null && !autre.getId().equals(id)) {
                throw new IllegalArgumentException("RIB déjà utilisé.");
            }
            existante.setRib(nouvelle.getRib());
        }

        if (nouvelle.getEmail() != null) existante.setEmail(nouvelle.getEmail());
        if (nouvelle.getContact() != null) existante.setContact(nouvelle.getContact());

        return personnePhysiqueRepository.save(existante);
    }

    public List<PersonnePhysique> listerToutes() {
        return personnePhysiqueRepository.findAll();
    }

    public void supprimer(Integer id) {
        PersonnePhysique personnePhysique = personnePhysiqueRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Personne physique non trouvée avec l'ID : " + id));

        List<Operation> operations = operationRepository.findByPersonnePhysique(personnePhysique);
        if (!operations.isEmpty()) {
            throw new RuntimeException("Impossible de supprimer : des opérations sont liées à cette personne physique.");
        }

        personnePhysiqueRepository.delete(personnePhysique);
    }
}
