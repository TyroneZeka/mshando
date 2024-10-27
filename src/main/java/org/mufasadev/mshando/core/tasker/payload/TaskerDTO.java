package org.mufasadev.mshando.core.tasker.payload;

import lombok.*;
import org.mufasadev.mshando.core.tasker.models.Skill;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class TaskerDTO {
    private String fullname;
    private String email;
    private String phone;
    private String bio;
    private List<Skill> skills;
    private String location;
    private Double hourlyRate;
    private Double rating;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
    private String profilePicture;
}