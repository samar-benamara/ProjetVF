package com.C_TechProject.Operation;

import com.C_TechProject.BankAccountSerializer;
import com.C_TechProject.Tier.PersonMorale;
import com.C_TechProject.Tier.PersonPhysique;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bankAccount.BankAccount;
import com.C_TechProject.legalEntity.LegalEntity;
import com.C_TechProject.user.OperationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_Operation")

public class Operation {
    @Id
    @GeneratedValue
    private Integer id;

    private String type;
    private String etat;
    @Column(nullable=true)

    private String numcheque;

    private String reglement ;

    private String montant;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "personnePhysique_id")
    private PersonPhysique personnePhysique;

    @ManyToOne
    @JoinColumn(name = "personneMorale_id")
    private PersonMorale personneMorale;

    @Temporal(TemporalType.DATE)
    private Date creationDate;


    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }



}



