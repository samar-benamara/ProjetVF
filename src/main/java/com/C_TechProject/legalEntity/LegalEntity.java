package com.C_TechProject.legalEntity;

import com.C_TechProject.bankAccount.BankAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_LegalEntity")
public class LegalEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String code;

    private String nameEntity;


    @OneToMany(mappedBy = "legalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccount> bankAccounts;

}
