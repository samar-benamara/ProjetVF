package com.C_TechProject.Operation;

public class DisbursementOperation implements OperationType {
    @Override
    public String getType() {
        return "disbursement";
    }

    @Override
    public void validate(OperationRequest request) {
        // Ajoute des validations spécifiques à "disbursement" si besoin
    }
}