package com.C_TechProject.Operation;

public class WaitingState implements OperationState {

    @Override
    public void valider(Operation operation) {
        operation.setState(new ValidState()); // Changement d’état
    }

    @Override
    public String getStatus() {
        return "WAITING";
    }
}
