package com.C_TechProject.Bordereau;

import com.C_TechProject.Operation.OperationResponse;
import java.util.List;

public interface BordereauOperationService {
    Bordereau addOperations(Integer bordereauId, List<Integer> operationIds);
    List<OperationResponse> getBordereauOperations(Integer bordereauId);
}