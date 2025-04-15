// Bordereau.java
package com.C_TechProject.Bordereau;

import com.C_TechProject.Operation.Operation;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;
    private String type;
    private String reglement;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total_amount")
    private String totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bordereau")
    private List<Operation> operations = new ArrayList<>();

    // Méthode de validation générique (LSP-friendly)
    public void validate() {
        if (this.number == null || this.number.isEmpty()) {
            throw new IllegalArgumentException("Bordereau number cannot be empty");
        }
    }
}