package com.example.back.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.back.dto.UserDTO;
import com.example.back.repository.UserRepository;
import com.example.back.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder; 암호화

    //회원가입
    public void registerService(UserDTO dto){
        User user = dto.toEntity();
        //user.setPassword(passwordEncoder.encode(dto.getPassword()));      //암호화
        userRepository.save(user);
    }

    //로그인
    public User loginService(UserDTO dto){
        Optional<User> optionalUser = userRepository.findByUserid(dto.getUserid());

        if(optionalUser.isEmpty()){ //userId가 일치한게 없으면 null return
            return null;
        }
        User user = optionalUser.get();

        if(!user.getPassword().equals(dto.getPassword())) {
            return null;
        }
        // if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
        //     throw new RuntimeException();
        // }        암호화
        
        return user;
    }
}
