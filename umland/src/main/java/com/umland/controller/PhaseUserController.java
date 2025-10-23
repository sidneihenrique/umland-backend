package com.umland.controller;

import com.umland.entities.PhaseUser;
import com.umland.service.PhaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.umland.entities.enums.PhaseStatus;

import java.util.List;

@RestController
@RequestMapping("/phase-users")
public class PhaseUserController {

    @Autowired
    private PhaseUserService phaseUserService;

    @GetMapping
    public List<PhaseUser> getAll() {
        return phaseUserService.findAll();
    }

    @GetMapping("/by-phase-and-user")
    public PhaseUser getByPhaseAndUserId(@RequestParam Integer phaseId, @RequestParam Integer userId) {
        return phaseUserService.findByPhaseAndUserId(phaseId, userId);
    }

    @PostMapping
    public PhaseUser create(@RequestBody PhaseUser phaseUser) {
        // Busca quantas PhaseUser existem para o usuário e GameMap
        int count = phaseUserService.countByUserAndGameMap(
            phaseUser.getUser().getId(),
            phaseUser.getPhase().getGameMap().getId()
        );

        if (count == 0) {
            phaseUser.setStatus(PhaseStatus.AVAILABLE);
            phaseUser.setCurrent(true);
        } else {
            phaseUser.setStatus(PhaseStatus.LOCKED);
            phaseUser.setCurrent(false);
        }
        return phaseUserService.save(phaseUser);
    }
    
    @PutMapping("/{id}")
    public PhaseUser update(@PathVariable Integer id, @RequestBody PhaseUser phaseUser) {
    	phaseUser.setId(id);
    	return phaseUserService.save(phaseUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        phaseUserService.delete(id);
    }
    
    @PatchMapping(value = "/{id}/user-diagram", consumes = {"application/json", "text/plain"})
    public void updateUserDiagram(@PathVariable Integer id, @RequestBody String userDiagram) {
        phaseUserService.updateUserDiagram(id, userDiagram);
    }
    
    @PostMapping("/unlock-next")
    public void unlockNextPhase(@RequestBody UnlockRequest request) {
        phaseUserService.unlockNextPhaseForUser(request.getPhaseId(), request.getUserId());
    }

    public static class UnlockRequest {
        private Integer phaseId;
        private Integer userId;

        public Integer getPhaseId() {
            return phaseId;
        }

        public void setPhaseId(Integer phaseId) {
            this.phaseId = phaseId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
    
}
