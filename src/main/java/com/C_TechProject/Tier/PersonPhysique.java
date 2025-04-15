package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Entity
@Table(name = "_PersonPhysique")
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonPhysique extends Person {
    private String firstName;
    private String lastName;
    private String cin;
    private String adresse;

    @OneToMany(mappedBy = "personnePhysique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operations;

    public PersonPhysique(String email, String contact, String rib, String firstName, String lastName, String cin, String adresse, String requestAdresse, List<Operation> operations) {
        setEmail(email);
        setContact(contact);
        setRib(rib);
        this.firstName = firstName;
        this.lastName = lastName;
        this.cin = cin;
        this.adresse = adresse;
        this.operations = operations;
    }
}
