package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personne_physique")
public class PersonnePhysique extends Tier {

    private String nom;
    private String prenom;
    private String cin;

   @OneToMany(mappedBy = "personnePhysique", cascade = CascadeType.ALL)
    private List<Operation> operations;
}
