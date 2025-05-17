package com.system.core.repository;

import com.system.core.entity.CoWorkspace;
import com.system.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface CoWorkspaceRepository extends JpaRepository<CoWorkspace, Long> {

    @Query("SELECT c FROM CoWorkspace c WHERE "
            + "(:city IS NULL OR c.ville = :city) AND "
            + "(:searchQuery IS NULL OR "
            + "LOWER(c.nom) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR "
            + "LOWER(c.description) LIKE LOWER(CONCAT('%', :searchQuery, '%')))")
    List<CoWorkspace> searchByCityAndQuery(
            @Param("city") String city,
            @Param("searchQuery") String searchQuery
    );
    @Query("SELECT c FROM CoWorkspace c LEFT JOIN FETCH c.espaces WHERE c.idCoWorkspace = :id")
    Optional<CoWorkspace> findByIdWithEspaces(@Param("id") Long id);



    List<CoWorkspace> findByPartner(User partner);




    @Query("SELECT c FROM CoWorkspace c " +
            "LEFT JOIN FETCH c.equipments " +
            "LEFT JOIN FETCH c.espaces " +
            "WHERE c.partner = :partner")
    List<CoWorkspace> findByPartnerWithRelations(@Param("partner") User partner);
}



