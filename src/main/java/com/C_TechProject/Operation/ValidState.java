package com.C_TechProject.Operation;

public class ValidState implements OperationState {
    @Override
    public void valider(Operation operation) {
        throw new InvalidStateTransitionException("L'opération est déjà validée.");
    }

    @Override
    public String getStatus() {
        return "VALID";
    }
}


