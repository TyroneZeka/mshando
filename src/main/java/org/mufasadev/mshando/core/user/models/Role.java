package org.mufasadev.mshando.core.user.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    @Column(length = 25, name = "roleName")
    private AppRole name;

    public Role(AppRole appRole){
        this.name = appRole;
    }
}