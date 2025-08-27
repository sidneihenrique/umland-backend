package io.umland.umlandback.controller;

import io.umland.umlandback.domain.Fase;
import io.umland.umlandback.repository.FaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fases")
class FaseController {
    private final FaseRepository repository;
    public FaseController(FaseRepository repository) { this.repository = repository; }


    @GetMapping
    public List<Fase> listar() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Fase> buscar(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PostMapping
    public Fase criar(@RequestBody Fase f) { return repository.save(f); }
    @PutMapping("/{id}") public ResponseEntity<Fase> atualizar(@PathVariable Long id, @RequestBody Fase f) {
        return repository.findById(id).map(fase -> {
            fase.setTitulo(f.getTitulo()); fase.setPersonagem(f.getPersonagem());
            fase.setPontuacaoReputacao(f.getPontuacaoReputacao()); fase.setMoedas(f.getMoedas());
            return ResponseEntity.ok(repository.save(fase));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}