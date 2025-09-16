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
        return phaseDao.findAll();
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
            phaseUser.setStatus(PhaseStatus.LOCKED); // status inicial
            phaseUser.setReputation(0);
            phaseUser.setCoins(0);
            phaseUserService.save(phaseUser);
        }

        return savedPhase;
    }

    public Phase update(Integer id, Phase phase) {
        phase.setId(id);
        return phaseDao.save(phase);
    }

    public void delete(Integer id) {
        phaseDao.deleteById(id);
    }
}
