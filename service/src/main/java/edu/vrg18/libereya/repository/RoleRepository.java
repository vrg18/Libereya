package edu.vrg18.libereya.repository;

import edu.vrg18.libereya.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<AppRole, UUID> {

    List<AppRole> findAll();
}
