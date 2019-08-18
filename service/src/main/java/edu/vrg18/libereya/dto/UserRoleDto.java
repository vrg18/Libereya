package edu.vrg18.libereya.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRoleDto {

    private UUID id;
    private AppUserDto appUser;
    private AppRoleDto appRole;
}