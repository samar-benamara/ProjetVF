ackage com.C_TechProject.Operation;

import com.C_TechProject.Tier.PersonMorale;
import com.C_TechProject.Tier.PersonPhysique;
import com.C_TechProject.Tier.PersonneMoraleRepository;
import com.C_TechProject.Tier.PersonnePhysiqueRepository;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bank.BankRepository;
import com.C_TechProject.bankAccount.BankAccount;
import com.C_TechProject.bankAccount.BankAccountRepository;
import com.C_TechProject.legalEntity.LegalEntity;
import com.C_TechProject.legalEntity.LegalEntityRepository;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor

public class OperationService {
    private final OperationRepository operationRepository;
    private final BankRepository bankRepository;
    private final BankAccountRepository bankAccountRepository;

    private final LegalEntityRepository legalEntityRepository;
    private final PersonnePhysiqueRepository personnePhysiqueRepository;
    private final PersonneMoraleRepository personneMoraleRepository;



    public Operation addOperation(OperationRequest operation) throws Exception {

        if (operation.getType() == null ||operation.getEtat()==null || operation.getReglement()==null || operation.getBank() == null || operation.getLegalEntity() == null ||
                operation.getBankAccount() == null) {
            throw new IllegalArgumentException("Missing required fields in the Operation entity");
        }

        Bank bank=bankRepository.findByNameBanque(operation.getBank());
        LegalEntity legalEntity=legalEntityRepository.findByNameEntity(operation.getLegalEntity());
        BankAccount bankAccount=bankAccountRepository.findBankAccountByRib(operation.getBankAccount());
        PersonPhysique personPhysique=personnePhysiqueRepository.findByCin((operation.getPersonnePhysique()));
        PersonMorale personMorale=personneMoraleRepository.findByCode(operation.getPersonneMorale());

        Operation operation1=Operation.builder()
                .type(operation.getType()).etat(operation.getEtat()).montant(operation.getMontant()).reglement(operation.getReglement()).numcheque(operation.getNumcheque())
                        .bank(bank).legalEntity(legalEntity).bankAccount(bankAccount).personnePhysique(personPhysique)
                        .personneMorale(personMorale).creationDate(new Date())
                                .build();

        Operation savedoperation=operationRepository.save(operation1);

        return savedoperation;
    }

    public OperationResponse findOperationById(Integer id) {
        Optional<Operation> operation = operationRepository.findById(id);
        if (operation.isPresent()) {
            Operation op = operation.get();
            OperationResponse response = new OperationResponse();
            response.setId(op.getId());
            response.setType(op.getType());
            response.setEtat(op.getEtat());
            response.setMontant(op.getMontant());

            response.setReglement(op.getReglement());
            response.setNumcheque(op.getNumcheque()!=null ? op.getNumcheque():null);
            response.setBank(op.getBank().getNameBanque());
            response.setLegalEntity(op.getLegalEntity().getNameEntity());
            response.setBankAccount(op.getBankAccount().getRib());
            response.setPersonnePhysique(op.getPersonnePhysique() != null ? op.getPersonnePhysique().getCin() : null);
            response.setPersonneMorale(op.getPersonneMorale() != null ? op.getPersonneMorale().getCode() : null);
            response.setCreationDate(String.valueOf(op.getCreationDate()));

            return response;
        } else {
            throw new RuntimeException("Operation not found with ID: " + id);
        }
    }


    public List<OperationResponse> findAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        return operations.stream()
                .map(operation -> {
                    OperationResponse response = new OperationResponse();
                    response.setId(operation.getId());
                    response.setType(operation.getType());
                    response.setEtat(operation.getEtat());
                    response.setMontant(operation.getMontant());

                    response.setReglement(operation.getReglement());
                    if (operation.getNumcheque() != null) {
                        response.setNumcheque(operation.getNumcheque());
                    }
                    response.setBank(operation.getBank().getNameBanque());
                    response.setLegalEntity(operation.getLegalEntity().getNameEntity());
                    response.setBankAccount(operation.getBankAccount().getRib());
                    if (operation.getPersonnePhysique() != null) {
                        response.setPersonnePhysique(operation.getPersonnePhysique().getCin());
                    }
                    if (operation.getPersonneMorale() != null) {
                        response.setPersonneMorale(operation.getPersonneMorale().getCode());
                    }
                    response.setCreationDate(String.valueOf(operation.getCreationDate()));

                    return response;
                })
                .collect(Collectors.toList());
    }


    public void deleteoperation (Integer id) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("operation not found with ID: " + id));

        operationRepository.delete(operation);
    }
