package edu.vrg18.libereya.service.interfaces;

import edu.vrg18.libereya.dto.AppRoleDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    AppRoleDto getAppRoleById(UUID id);
    void createAppRole(String roleName);
    void updateAppRole(UUID id, String roleName);
    void deleteAppRole(UUID id);
    List<AppRoleDto> findAllAppRoles();
}
