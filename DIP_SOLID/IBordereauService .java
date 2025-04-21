package com.C_TechProject.Bordereau;

import com.C_TechProject.Operation.OperationResponse;
import java.util.List;

public interface IBordereauService {

    Bordereau addBordereau(BordereauRequest bordereauRequest);
    Bordereau addOperationsToBordereau(Integer idBordereau, List<Integer> idsOperations);
    List<Bordereau> findAllBodereaux();
    Bordereau findBordereauByid(Integer id);
    void deleteBordereau(Integer id);
    List<OperationResponse> getOperationsInBordereau(Integer idBordereau);
}
