package com.C_TechProject.Bordereau;

import com.C_TechProject.Operation.Operation;
import com.C_TechProject.Operation.OperationResponse;
import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class BordereauController {

    public final Bordereauservice bordereauservice;

    @PostMapping("/addbordereau")
    public ResponseEntity<Bordereau> addbv (@RequestBody BordereauRequest bordereauVersement) {
        Bordereau bordereau1 = bordereauservice.addBordereau(bordereauVersement);
        return new ResponseEntity<>(bordereau1, HttpStatus.CREATED);
    }

    @PostMapping("addoperationstobordereau/{idBordereau}")
    public Bordereau addOperationsToBordereau(@PathVariable Integer idBordereau, @RequestBody List<Integer> idsOperations) {
        return bordereauservice.addOperationsToBordereau(idBordereau, idsOperations);
    }


    @GetMapping("/bordereaux")
    public ResponseEntity<List<Bordereau>> getAllBv() {
        List<Bordereau> bvs = bordereauservice.findAllBodereaux();
        return new ResponseEntity<>(bvs, HttpStatus.OK);
    }

    @GetMapping("/bordereau/{id}")
    public ResponseEntity<Bordereau> getBVtById(@PathVariable Integer id) {
        try {
            Bordereau bordereau = bordereauservice.findBordereauByid(id);
            return ResponseEntity.ok(bordereau);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/deletebordereau/{id}")
    public ResponseEntity<Void> deletBordereau(@PathVariable Integer id) {
        try {
            bordereauservice.deleteBordereau(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @GetMapping("bordereau/operations/{id}")
    public List<OperationResponse> getAllOperationsInBordereau(@PathVariable("id") Integer idBordereau) {
        Bordereau bordereau = bordereauservice.findBordereauByid(idBordereau);
        return bordereauservice.getOperationsInBordereau(idBordereau);
    }



}

