package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.Operation.OperationRepository;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bankAccount.BankAccount;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonnePhysiqueService {

    private final PersonnePhysiqueRepository personnePhysiqueRepository;
    private final OperationRepository operationRepository;


    public PersonPhysique addPerson(PersonPhysique personPhysique) {
        PersonPhysique existingPerson = personnePhysiqueRepository.findByCin(personPhysique.getCin());

        if (existingPerson != null) {
            throw new IllegalArgumentException("A person with the given CIN already exists");
        }

        return personnePhysiqueRepository.save(personPhysique);
    }
    public PersonPhysique findPersonbyid(Integer id) {
        Optional<PersonPhysique> personPhysique = personnePhysiqueRepository.findById(id);
        if (personPhysique.isPresent()) {
            return personPhysique.get();
        } else {
            throw new RuntimeException("Bank not found with ID: " + id);
        }
    }
    public PersonPhysique updatePerson(Integer id, PersonPhysique newPerson) {
        PersonPhysique person = personnePhysiqueRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Person not found with ID: " + id));

        if (newPerson.getFirstName() != null) {
            person.setFirstName(newPerson.getFirstName());
        }
        if (newPerson.getLastName() != null) {
            person.setLastName(newPerson.getLastName());
        }
        if (newPerson.getCin() != null && !newPerson.getCin().equals(person.getCin())) {
            PersonPhysique personWithSameCin = personnePhysiqueRepository.findByCin(newPerson.getCin());
            if (personWithSameCin != null && !personWithSameCin.getId().equals(id)) {
                throw new UserNotFoundException("CIN already exists in other persons");
            }
            person.setCin(newPerson.getCin());
        }
        if (newPerson.getRib() != null && !newPerson.getRib().equals(person.getRib())) {
            PersonPhysique personWithSameRib = personnePhysiqueRepository.findByRib(newPerson.getRib());
            if (personWithSameRib != null && !personWithSameRib.getId().equals(id)) {
                throw new UserNotFoundException("RIB already exists in other persons");
            }
            person.setRib(newPerson.getRib());
        }
        if (newPerson.getAdressemail() != null) {
            person.setAdressemail(newPerson.getAdressemail());
        }
        if (newPerson.getContact() != null) {
            person.setContact(newPerson.getContact());
        }
        if (newPerson.getAdresse() != null) {
            person.setAdresse(newPerson.getAdresse());
        }

        personnePhysiqueRepository.save(person);
        return person;
    }

    public List<PersonPhysique> findAllPersons() {
        return personnePhysiqueRepository.findAll();
    }

    public void deletePerson(Integer id) {
        PersonPhysique personPhysique = personnePhysiqueRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Person not found with ID: " + id));

        List<Operation> operations = operationRepository.findByPersonnePhysique(personPhysique);
        if (!operations.isEmpty()) {
            throw new RuntimeException("Cannot delete person with related operations.");
        }

        personnePhysiqueRepository.delete(personPhysique);
    }

}

