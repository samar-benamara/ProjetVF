package com.C_TechProject.Tier;

import com.C_TechProject.Operation.OperationRepository;
import com.C_TechProject.user.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// ===== GRASP Low Coupling : Ne dépend que des interfaces abstraites =====

@Transactional
public abstract class BasePersonService<T extends Person, R extends PersonRepository<T>> {
    // ===== GRASP Low Coupling : Usage de repository abstrait via injection =====

    protected final R repository;
    protected final OperationRepository operationRepository;

    // ===== GRASP Low Coupling : Dépendances injectées, pas créées à l'intérieur =====

    public BasePersonService(R repository, OperationRepository operationRepository) {
        this.repository = repository;
        this.operationRepository = operationRepository;
    }

    public T save(T entity) {
        validateUniqueFields(entity); // méthode métier
        return repository.save(entity); // ===== GRASP Low Coupling : utilisation du repository abstrait =====
    }

    public T findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Entity not found"));  //===== GRASP Low Coupling =====
    }

    public List<T> findAll() {
        return repository.findAll(); // ===== GRASP Low Coupling =====
    }

    public T update(Integer id, T newEntity) {
        T existing = findById(id);
        updateCommonFields(existing, newEntity);
        updateSpecificFields(existing, newEntity);
        return repository.save(existing);
    }

    public void delete(Integer id) {
        T entity = findById(id);
        checkAssociatedOperations(entity);
        repository.delete(entity);
    }

    private void updateCommonFields(T existing, T newEntity) {
        if (newEntity.getEmail() != null) existing.setEmail(newEntity.getEmail());
        if (newEntity.getContact() != null) existing.setContact(newEntity.getContact());
        if (newEntity.getRib() != null) existing.setRib(newEntity.getRib());
    }

    protected abstract void validateUniqueFields(T entity);
    protected abstract void updateSpecificFields(T existing, T newEntity);
    protected abstract void checkAssociatedOperations(T entity);
}
