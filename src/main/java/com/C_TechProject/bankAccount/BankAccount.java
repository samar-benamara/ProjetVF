package com.C_TechProject.bankAccount;

import com.C_TechProject.BankAccountSerializer;
import com.C_TechProject.Operation.Operation;
import com.C_TechProject.legalEntity.LegalEntity;
import com.C_TechProject.bank.Bank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_Bankaccount")
@JsonSerialize(using = BankAccountSerializer.class)

public class BankAccount {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "rib", columnDefinition = "BIGINT")

    private String rib;
    @OneToMany(mappedBy = "bankAccount")
    private List<Operation> operations;

    @ManyToOne

    @JoinColumn(name = "bank_id")
    private Bank bank;


    @ManyToOne
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;

    public BankAccount(String rib, Bank bank, LegalEntity legalEntity) {
        this.rib = rib;
        this.bank = bank;
        this.legalEntity = legalEntity;
    }

    public Integer getBankId() {
        return this.bank.getId();
    }

    public Integer getLegalEntityId() {
        return this.legalEntity.getId();
    }
}

