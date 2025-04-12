package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.Operation.OperationRepository;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonMoraleService {

    private final PersonneMoraleRepository personneMoraleRepository;
private final OperationRepository operationRepository;
    public PersonMorale addCompany(PersonMorale personMorale) {
        PersonMorale existingCompany = personneMoraleRepository.findByCode(personMorale.getCode());
        if (existingCompany != null) {
            throw new IllegalArgumentException("A company with the given code already exists");
        }
        return personneMoraleRepository.save(personMorale);
    }

    public PersonMorale findCompanybyid(Integer id) {
        Optional<PersonMorale> personMorale = personneMoraleRepository.findById(id);
        if (personMorale.isPresent()) {
            return personMorale.get();
        } else {
            throw new RuntimeException("Bank not found with ID: " + id);
        }
    }
    public PersonMorale updatePersonMorale(Integer id, PersonMorale newPersonMorale) {
        PersonMorale personMorale = personneMoraleRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Person morale not found with ID: " + id));

        if (newPersonMorale.getName() != null) {
            personMorale.setName(newPersonMorale.getName());
        }
        if (newPersonMorale.getCode() != null && !newPersonMorale.getCode().equals(personMorale.getCode())) {
            PersonMorale personWithSameCode = personneMoraleRepository.findByCode(newPersonMorale.getCode());
            if (personWithSameCode != null && !personWithSameCode.getId().equals(id)) {
                throw new UserNotFoundException("Code already exists in other companies");
            }
            personMorale.setCode(newPersonMorale.getCode());
        }
        if (newPersonMorale.getRib() != null && !newPersonMorale.getRib().equals(personMorale.getRib())) {
            PersonMorale personWithSameRib = personneMoraleRepository.findByRib(newPersonMorale.getRib());
            if (personWithSameRib != null && !personWithSameRib.getId().equals(id)) {
                throw new UserNotFoundException("RIB already exists in other companies");
            }
            personMorale.setRib(newPersonMorale.getRib());
        }
        if (newPersonMorale.getContact() != null) {
            personMorale.setContact(newPersonMorale.getContact());
        }
        if (newPersonMorale.getEmail() != null) {
            personMorale.setEmail(newPersonMorale.getEmail());
        }

        personneMoraleRepository.save(personMorale);
        return personMorale;
    }

    public List<PersonMorale> findAllCompanies() {
        return personneMoraleRepository.findAll();
    }
    public void deleteCompany(Integer id) {
        PersonMorale personMorale = personneMoraleRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Person not found with ID: " + id));

        List<Operation> operations = operationRepository.findByPersonneMorale(personMorale);
        if (!operations.isEmpty()) {
            throw new RuntimeException("Cannot delete company with related operations.");
        }

        personneMoraleRepository.delete(personMorale);
    }

}
