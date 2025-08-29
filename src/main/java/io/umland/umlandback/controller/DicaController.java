package io.umland.umlandback.controller;

import io.umland.umlandback.domain.Dica;
import io.umland.umlandback.repository.DicaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/dicas")
class DicaController {
    private final DicaRepository repository;
    public DicaController(DicaRepository repository) { this.repository = repository; }


    @GetMapping
    public List<Dica> listar() { return repository.findAll(); }
    @GetMapping("/id/{id}") public ResponseEntity<Dica> buscar(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PostMapping
    public Dica criar(@RequestBody Dica d) { return repository.save(d); }
    @PutMapping("/id/{id}") public ResponseEntity<Dica> atualizar(@PathVariable Long id, @RequestBody Dica d) {
        return repository.findById(id).map(dica -> {
            dica.setTexto(d.getTexto()); dica.setFase(d.getFase());
            return ResponseEntity.ok(repository.save(dica));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/id/{id}") public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id).map(d -> { repository.delete(d); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
    }
//    @GetMapping("/r")
//    public List<Dica> tresAleatorias() {
//        List<Dica> todas = repository.findAll();
//        Collections.shuffle(todas);
//        return todas.stream().limit(3).toList();
//    }
}