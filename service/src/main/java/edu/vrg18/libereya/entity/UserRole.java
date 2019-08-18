package edu.vrg18.libereya.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "user_role_uk", columnNames = { "user_id", "role_id" }) })
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(generator = "uuid")
    @Getter
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id", nullable = false)
    @Getter
    @Setter
    private AppRole appRole;

    public UserRole(AppUser appUser, AppRole appRole) {
        this.appUser = appUser;
        this.appRole = appRole;
    }
}