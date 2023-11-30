package com.polstat.mp2k.controller;

import com.polstat.mp2k.entity.MateriKU;
import com.polstat.mp2k.request.MateriKURequest;
import com.polstat.mp2k.response.MateriKUResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.polstat.mp2k.repository.MateriKURepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/materiKU")
public class MateriKUController {

    @Autowired
    MateriKURepository materiKURepository;

    @PostMapping()
    public ResponseEntity<?> createMateriKU(@RequestBody MateriKURequest request) {
        MateriKU materiKU = new MateriKU();
        materiKU.setName(request.getName());
        materiKU.setJumlahSKS(request.getJumlahSks());


        materiKURepository.save(materiKU);

        MateriKUResponse mkres = new MateriKUResponse();
        mkres.setId(materiKU.getId());
        mkres.setNama(materiKU.getName());

        return ResponseEntity.ok(mkres);
    }

    @GetMapping()
    public ResponseEntity<?> getAllMateriKU() {
        List<MateriKU> list = (List<MateriKU>) materiKURepository.findAll();

        List<MateriKUResponse> listRes = new ArrayList<>();
        for (MateriKU mk : list) {
            MateriKUResponse mkres = new MateriKUResponse();
            mkres.setId(mk.getId());
            mkres.setNama(mk.getName());
            listRes.add(mkres);
        }
        return ResponseEntity.ok(listRes);
    }
}
