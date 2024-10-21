package org.mufasadev.mshando.core.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer id;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    @Column(length = 25, name = "roleName")
    private AppRole name;
    public Role(AppRole appRole){
        this.name = appRole;
    }
}