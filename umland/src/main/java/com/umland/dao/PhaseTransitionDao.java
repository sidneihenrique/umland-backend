package com.umland.dao;

import com.umland.entities.Phase;
import com.umland.entities.PhaseTransition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseTransitionDao extends JpaRepository<PhaseTransition, Integer> {
    List<PhaseTransition> findByFromPhase_Id(Integer fromPhaseId);
    List<PhaseTransition> findByToPhase_Id(Integer toPhaseId);
    List<PhaseTransition> findByFromPhase_GameMap_IdOrToPhase_GameMap_Id(Integer fromGameMapId, Integer toGameMapId);
    
    boolean existsByFromPhaseAndToPhase(Phase fromPhase, Phase toPhase);


}
