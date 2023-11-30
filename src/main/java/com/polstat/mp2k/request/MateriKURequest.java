
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
public class MateriKURequest {
    @NotBlank
    private String name;
    @NotBlank
    private int jumlahSks;
    @NotBlank
    private Long periodeID;

}
