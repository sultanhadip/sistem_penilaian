package com.polstat.mp2k.service;

import com.polstat.mp2k.entity.Nilai;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NilaiService {

    public float getNilaiangka(Nilai nilai) {
        float nilaiAngka;
        if (nilai.getNilai_Pretest() == 0 || nilai.getNilai_Posttest() == 0) {
            nilaiAngka = 0;
        }
        nilaiAngka = (float) (0.6 * nilai.getNilai_Pretest() + 0.4 * nilai.getNilai_Posttest());
        
        return nilaiAngka;
    }

    public String getNilaiHuruf(float nilaiAngka) {
        String nilaiHuruf = "";
        if (nilaiAngka >= 85 && nilaiAngka <= 100) {
            nilaiHuruf = "A";
        } else {
            if (nilaiAngka >= 80) {
                nilaiHuruf = "A-";
            } else {
                if (nilaiAngka >= 75) {
                    nilaiHuruf = "B+";
                } else {
                    if (nilaiAngka >= 70) {
                        nilaiHuruf = "B";
                    } else {
                        if (nilaiAngka >= 65) {
                            nilaiHuruf = "C+";
                        } else {
                            if (nilaiAngka >= 60) {
                                nilaiHuruf = "C";
                            } else {
                                if (nilaiAngka >= 55) {
                                    nilaiHuruf = "D+";
                                } else {
                                    if (nilaiAngka > 0) {
                                        nilaiHuruf = "D";
                                    } else {
                                        nilaiHuruf = "E";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return nilaiHuruf;
    }

    public float getBobot(String nilaiHuruf) {
        float bobot = 0;
        switch (nilaiHuruf) {
            case "A":
                bobot = 4;
                break;
            case "A-":
                bobot = (float) 3.75;
                break;
            case "B+":
                bobot = (float) 3.5;
                break;
            case "B":
                bobot = (float) 3;
                break;
            case "C+":
                bobot = (float) 2.5;
                break;
            case "C":
                bobot = (float) 2;
                break;
            case "D+":
                bobot = (float) 1.5;
                break;
            case "D":
                bobot = (float) 1;
                break;
            case "E":
                bobot = (float) 0;
                break;
            default:
                throw new AssertionError();
        }
        return bobot;
    }
    
    public float getIPKelulusan(List<Nilai> Daftarnilai){
        float ipKel = 0;
        
        int sks=0;
        int totalNilai=0;
        for (Nilai nilai : Daftarnilai) {
            sks+=nilai.getMateriKU().getJumlahSKS();
            totalNilai+=(nilai.getBobot()*nilai.getMateriKU().getJumlahSKS());
        }
        
        ipKel = totalNilai/sks;
        
        return ipKel;
    }
}
