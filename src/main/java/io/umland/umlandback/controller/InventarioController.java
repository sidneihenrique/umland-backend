package io.umland.umlandback.controller;

import io.umland.umlandback.domain.Inventario;
import io.umland.umlandback.repository.InventarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
class InventarioController {
    private final InventarioRepository repository;
    public InventarioController(InventarioRepository repository) { this.repository = repository; }


    @GetMapping public List<Inventario> listar() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Inventario> buscar(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PostMapping public Inventario criar(@RequestBody Inventario inv) { return repository.save(inv); }
    @PutMapping("/{id}") public ResponseEntity<Inventario> atualizar(@PathVariable Long id, @RequestBody Inventario inv) {
        return repository.findById(id).map(inventario -> {
            inventario.setUsuario(inv.getUsuario()); inventario.setItem(inv.getItem()); inventario.setQuantidade(inv.getQuantidade());
            return ResponseEntity.ok(repository.save(inventario));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id).map(inv -> { repository.delete(inv); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
    }
}