package io.umland.umlandback.controller;

import io.umland.umlandback.controller.dto.FaseDTO;
import io.umland.umlandback.domain.Fase;
import io.umland.umlandback.repository.FaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fases")
class FaseController {

    private final FaseRepository repository;

    public FaseController(FaseRepository repository) {
        this.repository = repository;
    }

    // LISTAR todas as fases
    @GetMapping
    public List<FaseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(FaseDTO::new)
                .toList();
    }

    // BUSCAR fase por id
    @GetMapping("/{id}")
    public ResponseEntity<FaseDTO> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(fase -> ResponseEntity.ok(new FaseDTO(fase)))
                .orElse(ResponseEntity.notFound().build());
    }

    // CRIAR fase
    @PostMapping
    public ResponseEntity<FaseDTO> criar(@RequestBody FaseDTO dto) {
        Fase fase = new Fase();
        fase.setTitulo(dto.getTitulo());
        fase.setPontuacaoReputacao(dto.getPontuacaoReputacao());
        fase.setMoedas(dto.getMoedas());
        // TODO: buscar personagem pelo personagemId e setar
        Fase salvo = repository.save(fase);
        return ResponseEntity.ok(new FaseDTO(salvo));
    }

    // ATUALIZAR fase
    @PutMapping("/{id}")
    public ResponseEntity<FaseDTO> atualizar(@PathVariable Long id, @RequestBody FaseDTO dto) {
        return repository.findById(id).map(fase -> {
            fase.setTitulo(dto.getTitulo());
            fase.setPontuacaoReputacao(dto.getPontuacaoReputacao());
            fase.setMoedas(dto.getMoedas());
            // TODO: buscar personagem pelo personagemId e setar
            Fase atualizado = repository.save(fase);
            return ResponseEntity.ok(new FaseDTO(atualizado));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETAR fase
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
