
package com.polstat.mp2k.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NilaiResponse {
    private String materiKU;
    private float nilaiPretest;
    private float nilaiPosttest;
    private String nilaiHuruf;
    private float nilaiAngka;
    private float bobot;
    private String peserta;
    
}
