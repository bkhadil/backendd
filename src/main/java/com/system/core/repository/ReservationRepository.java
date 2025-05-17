package com.system.core.repository;

import com.system.core.entity.Reservation;
import com.system.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r "
            + "JOIN FETCH r.espace e "
            + "JOIN FETCH e.coWorkspace c " // Force le chargement
            + "JOIN FETCH c.partner p "
            + "WHERE p.id = :partnerId")
    List<Reservation> findByEspaceCoWorkspacePartnerId(Long partnerId);
    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId")
    List<Reservation> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(r) FROM Reservation r " +
            "WHERE r.espace.idEspace = :espaceId " +
            "AND r.status = 'ACCEPTED' " +
            "AND (r.dateStart <= :endDate AND r.dateEnd >= :startDate)")
    int countAcceptedReservationsForEspaceInDateRange(
            @Param("espaceId") Long espaceId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}