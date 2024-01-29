//package com.example.loc.global;
//
//import com.example.loc.domain.Location.Location;
//import com.example.loc.domain.Location.Type;
//import com.example.loc.domain.Member.Gender;
//import com.example.loc.domain.Member.Member;
//import com.example.loc.domain.Member.Role;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    public static long time;
//
//    @PostConstruct
//    public void init() {
//        initService.initMember();
//    }
//
//    @Service
//    @RequiredArgsConstructor
//    @Transactional
//    public static class InitService {
//        private final EntityManager em;
//        private final PasswordEncoder encoder;
//
//        public void initMember() {
//            Member customer = Member
//                    .builder()
//                    .email("customerEmail")
//                    .password(encoder.encode("pass2"))
//                    .nickname("customerNickname")
//                    .role(Role.Customer)
//                    .gender(Gender.Man)
//                    .birth(LocalDate.now())
//                    .build();
//            em.persist(customer);
//
//            Member owner = Member
//                    .builder()
//                    .email("ownerEmail")
//                    .password(encoder.encode("pass2"))
//                    .nickname("ownerNickname")
//                    .role(Role.Owner)
//                    .gender(Gender.Woman)
//                    .birth(LocalDate.now())
//                    .build();
//            em.persist(owner);
//
//            Location location = Location.builder()
//                    .name("name")
//                    .comment("comment")
//                    .phone("phone")
//                    .addr("addr")
//                    .type(Type.CAFE)
//                    .member(owner)
//                    .build();
//            em.persist(location);
//        }
//    }
//}