public Operation updateOperation(Integer id, OperationRequest newOperation) throws Exception {
    Operation operation = operationRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Operation not found with ID: " + id));

    Bank bank = bankRepository.findByNameBanque(newOperation.getBank());
    LegalEntity legalEntity = legalEntityRepository.findByNameEntity(newOperation.getLegalEntity());
    BankAccount bankAccount = bankAccountRepository.findBankAccountByRib(newOperation.getBankAccount());
    PersonPhysique personnePhysique = personnePhysiqueRepository.findByCin(newOperation.getPersonnePhysique());
    PersonMorale personneMorale = personneMoraleRepository.findByCode(newOperation.getPersonneMorale());

    // Appliquer la contrainte OCL selon le type
    String type = newOperation.getType();
    boolean hasPhysique = newOperation.getPersonnePhysique() != null;
    boolean hasMorale = newOperation.getPersonneMorale() != null;
    boolean hasEntity = newOperation.getLegalEntity() != null;

    int count = 0;
    if (hasPhysique) count++;
    if (hasMorale) count++;
    if (hasEntity) count++;

    if (count > 1) {
        throw new RuntimeException("Une seule entité doit être associée à l'opération.");
    }

    if ("Disbursement".equalsIgnoreCase(type)) {
        if (hasEntity) {
            throw new RuntimeException("Une opération de type 'Disbursement' ne peut pas être liée à une LegalEntity.");
        }
        if (!hasPhysique && !hasMorale) {
            throw new RuntimeException("Une opération de type 'Disbursement' doit être liée à une PersonnePhysique ou PersonneMorale.");
        }
    } else if ("Receipt".equalsIgnoreCase(type)) {
        if (!hasEntity) {
            throw new RuntimeException("Une opération de type 'Receipt' doit être liée à une LegalEntity.");
        }
        if (hasPhysique || hasMorale) {
            throw new RuntimeException("Une opération de type 'Receipt' ne peut pas être liée à une PersonnePhysique ni PersonneMorale.");
        }
    }

    // Mise à jour des champs si modifiés
    if (type != null && !type.equals(operation.getType())) {
        operation.setType(type);
    }
    if (newOperation.getReglement() != null && !newOperation.getReglement().equals(operation.getReglement())) {
        operation.setReglement(newOperation.getReglement());
    }
    if (newOperation.getEtat() != null && !newOperation.getEtat().equals(operation.getEtat())) {
        operation.setEtat(newOperation.getEtat());
    }
    if (newOperation.getNumcheque() != null && !newOperation.getNumcheque().equals(operation.getNumcheque())) {
        operation.setNumcheque(newOperation.getNumcheque());
    }

    if (newOperation.getBank() != null && !newOperation.getBank().equals(operation.getBank())) {
        operation.setBank(bank);
    }
    
    if (newOperation.getBankAccount() != null && !newOperation.getBankAccount().equals(operation.getBankAccount())) {
        operation.setBankAccount(bankAccount);
    }

    // Nettoyer les anciennes entités
    operation.setPersonnePhysique(null);
    operation.setPersonneMorale(null);
    operation.setLegalEntity(null);

    // Réaffecter la bonne entité
    if (hasPhysique) {
        operation.setPersonnePhysique(personnePhysique);
    } else if (hasMorale) {
        operation.setPersonneMorale(personneMorale);
    } else if (hasEntity) {
        operation.setLegalEntity(legalEntity);
    }

    return operationRepository.save(operation);
}

}

