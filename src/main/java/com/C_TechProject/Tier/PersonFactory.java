package com.C_TechProject.Tier;

import org.springframework.stereotype.Component;

@Component
public class PersonFactory {

    public Person createPerson(PersonRequest request) {
        if ("morale".equalsIgnoreCase(request.getType())) {
            return new PersonMorale(
                    null,
                    request.getEmail(),
                    request.getContact(),
                    request.getRib(),
                    request.getName(),
                    request.getCode(),
                    null
            );
        } else if ("physique".equalsIgnoreCase(request.getType())) {
            return new PersonPhysique(
                    null,
                    request.getEmail(),
                    request.getContact(),
                    request.getRib(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getCin(),
                    request.getAdresse(),
                    null
            );
        } else {
            throw new IllegalArgumentException("Type de personne inconnu: " + request.getType());
        }
    }
}
