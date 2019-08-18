package edu.vrg18.libereya.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AppRoleDto {

    private UUID roleId;
    private String roleName;
}