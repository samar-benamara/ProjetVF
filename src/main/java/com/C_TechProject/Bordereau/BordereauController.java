package com.C_TechProject.Bordereau;

import com.C_TechProject.Operation.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bordereaux")
@RequiredArgsConstructor
public class BordereauController {

    private final BordereauCrudService crudService;
    private final BordereauOperationService operationService;
    private final BordereauCalculationService calculationService;

    @PostMapping
    public ResponseEntity<BordereauResponse> createBordereau(@RequestBody BordereauRequest request) {
        Bordereau created = crudService.createBordereau(request);
        return new ResponseEntity<>(convertToResponse(created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BordereauResponse>> getAllBordereaux() {
        List<Bordereau> bordereaux = crudService.getAllBordereaux();
        return ResponseEntity.ok(bordereaux.stream()
                .map(this::convertToResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BordereauResponse> getBordereauById(@PathVariable Integer id) {
        Bordereau bordereau = crudService.getBordereauById(id);
        return ResponseEntity.ok(convertToResponse(bordereau));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBordereau(@PathVariable Integer id) {
        crudService.deleteBordereau(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/operations")
    public ResponseEntity<BordereauResponse> addOperationsToBordereau(
            @PathVariable Integer id,
            @RequestBody List<Integer> operationIds
    ) {
        Bordereau updated = operationService.addOperations(id, operationIds);
        return ResponseEntity.ok(convertToResponse(updated));
    }

    @GetMapping("/{id}/operations")
    public ResponseEntity<List<OperationResponse>> getBordereauOperations(@PathVariable Integer id) {
        return ResponseEntity.ok(operationService.getBordereauOperations(id));
    }

    @PostMapping("/{id}/calculate")
    public ResponseEntity<Void> calculateTotalAmount(@PathVariable Integer id) {
        calculationService.calculateTotalAmount(id);
        return ResponseEntity.ok().build();
    }

    private BordereauResponse convertToResponse(Bordereau bordereau) {
        return BordereauResponse.builder()
                .id(bordereau.getId())
                .number(bordereau.getNumber())
                .type(bordereau.getType())
                .reglement(bordereau.getReglement())
                .date(bordereau.getDate())
                .totalAmount(bordereau.getTotalAmount())
                .operationCount(bordereau.getOperationCount())
                .operations(operationService.getBordereauOperations(bordereau.getId()))
                .build();
    }
}