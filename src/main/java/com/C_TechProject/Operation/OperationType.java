package com.C_TechProject.Operation;

public interface OperationType {

        String getType(); // ex: "receipt", "disbursement"
        void validate(OperationRequest request); // ici tu mets ta logique de validation si nécessaire
    }

