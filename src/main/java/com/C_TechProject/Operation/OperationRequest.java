package com.C_TechProject.Operation;

import com.C_TechProject.Tier.PersonMorale;
import com.C_TechProject.Tier.PersonPhysique;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bankAccount.BankAccount;
import com.C_TechProject.legalEntity.LegalEntity;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
