package io.umland.umlandback.controller;

import io.umland.umlandback.controller.dto.InventarioDTO;
import io.umland.umlandback.domain.Inventario;
import io.umland.umlandback.domain.Item;
import io.umland.umlandback.domain.Usuario;
import io.umland.umlandback.repository.InventarioRepository;
import io.umland.umlandback.repository.ItemRepository;
import io.umland.umlandback.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ItemRepository itemRepository;

    public InventarioController(InventarioRepository repository,
                                UsuarioRepository usuarioRepository,
                                ItemRepository itemRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.itemRepository = itemRepository;
    }

    // Listar todos
    @GetMapping
    public List<InventarioDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar
    @PostMapping
    public ResponseEntity<InventarioDTO> criar(@RequestBody InventarioDTO dto) {
        Inventario inventario = new Inventario();
        if (!mapDtoToEntity(dto, inventario)) {
            return ResponseEntity.badRequest().build();
        }
        Inventario salvo = repository.save(inventario);
        return ResponseEntity.ok(toDTO(salvo));
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> atualizar(@PathVariable Long id, @RequestBody InventarioDTO dto) {
        return repository.findById(id)
                .map(inventario -> {
                    if (!mapDtoToEntity(dto, inventario)) {
                        return ResponseEntity.badRequest().<InventarioDTO>build();
                    }
                    Inventario atualizado = repository.save(inventario);
                    return ResponseEntity.ok(toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(inv -> {
                    repository.delete(inv);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Conversão Entity -> DTO
    private InventarioDTO toDTO(Inventario inv) {
        return new InventarioDTO(
                inv.getId(),
                inv.getUsuario() != null ? inv.getUsuario().getId() : null,
                inv.getItem() != null ? inv.getItem().getId() : null,
                inv.getQuantidade()
        );
    }

    // Mapear DTO -> Entity
    private boolean mapDtoToEntity(InventarioDTO dto, Inventario entity) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        Item item = itemRepository.findById(dto.getItemId()).orElse(null);

        if (usuario == null || item == null) return false; // inválido

        entity.setUsuario(usuario);
        entity.setItem(item);
        entity.setQuantidade(dto.getQuantidade());
        return true;
    }
}
