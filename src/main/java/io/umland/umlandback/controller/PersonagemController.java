package io.umland.umlandback.controller;

import io.umland.umlandback.domain.Personagem;
import io.umland.umlandback.repository.PersonagemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personagens")
class PersonagemController {
    private final PersonagemRepository repository;
    public PersonagemController(PersonagemRepository repository) { this.repository = repository; }

    @GetMapping
    public List<Personagem> listar() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Personagem> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Personagem criar(@RequestBody Personagem p) {
        return repository.save(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personagem> atualizar(@PathVariable Long id, @RequestBody Personagem p) {
        return repository.findById(id).map(per -> {
            per.setNome(p.getNome());
            per.setImagem(p.getImagem()); // Removido setFala
            return ResponseEntity.ok(repository.save(per));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}