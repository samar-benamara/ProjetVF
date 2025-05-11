package com.C_TechProject.Operation;


@Component
public class ReceiptOperation implements OperationType {

    @Override
    public String getType() {
        return "receipt";
    }

    @Override
    public void handle() {
        System.out.println("Handling receipt operation");
    }
}



