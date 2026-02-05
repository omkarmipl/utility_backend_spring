package com.utilitypro.gumbackend.service.business;

import com.utilitypro.gumbackend.domain.entity.AppUser;
import com.utilitypro.gumbackend.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public AppUser save(AppUser user) {
        return appUserRepository.save(user);
    }
}
