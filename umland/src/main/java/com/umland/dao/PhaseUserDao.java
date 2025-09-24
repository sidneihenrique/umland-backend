package com.umland.dao;

import com.umland.entities.PhaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PhaseUserDao extends JpaRepository<PhaseUser, Integer> {
    // Adicione métodos customizados se necessário
    List<PhaseUser> findByPhase_GameMap_IdAndUser_Id(Integer gameMapId, Integer userId);
    
    int countByUser_IdAndPhase_GameMap_Id(Integer userId, Integer gameMapId);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE phase_user SET user_diagram = cast(:userDiagram as json) WHERE id = :id", nativeQuery = true)
    int updateUserDiagramById(Integer id, String userDiagram);

    PhaseUser findByPhase_IdAndUser_Id(Integer phaseId, Integer userId);


}
