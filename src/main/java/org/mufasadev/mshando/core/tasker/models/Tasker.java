package org.mufasadev.mshando.core.tasker.models;

import jakarta.persistence.*;
import lombok.*;
import org.mufasadev.mshando.core.user.models.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Tasker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String bio;
    private String location;
    private Double latitude;
    private Double longitude;
    private Double hourlyRate;
    private Double rating;
    private LocalDate availableFrom;
    private LocalDate availableTo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tasker_skills",
            joinColumns = @JoinColumn(name = "tasker_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}