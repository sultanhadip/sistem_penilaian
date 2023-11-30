package com.polstat.mp2k.controller;

import com.polstat.mp2k.entity.IP_Kelulusan;
import com.polstat.mp2k.enumeration.RolesEnum;
import com.polstat.mp2k.entity.Nilai;
import com.polstat.mp2k.entity.User;
import com.polstat.mp2k.response.IPKelulusanResponse;
import com.polstat.mp2k.response.MessageResponse;
import com.polstat.mp2k.repository.NilaiRepository;
import com.polstat.mp2k.repository.UserRepository;
import com.polstat.mp2k.service.NilaiService;
import com.polstat.mp2k.service.UserActiveService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.polstat.mp2k.repository.IPKelulusanrepository;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ipKelulusan")
public class IPKelulusanController {

    @Autowired
    NilaiRepository nilaiRepository;
    @Autowired
    IPKelulusanrepository ipKelulusanRepository;
    @Autowired
    NilaiService nilaiService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserActiveService userActiveService;

    @PostMapping()
    public ResponseEntity<?> generateIPS() {
        Iterable<Nilai> daftarNilai = nilaiRepository.findAll();
        Iterable<IP_Kelulusan> daftarIPKel = ipKelulusanRepository.findAll();

        int j = 0;
        for (IP_Kelulusan ipKelulusan : daftarIPKel) {
            j += 1;
        }

        System.out.println("j = " + j);
        List<Nilai> NilaiBaru = new ArrayList<>();

        Map<Long, Map<Long, List<Nilai>>> grouppedNilai = new HashMap<>();

        if (j != 0) {
            daftarNilai.forEach(n -> {
                boolean isDuplicate = false;

                for (IP_Kelulusan i : daftarIPKel) {
                    if (n.getUser().equals(i.getUser())) {
                        isDuplicate = true;
                        break;
                    }

                }
                if (!isDuplicate) {
                    System.out.println("Nilai belum terdaftar");
                    System.out.println("nilai: " + n.getId());
                    NilaiBaru.add(n);
                }
            });
        } else {
            daftarNilai.forEach(n -> {
                System.out.println("Nilai belum terdaftar");
                System.out.println("nilai: " + n.getId());
                NilaiBaru.add(n);
            });
        }

        Map<User, List<Nilai>> groupedData = NilaiBaru.stream().collect(Collectors.groupingBy(Nilai::getUser));

        for (User user : groupedData.keySet()) {
            List<Nilai> nilaiList = groupedData.get(user);
            IP_Kelulusan ipKelulusan = new IP_Kelulusan();

            ipKelulusan.setIpKelulusan(nilaiService.getIPKelulusan(nilaiList));
            ipKelulusan.setUser(user);
            ipKelulusanRepository.save(ipKelulusan);

            for (Nilai nilai : nilaiList) {
                System.out.println(nilai);
            }
        }
        return ResponseEntity.ok(new MessageResponse("IP Kelulusan berhasil digenerate"));
    }

    @GetMapping("/peserta")
    public ResponseEntity<?> getIPSself() {
        User u = userActiveService.getUserActive();
        if (!u.getRoles().toString().equals("[" + RolesEnum.Peserta.toString() + "]")) {
            return ResponseEntity.ok(new MessageResponse("User bukan peserta"));
        }
        List<IP_Kelulusan> ipKelulusan = ipKelulusanRepository.findByUserId(u.getId());

        List<IPKelulusanResponse> ipres = new ArrayList<>();

        for (IP_Kelulusan ip : ipKelulusan) {
            IPKelulusanResponse ipMaba = new IPKelulusanResponse();
            ipMaba.setId(ip.getId());
            System.out.println("ipKelulusan " + ip.getIpKelulusan());
            ipMaba.setNamaMahasiswa(ip.getUser().getName());
            ipMaba.setIpKelulusan(ip.getIpKelulusan());
            ipres.add(ipMaba);
        }

        return ResponseEntity.ok(ipres);
    }

    @GetMapping("/peserta/{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long userID) {
        Optional<User> user = userRepository.findById(userID);
        User u = user.get();
        if (!u.getRoles().toString().equals("[" + RolesEnum.Peserta.toString() + "]")) {
            return ResponseEntity.ok(new MessageResponse("User bukan peserta"));
        }
        List<IP_Kelulusan> ipKelulusan = ipKelulusanRepository.findByUserId(u.getId());

        List<IPKelulusanResponse> ipres = new ArrayList<>();

         for (IP_Kelulusan ip : ipKelulusan) {
            IPKelulusanResponse ipMaba = new IPKelulusanResponse();
            ipMaba.setId(ip.getId());
            System.out.println("ipKelulusan " + ip.getIpKelulusan());
            ipMaba.setNamaMahasiswa(ip.getUser().getName());
            ipMaba.setIpKelulusan(ip.getIpKelulusan());
            ipres.add(ipMaba);
        }

        return ResponseEntity.ok(ipres);
    }
}
