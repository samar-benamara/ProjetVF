package com.C_TechProject.Bordereau;


import com.C_TechProject.Operation.Operation;
import com.C_TechProject.Operation.OperationRepository;
import com.C_TechProject.Operation.OperationResponse;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.bank.BankRepository;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class Bordereauservice {

    private final BordereauRepository bordereauRepository;
    private final OperationRepository operationRepository;


    public Bordereau addBordereau(BordereauRequest bordereauRequest) {


        Bordereau bordereau1 = Bordereau.builder().number(bordereauRequest.getNumber())
                .type(bordereauRequest.getType()).reglement(bordereauRequest.getReglement())
                .date(bordereauRequest.getDate()).build();
        Bordereau savedbor = bordereauRepository.save(bordereau1);

        return savedbor;

    }

    public Bordereau addOperationsToBordereau(Integer idBordereau, List<Integer> idsOperations) {

        Bordereau bordereau = bordereauRepository.getBordereauById(idBordereau);

        List<Operation> operations = operationRepository.findAllById(idsOperations);

        operations.forEach(operation -> operation.setEtat("Valid"));

        bordereau.getOperations().addAll(operations);

        return bordereauRepository.save(bordereau);
    }

    public List<OperationResponse> getOperationsInBordereau(Integer idBordereau) {
        Bordereau bordereau = bordereauRepository.getBordereauById(idBordereau);
        return bordereau.getOperations().stream()
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


    public List<Bordereau> findAllBodereaux() {
        List<Bordereau> bordereaux = bordereauRepository.findAll();
        for (Bordereau bordereau : bordereaux) {
            int operationCount = bordereau.getOperations().size();
            bordereau.setOperationCount(operationCount);
            setTotalMontantInBordereau(bordereau.getId());
        }
        return bordereaux;
    }

    public Bordereau findBordereauByid(Integer id) {
        Bordereau bordereau = bordereauRepository.getBordereauById(id);
        if (bordereau != null) {
            setTotalMontantInBordereau(id);
            return bordereau;
        } else {
            throw new RuntimeException("BordereauVersement not found with ID: " + id);
        }
    }


    public void deleteBordereau(Integer id) {
        Bordereau bordereau = bordereauRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Bordereau not found with ID: " + id));
        if (!bordereau.getOperations().isEmpty()) {
            throw new RuntimeException("Cannot delete bordereau with ID " + id + " as it has associated operations.");
        }
        bordereauRepository.delete(bordereau);
    }





    public void setTotalMontantInBordereau(Integer idBordereau) {
        Bordereau bordereau = bordereauRepository.getBordereauById(idBordereau);
        double totalMontant = bordereau.getOperations().stream()
                .mapToDouble(operation -> Double.parseDouble(operation.getMontant()))
                .sum();
        bordereau.setTotalAmount(String.valueOf(totalMontant));
        bordereauRepository.save(bordereau);
    }


}

