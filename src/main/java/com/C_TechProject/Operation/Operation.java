package com.C_TechProject.Operation;

import com.C_TechProject.Tier.PersonMorale;
import com.C_TechProject.Tier.PersonPhysique;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bankAccount.BankAccount;
import com.C_TechProject.legalEntity.LegalEntity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "_Operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;
    private String etat;

    @Column(nullable = true)
    private String numcheque;

    private String reglement;
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

    // Constructeur simple
    public Operation() {
        this.creationDate = new Date();
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNumcheque() {
        return numcheque;
    }

    public void setNumcheque(String numcheque) {
        this.numcheque = numcheque;
    }

    public String getReglement() {
        return reglement;
    }

    public void setReglement(String reglement) {
        this.reglement = reglement;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public PersonPhysique getPersonnePhysique() {
        return personnePhysique;
    }

    public void setPersonnePhysique(PersonPhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

    public PersonMorale getPersonneMorale() {
        return personneMorale;
    }

    public void setPersonneMorale(PersonMorale personneMorale) {
        this.personneMorale = personneMorale;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @PrePersist
    protected void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = new Date();
        }
    }
}