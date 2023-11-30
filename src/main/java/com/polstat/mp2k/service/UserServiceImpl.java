package com.polstat.mp2k.service;


import com.polstat.mp2k.dto.UserDto;
import com.polstat.mp2k.entity.User;
import com.polstat.mp2k.mapper.UserMapper;
import com.polstat.mp2k.repository.UserRepository;
import java.util.Optional;
import static org.hibernate.query.sqm.tree.SqmNode.log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserCheckService userCheckService;
    
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.save(UserMapper.mapToUser(userDto));
        return UserMapper.mapToUserDto(user);
    }
//
//    @Override
//    public UserDto getUserByEmail(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        return UserMapper.mapToUserDto(user);
//    }

    @Override
    public int check(UserDto user, String newPassword) {
       String email = user.getEmail();
       String oldPassword = user.getPassword();
       
       
       boolean checkPassword = userCheckService.isTruePassword(email,oldPassword);
        
       Optional<User> existingUser = userRepository.findByEmail(email);
       User userNew = existingUser.get();
       
       if(checkPassword){
           log.info("memperbarui password");
           userNew.setPassword(passwordEncoder.encode(newPassword));
           
           System.out.println("User = "+ userNew);
           userRepository.save(userNew);
           
           log.info("berhasil update password");
           return 1;
       }
       log.info(this.getClass().getName()+ "ubah password gagal");
       return 0;
    }
}
