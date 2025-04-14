package com.C_TechProject.Operation;

public class ValidState implements OperationState {

    @Override
    public void valider(Operation operation) {
        // Déjà validée, donc pas de changement d’état
    }

    @Override
    public String getStatus() {
        return "VALID";
    }
}
