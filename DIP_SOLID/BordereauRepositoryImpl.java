package com.C_TechProject.Bordereau;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class BordereauRepositoryImpl implements IBordereauRepository {

    private final BordereauRepository repo;

    @Override
    public Bordereau save(Bordereau bordereau) {
        return repo.save(bordereau);
    }

    @Override
    public Optional<Bordereau> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Bordereau> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Bordereau bordereau) {
        repo.delete(bordereau);
    }

    @Override
    public Bordereau getBordereauById(Integer id) {
        return repo.getBordereauById(id);
    }
}
