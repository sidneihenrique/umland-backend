package com.umland.dao;

import com.umland.entities.PhaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhaseUserDao extends JpaRepository<PhaseUser, Integer> {
    // Adicione métodos customizados se necessário
    List<PhaseUser> findByPhase_GameMap_IdAndUser_Id(Integer gameMapId, Integer userId);

}
