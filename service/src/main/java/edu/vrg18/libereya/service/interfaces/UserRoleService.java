package edu.vrg18.libereya.service.interfaces;

import edu.vrg18.libereya.dto.UserRoleDto;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    UserRoleDto getUserRoleById(UUID id);
    void createUserRole(UUID userId, UUID roleId);
    void updateUserRole(UUID id, UUID userId, UUID roleId);
    void deleteUserRole(UUID id);
    List<UserRoleDto> findAllUsersRoles();
}
