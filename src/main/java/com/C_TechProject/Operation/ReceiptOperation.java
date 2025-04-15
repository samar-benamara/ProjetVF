package com.C_TechProject.Operation;


public class ReceiptOperation implements OperationType {
    @Override
    public String getType() {
        return "receipt";
    }

    @Override
    public void validate(OperationRequest request) {
        // Ajoute des validations spécifiques à "receipt" si besoin
    }
}


