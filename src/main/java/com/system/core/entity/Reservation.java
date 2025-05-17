package com.system.core.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int nbrePlaces;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;
    private double total;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "espace_id")
    private Espace espace;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getNbrePlaces() {
        return nbrePlaces;
    }

    public void setNbrePlaces(int nbrePlaces) {
        this.nbrePlaces = nbrePlaces;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Espace getEspace() {
        return espace;
    }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public void setEspace(Espace espace) {
        this.espace = espace;
    }




    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}
