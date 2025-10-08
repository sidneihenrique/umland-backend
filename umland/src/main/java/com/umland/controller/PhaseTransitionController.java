package com.umland.controller;

import com.umland.entities.PhaseTransition;
import com.umland.service.PhaseTransitionService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PhaseTransitionController {

    private final PhaseTransitionService phaseTransitionService;

    public PhaseTransitionController(PhaseTransitionService service) {
        this.phaseTransitionService = service;
    }

    // GET /phases/{fromPhaseId}/transitions
    @GetMapping("/phases/{phaseId}/outgoing-transitions")
    public List<PhaseTransition> getByFromPhase(@PathVariable Integer phaseId) {
    	List<PhaseTransition> transitions = phaseTransitionService.getByFromPhase(phaseId);
        return transitions;
    }

    // GET /phases/{toPhaseId}/incoming-transitions
    @GetMapping("/phases/{phaseId}/incoming-transitions")
    public List<PhaseTransition> getByToPhase(@PathVariable Integer phaseId) {
        return phaseTransitionService.getByToPhase(phaseId);
    }

    // POST /phase-transitions
    @PostMapping("/phase-transitions")
    public ResponseEntity<PhaseTransition> create(@RequestBody CreateTransitionRequest body) {
        if (body == null || body.getFromPhase() == null) {
            return ResponseEntity.badRequest().build();
        }
        PhaseTransition created =
    		phaseTransitionService.create(body.getFromPhase(), body.getToPhase(), body.getOptionText());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    // PUT /phase-transitions/{id}
    @PutMapping("/phase-transitions/{id}")
    public ResponseEntity<PhaseTransition> update(
            @PathVariable Integer id,
            @RequestBody CreateTransitionRequest body) {

        PhaseTransition updated = phaseTransitionService.update(
                id,
                body.getFromPhase(),   // pode ser null (mantém atual)
                body.getToPhase(),     // pode ser null (fim do fluxo)
                body.getOptionText()); // pode ser null

        return ResponseEntity.ok(updated);
    }

    // DELETE /phase-transitions/{id}
    @DeleteMapping("/phase-transitions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
    	phaseTransitionService.delete(id);
    }

    // DTO para o POST
    public static class CreateTransitionRequest {
        private Integer fromPhase;
        private Integer toPhase;      // pode ser null
        private String optionText;    // pode ser null

        public Integer getFromPhase() { return fromPhase; }
        public void setFromPhase(Integer fromPhase) { this.fromPhase = fromPhase; }
        public Integer getToPhase() { return toPhase; }
        public void setToPhase(Integer toPhase) { this.toPhase = toPhase; }
        public String getOptionText() { return optionText; }
        public void setOptionText(String optionText) { this.optionText = optionText; }
    }
}