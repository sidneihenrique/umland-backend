package com.umland.service;

import com.umland.dao.PhaseDao;
import com.umland.entities.Phase;
import com.umland.entities.User;
import com.umland.entities.PhaseUser;
import com.umland.entities.enums.PhaseStatus;
import com.umland.service.PhaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseService {

    @Autowired
    private PhaseDao phaseDao;
    
    @Autowired
    private PhaseUserService phaseUserService;

    public List<Phase> findAll() {
    	List<Phase> phases = phaseDao.findAll();
        return phases;
    }

    public Phase findById(Integer id) {
        return phaseDao.findById(id).orElse(null);
    }

    public Phase save(Phase phase) {
        Phase savedPhase = phaseDao.save(phase);

        // Cria PhaseUser para cada usuário do mesmo GameMap
        List<User> users = savedPhase.getGameMap().getUsers();
        for (User user : users) {
            PhaseUser phaseUser = new PhaseUser();
            phaseUser.setUser(user);
            phaseUser.setPhase(savedPhase);
            phaseUser.setStatus(PhaseStatus.LOCKED);
            phaseUser.setReputation(0);
            phaseUser.setCoins(0);
            phaseUserService.save(phaseUser);
        }

        return savedPhase;
    }

    public Phase update(Integer id, Phase phase) {
        Phase existingPhase = phaseDao.findById(id).orElseThrow(() -> new RuntimeException("Phase não encontrada"));

        existingPhase.setTitle(phase.getTitle());
        existingPhase.setDescription(phase.getDescription());
        existingPhase.setType(phase.getType());
        existingPhase.setMode(phase.getMode());
        existingPhase.setMaxTime(phase.getMaxTime());
        existingPhase.setCharacter(phase.getCharacter());
        existingPhase.setGameMap(phase.getGameMap());
        existingPhase.setCorrectDiagrams(phase.getCorrectDiagrams());
        existingPhase.setCharacterDialogues(phase.getCharacterDialogues());
        existingPhase.setDiagramInitial(phase.getDiagramInitial());
        // Não altera existingPhase.setPhaseUsers(...);
        
        // Garante PhaseUser para cada usuário do GameMap
        List<User> users = existingPhase.getGameMap().getUsers();
        for (User user : users) {
            PhaseUser phaseUser = phaseUserService.findByPhaseAndUserId(existingPhase.getId(), user.getId());
            if (phaseUser == null) {
                phaseUser = new PhaseUser();
                phaseUser.setUser(user);
                phaseUser.setPhase(existingPhase);
                phaseUser.setStatus(PhaseStatus.LOCKED);
                phaseUser.setReputation(0);
                phaseUser.setCoins(0);
                phaseUserService.save(phaseUser);
            }
        }

        return phaseDao.save(existingPhase);
    }

    public void delete(Integer id) {
        phaseDao.deleteById(id);
    }
}
