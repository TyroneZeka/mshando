package org.mufasadev.mshando.core.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstname;
    private String lastname;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "boolean default false")
    private boolean enabled;
    @Column(columnDefinition = "boolean default true")
    private boolean accountLocked;

    @CreatedDate
    @Column(updatable = false,nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String encode) {
        this.username = username;
        this.email = email;
        this.password = encode;
    }

    public String getFullName(){
        return firstname + " " + lastname;
    }
}