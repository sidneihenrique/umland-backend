package io.umland.umlandback.controller;

import io.umland.umlandback.domain.Diagrama;
import io.umland.umlandback.repository.DiagramaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagramas")
class DiagramaController {
    private final DiagramaRepository repository;
    public DiagramaController(DiagramaRepository repository) { this.repository = repository; }


    @GetMapping
    public List<Diagrama> listar() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Diagrama> buscar(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PostMapping public Diagrama criar(@RequestBody Diagrama d) { return repository.save(d); }
    @PutMapping("/{id}") public ResponseEntity<Diagrama> atualizar(@PathVariable Long id, @RequestBody Diagrama d) {
        return repository.findById(id).map(diagrama -> {
            diagrama.setRepresentacao(d.getRepresentacao()); diagrama.setStatus(d.getStatus());
            return ResponseEntity.ok(repository.save(diagrama));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id).map(d -> { repository.delete(d); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
    }
}