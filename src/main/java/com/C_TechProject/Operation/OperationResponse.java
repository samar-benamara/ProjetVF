package com.C_TechProject.Operation;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationResponse {
    private Integer id;
    private String type;
    private String etat;
    private String reglement;
    private String montant;

    private String numcheque;
    private String bank;
    private String legalEntity;
    private String bankAccount;
    private String personnePhysique;
    private String personneMorale;
    private String creationDate;



}
