package com.polstat.mp2k.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IPKelulusanResponse {
    private Long id;
    private float ipKelulusan;
    private String namaMahasiswa;
}
