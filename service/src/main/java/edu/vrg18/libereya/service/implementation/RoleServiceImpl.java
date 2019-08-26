package edu.vrg18.libereya.service.implementation;

import edu.vrg18.libereya.dto.AppRoleDto;
import edu.vrg18.libereya.entity.AppRole;
import edu.vrg18.libereya.repository.RoleRepository;
import edu.vrg18.libereya.service.interfaces.RoleService;
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
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private ModelMapper roleMapper;

    @Autowired
    private void setRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setMapper(ModelMapper roleMapper) {
        this.roleMapper = roleMapper;
    }


    @Override
    public AppRoleDto getAppRoleById(UUID id) {
        return roleMapper.map(roleRepository.getOne(id), AppRoleDto.class);
    }

    @Override
    public void createAppRole(String name) {
        roleRepository.save(new AppRole(name));
    }

    @Override
    public void updateAppRole(UUID id, String name) {
        AppRole appRole = roleRepository.getOne(id);
        appRole.setRoleName(name);
        roleRepository.save(appRole);
    }

    @Override
    public void deleteAppRole(UUID id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<AppRoleDto> findAllAppRoles() {
        return roleRepository.findAll()
                .stream()
                .map(b -> roleMapper.map(b, AppRoleDto.class))
                .collect(Collectors.toList());
    }

}
