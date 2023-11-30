
package com.polstat.mp2k.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequest {
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String oldPassword;
    
    @NotBlank
    private String newPassword;
}
