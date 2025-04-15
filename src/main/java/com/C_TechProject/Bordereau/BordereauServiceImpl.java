package com.C_TechProject.Bordereau;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.Operation.OperationRepository;
import com.C_TechProject.Operation.OperationResponse;
import com.C_TechProject.bank.Bank;
import com.C_TechProject.legalEntity.LegalEntity;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BordereauServiceImpl implements BordereauCrudService, BordereauOperationService, BordereauCalculationService {

    private final BordereauRepository bordereauRepository;
    private final OperationRepository operationRepository;

    @Override
    public Bordereau createBordereau(BordereauRequest request) {
        Bordereau bordereau = Bordereau.builder()
                .number(request.getNumber())
                .type(request.getType())
                .reglement(request.getReglement())
                .date(request.getDate())
                .build();
        bordereau.validate();
        return bordereauRepository.save(bordereau);
    }

    @Override
    public List<Bordereau> getAllBordereaux() {
        List<Bordereau> bordereaux = bordereauRepository.findAll();
        bordereaux.forEach(b -> {
            b.setOperationCount(b.getOperations().size());
            calculateTotalAmount(b.getId());
        });
        return bordereaux;
    }

    @Override
    public Bordereau getBordereauById(Integer id) {
        Bordereau bordereau = bordereauRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Bordereau not found"));
        bordereau.setOperationCount(bordereau.getOperations().size());
        calculateTotalAmount(id);
        return bordereau;
    }

    @Override
    public void deleteBordereau(Integer id) {
        Bordereau bordereau = getBordereauById(id);
        if (!bordereau.getOperations().isEmpty()) {
            throw new IllegalStateException("Cannot delete bordereau with operations");
        }
        bordereauRepository.delete(bordereau);
    }

    @Override
    public Bordereau addOperations(Integer bordereauId, List<Integer> operationIds) {
        Bordereau bordereau = getBordereauById(bordereauId);
        List<Operation> operations = operationRepository.findAllById(operationIds);

        operations.forEach(op -> {
            op.setEtat("Valid");
            op.setBordereau(bordereau); // Set bidirectional relationship
        });

        bordereau.getOperations().addAll(operations);
        calculateTotalAmount(bordereauId);
        return bordereauRepository.save(bordereau);
    }

    @Override
    public List<OperationResponse> getBordereauOperations(Integer bordereauId) {
        return getBordereauById(bordereauId).getOperations().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void calculateTotalAmount(Integer bordereauId) {
        Bordereau bordereau = getBordereauById(bordereauId);
        double total = bordereau.getOperations().stream()
                .mapToDouble(op -> Double.parseDouble(op.getMontant()))
                .sum();
        bordereau.setTotalAmount(String.format("%.2f", total));
        bordereauRepository.save(bordereau);
    }

    private OperationResponse convertToResponse(Operation operation) {
        OperationResponse response = new OperationResponse();
        response.setId(operation.getId());
        response.setType(operation.getType());
        response.setEtat(operation.getEtat());
        response.setMontant(operation.getMontant());
        response.setReglement(operation.getReglement());

        if (operation.getNumcheque() != null) {
            response.setNumcheque(operation.getNumcheque());
        }

        Bank bank = operation.getBank();
        if (bank != null) {
            response.setBank(bank.getNameBanque());
        }

        LegalEntity legalEntity = operation.getLegalEntity();
        if (legalEntity != null) {
            response.setLegalEntity(legalEntity.getNameEntity());
        }

        if (operation.getBankAccount() != null) {
            response.setBankAccount(operation.getBankAccount().getRib());
        }

        if (operation.getPersonnePhysique() != null) {
            response.setPersonnePhysique(operation.getPersonnePhysique().getCin());
        }

        if (operation.getPersonneMorale() != null) {
            response.setPersonneMorale(operation.getPersonneMorale().getCode());
        }

        response.setCreationDate(operation.getCreationDate().toString());
        return response;
    }
}