package org.mufasadev.mshando.core.tasker.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class TaskerRequest{
    private String bio;
    private String location;
    private Double hourlyRate;
    private Double rating;
    private LocalDate availableFrom;
    private LocalDate availableTo;
}