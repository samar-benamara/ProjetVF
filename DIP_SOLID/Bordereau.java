package com.C_TechProject.Bordereau;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.bank.Bank;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Bordereau")
public class Bordereau {
    @Id
    @GeneratedValue
    private Integer id;

    private String number;
    private String type ;

    private String reglement;
    private Number operationCount;
    @Column(name = "date")
    private LocalDate date;

    @Column(nullable=true)
    private String totalAmount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Operation> operations = new ArrayList<>();




}
