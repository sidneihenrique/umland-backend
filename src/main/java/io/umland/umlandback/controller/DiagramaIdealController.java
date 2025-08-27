package io.umland.umlandback.controller;

import io.umland.umlandback.domain.DiagramaIdeal;
import io.umland.umlandback.repository.DiagramaIdealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagramas-ideais")
class DiagramaIdealController {
    private final DiagramaIdealRepository repository;
    public DiagramaIdealController(DiagramaIdealRepository repository) { this.repository = repository; }


    @GetMapping public List<DiagramaIdeal> listar() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<DiagramaIdeal> buscar(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PostMapping public DiagramaIdeal criar(@RequestBody DiagramaIdeal d) { return repository.save(d); }
    @PutMapping("/{id}") public ResponseEntity<DiagramaIdeal> atualizar(@PathVariable Long id, @RequestBody DiagramaIdeal d) {
        return repository.findById(id).map(diagrama -> {
            diagrama.setRepresentacao(d.getRepresentacao()); diagrama.setFase(d.getFase());
            return ResponseEntity.ok(repository.save(diagrama));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id).map(d -> { repository.delete(d); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
    }
}