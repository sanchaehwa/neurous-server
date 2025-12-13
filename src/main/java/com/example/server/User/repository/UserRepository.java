package com.example.server.User.repository;


import com.example.server.User.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    Optional<User> findByProviderId(String providerId);
    Optional<User> findByDisplayName(String displayName);
    boolean existsByDisplayName(String displayName);
}