package com.C_TechProject.Operation;

import com.C_TechProject.bank.Bank;
import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @PostMapping("/addoperation")
    public ResponseEntity<Operation> createOperation(@RequestBody OperationRequest operation) {
        try {
            Operation newOperation = operationService.addOperation(operation);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newOperation);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/operations")
    public ResponseEntity<List<OperationResponse>> getAllOperations() {
        List<OperationResponse> operations = operationService.findAllOperations();
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }
    @GetMapping("/operation/{id}")
    public ResponseEntity<OperationResponse> getOperationById(@PathVariable Integer id) {
        try {
            OperationResponse operation = operationService.findOperationById(id);
            return ResponseEntity.ok(operation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deleteoperation/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable Integer id) {
        try {
            operationService.deleteoperation(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PutMapping("/updateoperation/{id}")
    public ResponseEntity<Operation> updateOperation(@PathVariable Integer id, @RequestBody OperationRequest operation) {
        try {
            Operation updateOperation = operationService.updateOperation(id, operation);
            return ResponseEntity.ok(updateOperation);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}