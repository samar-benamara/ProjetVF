package com.C_TechProject.Bordereau;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BordereauRequest {

    private String Type ;
    private String number;

    private String reglement;

    private LocalDate date;
}
