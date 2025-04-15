package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "_PersonMorale")
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonMorale extends Person {
    private String name;
    private String code;

    @OneToMany(mappedBy = "personneMorale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operations;

    public PersonMorale(String email, String contact, String rib, String name, String code, String requestCode, List<Operation> operations) {
        setEmail(email);
        setContact(contact);
        setRib(rib);
        this.name = name;
        this.code = code;
        this.operations = operations;
    }
}


