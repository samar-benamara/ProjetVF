package com.C_TechProject.Operation;

import com.C_TechProject.Tier.PersonneMorale;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OperationRequest {


    private String type;
    private String etat;

    private String reglement ;

    private String montant;


    @Column(nullable=true)

    private String numcheque;

    private String bank;

    private String legalEntity;

    private String bankAccount;

    private String personnePhysique;

    private String personneMorale;

}
