package com.umland.service;

import com.umland.dao.GameMapDao;
import com.umland.dao.PhaseTransitionDao;
import com.umland.entities.GameMap;
import com.umland.entities.PhaseTransition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.umland.entities.PhaseUser;
import com.umland.entities.User;
import com.umland.dao.PhaseUserDao;
import com.umland.dao.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
public class GameMapService {

    @Autowired
    private GameMapDao gameMapDao;
    
    @Autowired
    private PhaseUserDao phaseUserDao;
    
    @Autowired
    private PhaseTransitionDao phaseTransitionDao;
    
    @Autowired
    private UserDao userDao;

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
    
    public User findUserById(Integer id) {
        return userDao.findById(id).orElse(null);
    }

    public void saveGameMapAndUser(GameMap gameMap, User user) {
        gameMapDao.save(gameMap);
        userDao.save(user);
    }
    
    public List<PhaseTransition> getPhaseTransitionsByGameMapId(Integer gameMapId) {
        // Busca todas as transições onde o GameMap está associado às fases
        return phaseTransitionDao.findByFromPhase_GameMap_IdOrToPhase_GameMap_Id(null, gameMapId);
    }
    
    public List<PhaseUser> getOrderedPhaseUsersByGameMapAndUser(Integer gameMapId, Integer userId) {
        // Busca todos os PhaseUser para o GameMap e usuário
        List<PhaseUser> phaseUsers = phaseUserDao.findByPhase_GameMap_IdAndUser_Id(gameMapId, userId);

        // Busca todas as transições relacionadas ao GameMap
        List<PhaseTransition> transitions = phaseTransitionDao.findAll();

        // Mapeia as fases de origem para suas fases de destino
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (PhaseTransition transition : transitions) {
            if (transition.getFromPhase() != null && transition.getToPhase() != null) {
                graph.computeIfAbsent(transition.getFromPhase().getId(), k -> new ArrayList<>())
                     .add(transition.getToPhase().getId());
            }
        }

        // Realiza a ordenação topológica
        List<Integer> orderedPhaseIds = topologicalSort(graph);

        // Retorna os PhaseUser ordenados com base na ordem das fases
        return orderedPhaseIds.stream()
                .flatMap(phaseId -> phaseUsers.stream()
                        .filter(phaseUser -> phaseUser.getPhase().getId().equals(phaseId)))
                .collect(Collectors.toList());
    }

    private List<Integer> topologicalSort(Map<Integer, List<Integer>> graph) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (Integer node : graph.keySet()) {
            inDegree.put(node, 0);
        }
        for (List<Integer> neighbors : graph.values()) {
            for (Integer neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Integer current = queue.poll();
            result.add(current);

            if (graph.containsKey(current)) {
                for (Integer neighbor : graph.get(current)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        return result;
    }
}