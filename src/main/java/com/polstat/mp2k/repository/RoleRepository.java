package com.polstat.mp2k.repository;

import com.polstat.mp2k.entity.Role;
import com.polstat.mp2k.enumeration.RolesEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RolesEnum name);
}
