package edu.vrg18.libereya.repository;

import edu.vrg18.libereya.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {

    List<AppUser> findAll();

    AppUser findAppUserByUserName(String userName);
}
