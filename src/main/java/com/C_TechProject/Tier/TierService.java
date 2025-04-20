package com.C_TechProject.Tier;

import java.util.List;

public interface TierService<T extends Tier> {
    T ajouter(T entity);
    T modifier(Integer id, T updatedEntity);
    T trouverParId(Integer id);
    List<T> listerToutes();
    void supprimer(Integer id);
}
