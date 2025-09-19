package com.umland.controller;

import com.umland.entities.GameMap;
import com.umland.service.GameMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.umland.entities.Phase;
import com.umland.entities.PhaseUser;

import java.util.List;

@RestController
@RequestMapping("/gamemaps")
public class GameMapController {

    @Autowired
    private GameMapService gameMapService;

    @GetMapping
    public List<GameMap> getAll() {
        // Retorna todos os GameMaps
        return gameMapService.findAll();
    }

    @GetMapping("/{id}")
    public GameMap getById(@PathVariable Integer id) {
        // Retorna GameMap por id
        return gameMapService.findById(id);
    }

    @PostMapping
    public GameMap create(@RequestBody GameMap gameMap) {
        // Cria novo GameMap
        return gameMapService.save(gameMap);
    }

    @PutMapping("/{id}")
    public GameMap update(@PathVariable Integer id, @RequestBody GameMap gameMap) {
        // Atualiza GameMap existente
        gameMap.setId(id);
        return gameMapService.save(gameMap);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        // Remove GameMap por id
        gameMapService.delete(id);
    }
    
    @GetMapping("/{id}/phases")
    public List<PhaseUser> getAllPhasesByUser(
            @PathVariable("id") Integer gameMapId,
            @RequestParam("userId") Integer userId) {
        GameMap gameMap = gameMapService.findById(gameMapId);
        if (gameMap == null) {
            throw new RuntimeException("GameMap não encontrado com id: " + gameMapId);
        }
        // Supondo que exista um método no serviço para buscar PhaseUser por GameMap e usuário
        return gameMapService.findPhaseUsersByGameMapAndUser(gameMapId, userId);
    }
}
