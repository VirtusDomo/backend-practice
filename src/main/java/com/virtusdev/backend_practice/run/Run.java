package com.virtusdev.backend_practice.run;

import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record Run (
    @Id 
    Integer id,
    @NotEmpty 
    String title,
    LocalDateTime startedOn,
    LocalDateTime completedOn,
    @Positive
    Integer miles,
    Location location,
    @Version 
    Integer version
){
    public Run{
    if(!completedOn.isAfter(startedOn)){
            throw new IllegalArgumentException("Complete On must be after Started On");
        }
    }
    
}
