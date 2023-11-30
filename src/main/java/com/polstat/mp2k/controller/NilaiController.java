package com.polstat.mp2k.controller;

import com.polstat.mp2k.entity.MateriKU;
import com.polstat.mp2k.entity.Nilai;
import com.polstat.mp2k.entity.User;
import com.polstat.mp2k.enumeration.RolesEnum;
import com.polstat.mp2k.request.NilaiRequest;
import com.polstat.mp2k.response.MessageResponse;
import com.polstat.mp2k.response.NilaiResponse;
import com.polstat.mp2k.repository.NilaiRepository;
import com.polstat.mp2k.repository.UserRepository;
import com.polstat.mp2k.service.NilaiService;
import com.polstat.mp2k.service.UserActiveService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.polstat.mp2k.repository.MateriKURepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/nilai")
public class NilaiController {

    @Autowired
    MateriKURepository materiKURepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NilaiRepository nilaiRepository;

    @Autowired
    UserActiveService userActiveService;

    @Autowired
    NilaiService nilaiService;

    @PostMapping()
    public ResponseEntity<?> createNilai(@RequestBody NilaiRequest request) {
        User userActiv = userActiveService.getUserActive();

        Optional<User> user = userRepository.findById(request.getIdMahasiswa());
        User u = user.get();

        System.out.println(u.getRoles().toString().equals("[" + RolesEnum.Peserta.toString() + "]"));
        if (!u.getRoles().toString().equals("[" + RolesEnum.Peserta.toString() + "]")) {
            return ResponseEntity.ok(new MessageResponse("Role bukan mahasiswa"));
        }

        Optional<MateriKU> matkul = materiKURepository.findByName(request.getMateriKU());
        MateriKU mk = matkul.get();

        List<Nilai> DaftarNilai = nilaiRepository.findByUser_Id(u.getId());
        if (DaftarNilai != null) {
            for (Nilai nilai : DaftarNilai) {
                if (Objects.equals(nilai.getMateriKU().getId(), mk.getId())) {
                    return ResponseEntity.ok(new MessageResponse("Gagal menginput nilai dikarenakan nilai sudah dibuat untuk user dengan matkul dan periode tersebut"));
                }
            }
        }
        System.out.println("lolos pengecekan");

        Nilai nilai = new Nilai();

        nilai.setNilai_Pretest(request.getNilaiPretest());
        nilai.setNilai_Posttest(request.getNilaiPosttest());
        nilai.setUser(u);
        nilai.setMateriKU(mk);

        float nilaiAngka = nilaiService.getNilaiangka(nilai);
        String nilaiHuruf = nilaiService.getNilaiHuruf(nilaiAngka);
        float bobot = nilaiService.getBobot(nilaiHuruf);

        System.out.println("nilai angka = " + nilaiAngka);
        nilai.setNilai_Angka(nilaiAngka);
        nilai.setNilai_Huruf(nilaiHuruf);
        nilai.setBobot(bobot);

        nilaiRepository.save(nilai);

        NilaiResponse nilaires = new NilaiResponse();
        nilaires.setMateriKU(nilai.getMateriKU().toString());
        nilaires.setNilaiPretest(nilai.getNilai_Pretest());
        nilaires.setNilaiPosttest(nilai.getNilai_Posttest());
        nilaires.setNilaiHuruf(nilai.getNilai_Huruf());
        nilaires.setNilaiAngka(nilai.getNilai_Angka());
        nilaires.setBobot(nilai.getBobot());
        nilaires.setPeserta(nilai.getUser().getName());
        
        return ResponseEntity.ok(nilaires);
    }

    @GetMapping("/peserta/{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long userID) {

        User u = userActiveService.getUserActive();
        
        if (u.getRoles().toString().equals("["+RolesEnum.Peserta.toString()+"]")) {
            if (!u.getId().equals(userID)) {
                return ResponseEntity.ok("Anda tidak berhak melihat nilai user ini");
            }
        }
        List<Nilai> nilai = nilaiRepository.findByUser_Id(userID);

        List<NilaiResponse> listres = new ArrayList<>();
        for (Nilai n : nilai) {
            NilaiResponse nilaires = new NilaiResponse();
            nilaires.setMateriKU(n.getMateriKU().toString());
            nilaires.setNilaiPretest(n.getNilai_Pretest());
            nilaires.setNilaiPosttest(n.getNilai_Posttest());
            nilaires.setNilaiHuruf(n.getNilai_Huruf());
            nilaires.setNilaiAngka(n.getNilai_Angka());
            nilaires.setBobot(n.getBobot());
            nilaires.setPeserta(n.getUser().getName());
            listres.add(nilaires);
        }

        return ResponseEntity.ok(listres);
    }

    @GetMapping()
    public ResponseEntity<?> getAllNilai() {

        List<Nilai> nilai = (List<Nilai>) nilaiRepository.findAll();

        List<NilaiResponse> listres = new ArrayList<>();
        for (Nilai n : nilai) {
            NilaiResponse nilaires = new NilaiResponse();
            nilaires.setMateriKU(n.getMateriKU().toString());
            nilaires.setNilaiPretest(n.getNilai_Pretest());
            nilaires.setNilaiPosttest(n.getNilai_Posttest());
            nilaires.setNilaiHuruf(n.getNilai_Huruf());
            nilaires.setNilaiAngka(n.getNilai_Angka());
            nilaires.setBobot(n.getBobot());
            nilaires.setPeserta(n.getUser().getName());
            listres.add(nilaires);
        }

        return ResponseEntity.ok(listres);
    }
}
