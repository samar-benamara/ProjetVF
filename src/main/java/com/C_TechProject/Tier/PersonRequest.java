package com.C_TechProject.Tier;


import lombok.Data;

@Data
public class PersonRequest {
    private String type; // "morale" ou "physique"

    // Champs communs
    private String email;
    private String contact;
    private String rib;

    // Champs pour PersonMorale
    private String name;
    private String code;

    // Champs pour PersonPhysique
    private String firstName;
    private String lastName;
    private String cin;
    private String adresse;
}
