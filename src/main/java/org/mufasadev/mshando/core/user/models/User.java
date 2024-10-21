package org.mufasadev.mshando.core.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.mufasadev.mshando.core.tasker.models.Tasker;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {
    //TODO: Change implementation to implement UserDetails here without UserDetailsImpl

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;
    private String firstname;
    private String lastname;
    @Size(min = 10, max = 15)
    private String phone;
    private String profilePicture;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private Tasker tasker;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String fullName(){
        return firstname + " " + lastname;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}