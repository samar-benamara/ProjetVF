package com.C_TechProject.Operation;

import org.springframework.stereotype.Component;

@Component
public class DisbursementOperation implements OperationType {

    @Override
    public String getType() {
        return "disbursement";
    }

    @Override
    public void handle() {
        System.out.println("Handling disbursement operation");
    }
}
