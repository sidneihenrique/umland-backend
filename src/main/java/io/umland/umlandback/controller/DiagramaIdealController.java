package io.umland.umlandback.controller;

import io.umland.umlandback.domain.DiagramaIdeal;
import io.umland.umlandback.domain.Fase;
import io.umland.umlandback.controller.dto.DiagramaIdealDTO;
import io.umland.umlandback.repository.DiagramaIdealRepository;
import io.umland.umlandback.repository.FaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diagramas-ideais")
class DiagramaIdealController {
    private final DiagramaIdealRepository repository;
    private final FaseRepository faseRepository;

    public DiagramaIdealController(DiagramaIdealRepository repository, FaseRepository faseRepository) {
        this.repository = repository;
        this.faseRepository = faseRepository;
    }

    // Converter entidade -> DTO
    private DiagramaIdealDTO toDTO(DiagramaIdeal diagrama) {
        return new DiagramaIdealDTO(
                diagrama.getId(),
                diagrama.getRepresentacao(),
                diagrama.getFase() != null ? diagrama.getFase().getId() : null
        );
    }

    // Converter DTO -> entidade
    private DiagramaIdeal toEntity(DiagramaIdealDTO dto) {
        DiagramaIdeal diagrama = new DiagramaIdeal();
        diagrama.setId(dto.getId());
        diagrama.setRepresentacao(dto.getRepresentacao());

        if (dto.getFaseId() != null) {
            Fase fase = faseRepository.findById(dto.getFaseId())
                    .orElseThrow(() -> new IllegalArgumentException("Fase não encontrada com id " + dto.getFaseId()));
            diagrama.setFase(fase);
        }

        return diagrama;
    }

    @GetMapping
    public List<DiagramaIdealDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagramaIdealDTO> buscar(@PathVariable Long id) {
        return repository.findById(id).map(diagrama -> ResponseEntity.ok(toDTO(diagrama)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DiagramaIdealDTO> criar(@RequestBody DiagramaIdealDTO dto) {
        DiagramaIdeal salvo = repository.save(toEntity(dto));
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagramaIdealDTO> atualizar(@PathVariable Long id, @RequestBody DiagramaIdealDTO dto) {
        return repository.findById(id).map(diagrama -> {
            diagrama.setRepresentacao(dto.getRepresentacao());

            if (dto.getFaseId() != null) {
                Fase fase = faseRepository.findById(dto.getFaseId())
                        .orElseThrow(() -> new IllegalArgumentException("Fase não encontrada com id " + dto.getFaseId()));
                diagrama.setFase(fase);
            }

            return ResponseEntity.ok(toDTO(repository.save(diagrama)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id).map(diagrama -> {
            repository.delete(diagrama);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
