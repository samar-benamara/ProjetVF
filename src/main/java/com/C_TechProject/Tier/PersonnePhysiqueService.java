package com.C_TechProject.Tier;

import com.C_TechProject.Operation.OperationRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonnePhysiqueService extends BasePersonService<PersonPhysique, PersonnePhysiqueRepository> {

    public PersonnePhysiqueService(PersonnePhysiqueRepository repository, OperationRepository operationRepository) {
        super(repository, operationRepository);
    }

    @Override
    protected void validateUniqueFields(PersonPhysique entity) {
        repository.findByCin(entity.getCin()).ifPresent(person -> {
            throw new IllegalArgumentException("CIN déjà utilisé");
        });
    }

    @Override
    protected void updateSpecificFields(PersonPhysique existing, PersonPhysique newEntity) {
        if (newEntity.getFirstName() != null) existing.setFirstName(newEntity.getFirstName());
        if (newEntity.getLastName() != null) existing.setLastName(newEntity.getLastName());
        if (newEntity.getCin() != null) existing.setCin(newEntity.getCin());
        if (newEntity.getAdresse() != null) existing.setAdresse(newEntity.getAdresse());
    }

    @Override
    protected void checkAssociatedOperations(PersonPhysique entity) {
        if (!operationRepository.findByPersonnePhysique(entity).isEmpty()) {
            throw new RuntimeException("Opérations associées existantes");
        }
    }
}

