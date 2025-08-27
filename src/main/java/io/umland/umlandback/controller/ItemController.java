package io.umland.umlandback.controller;

import io.umland.umlandback.domain.Item;
import io.umland.umlandback.repository.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
class ItemController {
    private final ItemRepository repository;
    public ItemController(ItemRepository repository) { this.repository = repository; }


    @GetMapping public List<Item> listar() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Item> buscar(@PathVariable Long id) { return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PostMapping public Item criar(@RequestBody Item i) { return repository.save(i); }
    @PutMapping("/{id}") public ResponseEntity<Item> atualizar(@PathVariable Long id, @RequestBody Item i) {
        return repository.findById(id).map(item -> {
            item.setNome(i.getNome()); item.setDescricao(i.getDescricao());
            return ResponseEntity.ok(repository.save(item));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id).map(i -> { repository.delete(i); return ResponseEntity.noContent().build(); }).orElse(ResponseEntity.notFound().build());
    }
}
