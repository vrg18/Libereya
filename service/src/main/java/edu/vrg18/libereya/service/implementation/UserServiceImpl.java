package edu.vrg18.libereya.service.implementation;

import edu.vrg18.libereya.dto.AppUserDto;
import edu.vrg18.libereya.entity.AppUser;
import edu.vrg18.libereya.repository.UserRepository;
import edu.vrg18.libereya.service.interfaces.UserService;
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
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper userMapper;

    @Autowired
    private void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setMapper(ModelMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public AppUserDto getAppUserById(UUID id) {
        return userMapper.map(userRepository.getOne(id), AppUserDto.class);
    }

    @Override
    public void createAppUser(String name, String password, boolean enabled) {
        userRepository.save(new AppUser(name, password, enabled));
    }

    @Override
    public void updateAppUser(UUID id, String name, String password, boolean enabled) {
        AppUser appUser = userRepository.getOne(id);
        appUser.setUserName(name);
        if (!password.equals("password")) {
            appUser.setEncryptedPassword(password);
        }
        appUser.setEnabled(enabled);
        userRepository.save(appUser);
    }

    @Override
    public void deleteAppUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<AppUserDto> findAllAppUsers() {
        return userRepository.findAll().stream()
                .map(b -> userMapper.map(b, AppUserDto.class))
                .collect(Collectors.toList());
    }
}
