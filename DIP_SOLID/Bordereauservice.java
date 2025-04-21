package com.C_TechProject.Bordereau;

import com.C_TechProject.Operation.*;
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

    private final IBordereauRepository bordereauRepository;
    private final IOperationRepository operationRepository;

     public Bordereauservice IBordereauRepository bordereauRepository,IOperationRepository  operationRepository) {
        this. bordereauRepository =  bordereauRepository;
	this.operationRepositoroperationRepository;

    }
     public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Bordereau addBordereau(BordereauRequest bordereauRequest) {
        Bordereau bordereau = Bordereau.builder()
                .number(bordereauRequest.getNumber())
                .type(bordereauRequest.getType())
                .reglement(bordereauRequest.getReglement())
                .date(bordereauRequest.getDate())
                .build();
        return bordereauRepository.save(bordereau);
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
                .map(OperationResponseMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<Bordereau> findAllBodereaux() {
        List<Bordereau> bordereaux = bordereauRepository.findAll();
        for (Bordereau bordereau : bordereaux) {
            bordereau.setOperationCount(bordereau.getOperations().size());
            setTotalMontantInBordereau(bordereau.getId());
        }
        return bordereaux;
    }

    public Bordereau findBordereauByid(Integer id) {
        return bordereauRepository.findById(id)
                .map(b -> {
                    setTotalMontantInBordereau(id);
                    return b;
                })
                .orElseThrow(() -> new RuntimeException("Bordereau non trouvé avec ID: " + id));
    }

    public void deleteBordereau(Integer id) {
        Bordereau bordereau = bordereauRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Bordereau non trouvé avec ID: " + id));
        if (!bordereau.getOperations().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer un bordereau ayant des opérations.");
        }
        bordereauRepository.delete(bordereau);
    }

    public void setTotalMontantInBordereau(Integer idBordereau) {
        Bordereau bordereau = bordereauRepository.getBordereauById(idBordereau);
        double totalMontant = bordereau.getOperations().stream()
                .mapToDouble(op -> Double.parseDouble(op.getMontant()))
                .sum();
        bordereau.setTotalAmount(String.valueOf(totalMontant));
        bordereauRepository.save(bordereau);
    }
}
