package com.polstat.mp2k.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NilaiRequest {

    @NotBlank
    private float nilaiPretest;
    @NotBlank 
    private float nilaiPosttest;
    
    @NotBlank
    private String materiKU;
    @NotBlank
    private Long idMahasiswa;
}
