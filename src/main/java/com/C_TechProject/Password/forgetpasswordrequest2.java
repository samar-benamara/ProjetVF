package com.C_TechProject.Password;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class forgetpasswordrequest2 {

    private String email;
    private String newPassword;
}
