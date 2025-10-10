package com.umland.controller;

import com.umland.entities.GameMap;
import com.umland.service.GameMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.umland.entities.Phase;
import com.umland.entities.PhaseTransition;
import com.umland.entities.PhaseUser;
import com.umland.entities.User;

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
        return gameMapService.getOrderedPhaseUsersByGameMapAndUser(gameMapId, userId);
    }
    
    @PostMapping("/{gameMapId}/set-to-user/{userId}")
    public ResponseEntity<GameMap> setGameMapToUser(@PathVariable Integer gameMapId, @PathVariable Integer userId) {
        GameMap gameMap = gameMapService.findById(gameMapId);
        if (gameMap == null) {
            return ResponseEntity.noContent().build();
        }
        User user = gameMapService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        // Validação para evitar duplicidade
        if (!user.getGameMaps().contains(gameMap)) {
            user.getGameMaps().add(gameMap);
        }
        if (!gameMap.getUsers().contains(user)) {
            gameMap.getUsers().add(user);
        }
        gameMapService.saveGameMapAndUser(gameMap, user);
        return ResponseEntity.ok(gameMap);
    }
    
    @GetMapping("/{gameMapId}/phase-transitions")
    public List<PhaseTransition> getPhaseTransitionsByGameMapId(@PathVariable Integer gameMapId) {
        List<PhaseTransition> transitions = gameMapService.getPhaseTransitionsByGameMapId(gameMapId);
    	return transitions;
    }
}
