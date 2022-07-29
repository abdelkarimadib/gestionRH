package com.demo.login.utils.init;

import com.demo.login.entity.User;
import com.demo.login.enums.Roles;
import com.demo.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class Init {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init() {
        User admin = User.builder()
                .email("admin@admin.com")
                .password("{bcrypt}$2a$10$KoMFzb.J2sDd3CgWxUPB2.18Non3cnn3HXYYFODRLkXzbc45d4gE2")
                .roles(Collections.singleton(Roles.ADMINISTRATEUR))
                .firstName("admin")
                .lastName("admin")
                .build();
        userRepository.save(admin);
    }
}
