package edu.vrg18.libereya.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AppUserDto {

    private UUID userId;
    private String userName;
    private String encryptedPassword;
    private boolean enabled;
}
