package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.Operation.OperationRepository;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonMoraleService extends BasePersonService<PersonMorale, PersonneMoraleRepository> {

    public PersonMoraleService(PersonneMoraleRepository repository, OperationRepository operationRepository) {
        super(repository, operationRepository);
    }

    @Override
    protected void validateUniqueFields(PersonMorale entity) {
        repository.findByCode(entity.getCode()).ifPresent(person -> {
            throw new IllegalArgumentException("Code déjà utilisé");
        });
    }

    @Override
    protected void updateSpecificFields(PersonMorale existing, PersonMorale newEntity) {
        if (newEntity.getName() != null) existing.setName(newEntity.getName());
        if (newEntity.getCode() != null) existing.setCode(newEntity.getCode());
    }

    @Override
    protected void checkAssociatedOperations(PersonMorale entity) {
        if (!operationRepository.findByPersonneMorale(entity).isEmpty()) {
            throw new RuntimeException("Opérations associées existantes");
        }
    }
}
