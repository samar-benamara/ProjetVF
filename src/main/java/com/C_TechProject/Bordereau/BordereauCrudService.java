package com.C_TechProject.Bordereau;

import java.util.List;

public interface BordereauCrudService {
    Bordereau createBordereau(BordereauRequest request);
    List<Bordereau> getAllBordereaux();
    Bordereau getBordereauById(Integer id);
    void deleteBordereau(Integer id);
}