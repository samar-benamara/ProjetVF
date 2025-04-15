package com.C_TechProject.Bordereau;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BordereauRequest {
    private String type;
    private String number;
    private String reglement;
    private LocalDate date;
}
