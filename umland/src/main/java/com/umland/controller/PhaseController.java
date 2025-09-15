package com.umland.controller;

import com.umland.entities.Phase;
import com.umland.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phases")
public class PhaseController {

    @Autowired
    private PhaseService phaseService;

    @GetMapping
    public List<Phase> getAllPhases() {
        return phaseService.findAll();
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
