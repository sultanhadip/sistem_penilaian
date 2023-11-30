package com.polstat.mp2k.controller;

import com.polstat.mp2k.dto.UserDto;
import com.polstat.mp2k.entity.User;
import com.polstat.mp2k.request.UpdatePasswordRequest;
import com.polstat.mp2k.request.UpdateProfilRequest;
import com.polstat.mp2k.response.MessageResponse;
import com.polstat.mp2k.response.ProfileResponse;
import com.polstat.mp2k.repository.UserRepository;
import com.polstat.mp2k.service.UserActiveService;
import com.polstat.mp2k.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    UserService userService;
    
    @Autowired
    UserActiveService userActiveService;
    
    @Autowired
    UserRepository userRepository;

    @PatchMapping("/updatePassword")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest request) {
        String userActiveEmail = userActiveService.getUserActive().getEmail();
        System.out.println("email "+ request.getEmail() + " activ "+ userActiveEmail);
        if (!request.getEmail().equals(userActiveEmail)) {
            return ResponseEntity.ok(new MessageResponse("Email tidak cocok"));
        }
        UserDto udto = new UserDto();
        udto.setEmail(request.getEmail());
        udto.setPassword(request.getOldPassword());

        int check = userService.check(udto, request.getNewPassword());

        if (check == 1) {
            return ResponseEntity.ok(new MessageResponse("Password berhasil diubah"));
        }
        return ResponseEntity.ok(new MessageResponse("Password gagal diubah"));
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> profile (HttpServletRequest request){
        System.out.println("coba req: "+((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization"));
    
        User user = userActiveService.getUserActive();   
        return ResponseEntity.ok(new ProfileResponse(user.getId(), user.getName(), user.getEmail(), user.getNIM()));
    
    }
    
     
    @PatchMapping("/profile")
    public ResponseEntity<?> editProfile(@RequestBody UpdateProfilRequest request){
        User user = userActiveService.getUserActive();
        
        user.setName(request.getName());
        
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Profil berhasil diubah"));
    }
    
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteAcount(){
        User user = userActiveService.getUserActive();
        userRepository.delete(user);
        
        return ResponseEntity.ok(new MessageResponse("Akun berhasil dihapus"));
    }
    
}
