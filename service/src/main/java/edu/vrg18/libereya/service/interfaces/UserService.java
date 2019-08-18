package edu.vrg18.libereya.service.interfaces;

import edu.vrg18.libereya.dto.AppUserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    AppUserDto getAppUserById(UUID id);
    void createAppUser(String userName, String password, boolean enabled);
    void updateAppUser(UUID id, String userName, String password, boolean enabled);
    void deleteAppUser(UUID id);
    List<AppUserDto> findAllAppUsers();
}
