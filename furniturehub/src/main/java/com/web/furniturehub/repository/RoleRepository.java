package com.web.furniturehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.furniturehub.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
