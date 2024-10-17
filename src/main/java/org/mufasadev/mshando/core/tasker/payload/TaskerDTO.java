package org.mufasadev.mshando.core.tasker.payload;

import lombok.*;

import java.time.LocalDateTime;

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
    private String location;
    private Double hourlyRate;
    private Double rating;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
    private String profilePicture;
}