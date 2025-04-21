package com.C_TechProject.Bordereau;

import java.util.List;
import java.util.Optional;

public interface IBordereauRepository {
    Bordereau save(Bordereau bordereau);
    Optional<Bordereau> findById(Integer id);
    List<Bordereau> findAll();
    void delete(Bordereau bordereau);
    Bordereau getBordereauById(Integer id);
}
