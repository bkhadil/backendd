package com.system.core.controller;

import com.system.core.entity.*;
import com.system.core.service.EspaceService;
import com.system.core.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final EspaceService espaceService;

    @Autowired
    public ReservationController(ReservationService reservationService, EspaceService espaceService) {
        this.reservationService = reservationService;
        this.espaceService = espaceService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createReservation(
            @RequestBody @Valid ReservationRequest request,
            @AuthenticationPrincipal User user) {

        try {
            // Validation des dates
            if(request.dateStart().isAfter(request.dateEnd())) {
                return ResponseEntity.badRequest().body("Date de fin invalide");
            }

            // Récupération de l'espace
            Espace espace = espaceService.findById(request.espaceId());

            // Validation de la capacité
            if(request.nbrePlaces() > espace.getCapacite()) {
                return ResponseEntity.badRequest().body("Capacité dépassée");
            }

            // Création de la réservation
            Reservation reservation = new Reservation();
            reservation.setDateStart(request.dateStart());
            reservation.setDateEnd(request.dateEnd());
            reservation.setNbrePlaces(request.nbrePlaces());
            reservation.setUser(user);
            reservation.setEspace(espace);

            Reservation savedReservation = reservationService.createReservation(reservation);
            return ResponseEntity.ok(savedReservation);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "Conflit de réservation",
                            "message", ex.getMessage() // Message spécifique
                    ));
        }catch (Exception e) {
            // Gestion des autres erreurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Erreur interne",
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/partner")
    @PreAuthorize("hasRole('PARTNER')")
    public List<Reservation> getPartnerReservations(@AuthenticationPrincipal User partner) {
        return reservationService.getPartnerReservations(partner.getId().longValue()); // Conversion Integer → Long
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('PARTNER')")
    public Reservation updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return reservationService.updateReservationStatus(id, body.get("status"));
    }
    @GetMapping("/my-reservations")
    @PreAuthorize("hasRole('USER')")
    public List<Reservation> getUserReservations(@AuthenticationPrincipal User user) {
        return reservationService.findByUserId(user.getId());
    }
}
