package com.web.furniturehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.web.furniturehub.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
