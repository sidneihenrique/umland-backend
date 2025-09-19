package com.umland.service;

import com.umland.dao.GameMapDao;
import com.umland.entities.GameMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umland.entities.PhaseUser;
import com.umland.dao.PhaseUserDao;

import java.util.List;

@Service
public class GameMapService {

    @Autowired
    private GameMapDao gameMapDao;
    
    @Autowired
    private PhaseUserDao phaseUserDao;

    public GameMap findById(Integer id) {
        return gameMapDao.findById(id).orElse(null);
    }
    
    public List<GameMap> findAll() {
		return gameMapDao.findAll();
	}

    public GameMap save(GameMap gameMap) {
        return gameMapDao.save(gameMap);
    }
    
    public void delete(Integer id) {
		gameMapDao.deleteById(id);
	}
    
    public List<PhaseUser> findPhaseUsersByGameMapAndUser(Integer gameMapId, Integer userId) {
        return phaseUserDao.findByPhase_GameMap_IdAndUser_Id(gameMapId, userId);
    }
}