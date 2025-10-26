package com.umland.service;

import com.umland.dao.PhaseTransitionDao;
import com.umland.dao.PhaseUserDao;
import com.umland.entities.PhaseUser;
import com.umland.entities.enums.PhaseStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PhaseUserService {

    @Autowired
    private PhaseUserDao phaseUserDao;
    
    @Autowired
    private PhaseTransitionDao phaseTransitionDao;

    public List<PhaseUser> findAll() {
        return phaseUserDao.findAll();
    }

    public PhaseUser findById(Integer id) {
        return phaseUserDao.findById(id).orElse(null);
    }
    
    public PhaseUser findByPhaseAndUserId(Integer phaseId, Integer userId) {
		return phaseUserDao.findByPhase_IdAndUser_Id(phaseId, userId);
	}

    public PhaseUser save(PhaseUser phaseUser) {
        return phaseUserDao.save(phaseUser);
    }

    public void delete(Integer id) {
        phaseUserDao.deleteById(id);
    }
    
    public int countByUserAndGameMap(Integer userId, Integer gameMapId) {
        return phaseUserDao.countByUser_IdAndPhase_GameMap_Id(userId, gameMapId);
    }
    
    public int updateUserDiagram(Integer id, String userDiagram) {
        return phaseUserDao.updateUserDiagramById(id, userDiagram);
    }
 
    // Altera o status do PhaseUser para AVAILABLE para o phaseId e userId informados
    public void unlockNextPhaseForUser(Integer phaseId, Integer userId) {
        PhaseUser phaseUser = findByPhaseAndUserId(phaseId, userId);
        if (phaseUser != null) {
        	phaseUser.setStatus(PhaseStatus.COMPLETED);
        	phaseUser.getPhase().getOutgoingTransitions().forEach(pt -> {
				PhaseUser nextPhaseUser = findByPhaseAndUserId(pt.getToPhase().getId(), userId);
				if (nextPhaseUser != null && nextPhaseUser.getStatus() == PhaseStatus.LOCKED) {
					nextPhaseUser.setStatus(PhaseStatus.AVAILABLE);
					nextPhaseUser.setCurrent(true);
					phaseUserDao.save(nextPhaseUser);
				}
			});
        	phaseUser.setCurrent(false);
            phaseUserDao.save(phaseUser);
        }
    }

}
