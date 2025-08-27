package io.umland.umlandback.controller;

import io.umland.umlandback.domain.Tentativa;
import io.umland.umlandback.repository.TentativaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tentativas")
class TentativaController {
    private final TentativaRepository repository;
    public TentativaController(TentativaRepository repository) { this.repository = repository; }


    @GetMapping public List<Tentativa> listar() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Tentativa> buscar(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PostMapping
    public Tentativa criar(@RequestBody Tentativa t) { return repository.save(t); }
    @PutMapping("/{id}") public ResponseEntity<Tentativa> atualizar(@PathVariable Long id, @RequestBody Tentativa t) {
        return repository.findById(id).map(tentativa -> {
            tentativa.setUsuario(t.getUsuario()); tentativa.setFase(t.getFase());
            tentativa.setDiagrama(t.getDiagrama()); tentativa.setResultado(t.getResultado());
            tentativa.setMoedasGanhas(t.getMoedasGanhas()); tentativa.setReputacaoGanha(t.getReputacaoGanha());
            return ResponseEntity.ok(repository.save(tentativa));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id).map(t -> { repository.delete(t); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
    }
}
