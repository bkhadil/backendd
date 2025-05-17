package com.system.core.repository;

import com.system.core.entity.Equipment;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByNomIn(List<String> names);

    Optional<Equipment> findByNom(String name);
}
