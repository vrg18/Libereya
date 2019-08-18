package edu.vrg18.libereya.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "app_user", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "app_user_uk", columnNames = "user_name")})
@NoArgsConstructor
public class AppUser {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Getter
    private UUID userId;

    @Column(name = "user_name", length = 36, nullable = false)
    @Getter
    @Setter
    private String userName;

    @Column(name = "encrypted_password", length = 128, nullable = false)
    @Getter
    private String encryptedPassword;

    @Getter
    @Setter
    private boolean enabled;

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AppUser(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.encryptedPassword = encoder.encode(password);
        this.enabled = enabled;
    }

    public void setEncryptedPassword(String password) {
        this.encryptedPassword = encoder.encode(password);
    }
}
