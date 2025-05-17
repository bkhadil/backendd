package com.system.core.controller;



import java.time.LocalDate;

public record ReservationRequest(
        Long espaceId,
        LocalDate dateStart,
        LocalDate dateEnd,
        int nbrePlaces
) {}