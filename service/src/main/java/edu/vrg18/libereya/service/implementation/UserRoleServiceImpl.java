package edu.vrg18.libereya.service.implementation;

import edu.vrg18.libereya.dto.UserRoleDto;
import edu.vrg18.libereya.entity.UserRole;
import edu.vrg18.libereya.repository.RoleRepository;
import edu.vrg18.libereya.repository.UserRepository;
import edu.vrg18.libereya.repository.UserRoleRepository;
import edu.vrg18.libereya.service.interfaces.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleRepository userRoleRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private ModelMapper userRoleMapper;

    @Autowired
    private void setRepository(
            UserRoleRepository userRoleRepository,
            UserRepository userRepository,
            RoleRepository roleRepository)
    {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setMapper(ModelMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public UserRoleDto getUserRoleById(UUID id) {
        return userRoleMapper.map(userRoleRepository.getOne(id), UserRoleDto.class);
    }

    @Override
    public void createUserRole(UUID userId, UUID roleId) {
        userRoleRepository.save(new UserRole(userRepository.getOne(userId), roleRepository.getOne(roleId)));
    }

    @Override
    public void updateUserRole(UUID id, UUID userId, UUID roleId) {
        UserRole userRole = userRoleRepository.getOne(id);
        userRole.setAppUser(userRepository.getOne(userId));
        userRole.setAppRole(roleRepository.getOne(roleId));
        userRoleRepository.save(userRole);
    }

    @Override
    public void deleteUserRole(UUID id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public List<UserRoleDto> findAllUsersRoles() {
        return userRoleRepository.findAll()
                .stream()
                .map(b -> userRoleMapper.map(b, UserRoleDto.class))
                .collect(Collectors.toList());
    }
}
