package edu.vrg18.libereya.controller;

import edu.vrg18.libereya.dto.AppRoleDto;
import edu.vrg18.libereya.dto.AppUserDto;
import edu.vrg18.libereya.dto.UserRoleDto;
import edu.vrg18.libereya.service.interfaces.RoleService;
import edu.vrg18.libereya.service.interfaces.UserRoleService;
import edu.vrg18.libereya.service.interfaces.UserService;
import edu.vrg18.libereya.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class SecurityController {

    private UserService userService;
    private RoleService roleService;
    private UserRoleService userRoleService;

    @Autowired
    public void setService(UserService userService, RoleService roleService, UserRoleService userRoleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPage(Model model, Principal principal) {

        model.addAttribute("title", "AdminPage");

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        List<AppUserDto> usersDto = userService.findAllAppUsers();
        model.addAttribute("users", usersDto);

        List<AppRoleDto> rolesDto = roleService.findAllAppRoles();
        model.addAttribute("roles", rolesDto);

        List<UserRoleDto> usersRolesDto = userRoleService.findAllUsersRoles();
        model.addAttribute("usersRoles", usersRolesDto);

        return "security/adminPage";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Login");
        return "security/loginPage";
    }

    @GetMapping("/logout_successful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "security/logoutSuccessfulPage";
    }

    @GetMapping("/user_info")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userInfo(Model model, Principal principal) {

        model.addAttribute("title", "UserInfo");
        // After user login successfully.
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "security/userInfoPage";
    }

    @GetMapping("/403")
    public String accessDenied(Model model, Principal principal) {

        model.addAttribute("title", "Error403");

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("userInfo", userInfo);
            String message = "You do not have permission!";
            model.addAttribute("message", message);
        }

        return "security/403Page";
    }

    @GetMapping("/edit_user/{id}")
    public String editUser(@PathVariable UUID id, Model model) {
        model.addAttribute("title", "EditUser");
        AppUserDto appUserDto = userService.getAppUserById(id);
        model.addAttribute("appUser", appUserDto);
        return "security/editUser";
    }

    @PostMapping("/update_user")
    public String updateUser(@RequestParam UUID userId, @RequestParam String userName, @RequestParam String password, boolean enabled) {
        userService.updateAppUser(userId, userName, password, enabled);
        return "redirect:/admin";
    }

    @GetMapping("/new_user")
    public String newUser(Model model) {
        model.addAttribute("title", "NewUser");
        return "security/newUser";
    }

    @PostMapping("/create_user")
    public String createUser(@RequestParam String userName, @RequestParam String password, boolean enabled) {
        userService.createAppUser(userName, password, enabled);
        return "redirect:/admin";
    }

    @GetMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteAppUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit_role/{id}")
    public String editRole(@PathVariable UUID id, Model model) {
        model.addAttribute("title", "EditRole");
        AppRoleDto appRoleDto = roleService.getAppRoleById(id);
        model.addAttribute("appRole", appRoleDto);
        return "security/editRole";
    }

    @PostMapping("/update_role")
    public String updateRole(@RequestParam UUID roleId, @RequestParam String roleName) {
        roleService.updateAppRole(roleId, roleName);
        return "redirect:/admin";
    }

    @GetMapping("/new_role")
    public String newRole(Model model) {
        model.addAttribute("title", "NewRole");
        return "security/newRole";
    }

    @PostMapping("/create_role")
    public String createRole(@RequestParam String roleName) {
        roleService.createAppRole(roleName);
        return "redirect:/admin";
    }

    @GetMapping("/delete_role/{id}")
    public String deleteRole(@PathVariable UUID id) {
        roleService.deleteAppRole(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit_userrole/{id}")
    public String editUserRole(@PathVariable UUID id, Model model) {

        model.addAttribute("title", "EditUserRole");

        UserRoleDto userRoleDto = userRoleService.getUserRoleById(id);
        model.addAttribute("userRole", userRoleDto);

        List<AppUserDto> appUsersDto = userService.findAllAppUsers();
        model.addAttribute("appUsers", appUsersDto);

        List<AppRoleDto> appRolesDto = roleService.findAllAppRoles();
        model.addAttribute("appRoles", appRolesDto);

        return "security/editUserRole";
    }

    @PostMapping("/update_userrole")
    public String updateUserRole(@RequestParam UUID id, @RequestParam UUID userId, @RequestParam UUID roleId) {
        userRoleService.updateUserRole(id, userId, roleId);
        return "redirect:/admin";
    }

    @GetMapping("/new_userrole")
    public String newUserRole(Model model) {
        model.addAttribute("title", "NewUserRole");
        List<AppUserDto> appUsersDto = userService.findAllAppUsers();
        model.addAttribute("appUsers", appUsersDto);
        List<AppRoleDto> appRolesDto = roleService.findAllAppRoles();
        model.addAttribute("appRoles", appRolesDto);
        return "security/newUserRole";
    }

    @PostMapping("/create_userrole")
    public String createUserRole(@RequestParam UUID userId, @RequestParam UUID roleId) {
        userRoleService.createUserRole(userId, roleId);
        return "redirect:/admin";
    }

    @GetMapping("/delete_userrole/{id}")
    public String deleteUserRole(@PathVariable UUID id) {
        userRoleService.deleteUserRole(id);
        return "redirect:/admin";
    }
}
