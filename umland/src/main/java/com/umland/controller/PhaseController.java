package com.umland.controller;

import com.umland.dto.PhaseDTO;
import com.umland.entities.Phase;
import com.umland.entities.PhaseTransition;
import com.umland.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/phases")
public class PhaseController {

    @Autowired
    private PhaseService phaseService;

//    @GetMapping
//    public List<Phase> getAllPhases() {
//        return phaseService.findAll();
//    }
    
    @GetMapping
    public List<PhaseDTO> getPhases() {
        List<Phase> phases = phaseService.findAll();
        return phases.stream().map(phase -> {
            PhaseDTO dto = new PhaseDTO();
            dto.setId(phase.getId());
            dto.setTitle(phase.getTitle());
            dto.setDescription(phase.getDescription());
            dto.setType(phase.getType());
            dto.setMode(phase.getMode());
            dto.setMaxTime(phase.getMaxTime());
            dto.setCharacter(phase.getCharacter());
            dto.setGameMap(phase.getGameMap());
            dto.setDiagramInitial(phase.getDiagramInitial());
            dto.setCorrectDiagrams(phase.getCorrectDiagrams());
            dto.setCharacterDialogues(phase.getCharacterDialogues());
            dto.setNodeType(phase.getNodeType());
            // Outgoing: collect target phase IDs
            dto.setOutgoingTransitionIds(
                phase.getOutgoingTransitions().stream()
                    .map(t -> t.getToPhase() != null ? t.getToPhase().getId() : null)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
            );

            // Incoming: collect source phase IDs
            dto.setIncomingTransitionIds(
                phase.getIncomingTransitions().stream()
                    .map(t -> t.getFromPhase() != null ? t.getFromPhase().getId() : null)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
            );
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Phase getPhaseById(@PathVariable Integer id) {
        return phaseService.findById(id);
    }

    @PostMapping
    public Phase createPhase(@RequestBody Phase phase) {
        return phaseService.save(phase);
    }

    @PutMapping("/{id}")
    public Phase updatePhase(@PathVariable Integer id, @RequestBody Phase phase) {
        return phaseService.update(id, phase);
    }

    @DeleteMapping("/{id}")
    public void deletePhase(@PathVariable Integer id) {
        phaseService.delete(id);
    }
}
