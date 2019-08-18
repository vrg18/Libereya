package edu.vrg18.libereya.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "app_role", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "app_role_uk", columnNames = "role_name") })
@NoArgsConstructor
public class AppRole {

    @Id
    @Column(name = "role_id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Getter
    private UUID roleId;

    @Column(name = "role_name", length = 30, nullable = false)
    @Getter
    @Setter
    private String roleName;

    public AppRole(String roleName) {
        this.roleName = roleName;
    }
}