package com.umland.service;

import com.umland.dao.GameMapDao;
import com.umland.entities.GameMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameMapService {

    @Autowired
    private GameMapDao gameMapDao;

    public GameMap findById(Integer id) {
        return gameMapDao.findById(id).orElse(null);
    }

    public GameMap save(GameMap gameMap) {
        return gameMapDao.save(gameMap);
    }
}