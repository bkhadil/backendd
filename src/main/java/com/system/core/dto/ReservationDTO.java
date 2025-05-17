package com.system.core.dto;
import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDate;


@Getter
@Setter
public class ReservationDTO {
    private Long espaceId;
    @FutureOrPresent
    private LocalDate dateStart;
    @Future
    private LocalDate dateEnd;
    @Min(1)
    private int nbrePlaces;
}