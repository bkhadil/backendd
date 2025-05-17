package com.system.core.repository;
import com.system.core.entity.Espace;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspaceRepository extends JpaRepository<Espace, Long> {
    List<Espace> findByCoWorkspaceIdCoWorkspace(Long coWorkspaceId);
}

