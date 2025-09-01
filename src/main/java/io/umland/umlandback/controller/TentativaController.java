package io.umland.umlandback.controller;

import io.umland.umlandback.domain.*;
import io.umland.umlandback.controller.dto.TentativaDTO;
import io.umland.umlandback.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tentativas")
class TentativaController {

    private final TentativaRepository tentativaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FaseRepository faseRepository;
    private final DiagramaRepository diagramaRepository;

    public TentativaController(TentativaRepository tentativaRepository,
                               UsuarioRepository usuarioRepository,
                               FaseRepository faseRepository,
                               DiagramaRepository diagramaRepository) {
        this.tentativaRepository = tentativaRepository;
        this.usuarioRepository = usuarioRepository;
        this.faseRepository = faseRepository;
        this.diagramaRepository = diagramaRepository;
    }

    // ==================== MAPEAMENTO ====================

    private TentativaDTO toDTO(Tentativa tentativa) {
        return new TentativaDTO(
                tentativa.getId(),
                tentativa.getUsuario().getId(),
                tentativa.getFase().getId(),
                tentativa.getDiagrama().getId(),
                tentativa.getResultado(),
                tentativa.getMoedasGanhas(),
                tentativa.getReputacaoGanha(),
                tentativa.getCriadoEm()
        );
    }

    private Tentativa toEntity(TentativaDTO dto) {
        Tentativa tentativa = new Tentativa();
        tentativa.setId(dto.getId());
        tentativa.setResultado(dto.getResultado());
        tentativa.setMoedasGanhas(dto.getMoedasGanhas());
        tentativa.setReputacaoGanha(dto.getReputacaoGanha());
        tentativa.setCriadoEm(dto.getCriadoEm() != null ? dto.getCriadoEm() : tentativa.getCriadoEm());

        usuarioRepository.findById(dto.getUsuarioId()).ifPresent(tentativa::setUsuario);
        faseRepository.findById(dto.getFaseId()).ifPresent(tentativa::setFase);
        diagramaRepository.findById(dto.getDiagramaId()).ifPresent(tentativa::setDiagrama);

        return tentativa;
    }

    // ==================== ENDPOINTS ====================

    @GetMapping
    public List<TentativaDTO> listar() {
        return tentativaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TentativaDTO> buscar(@PathVariable Long id) {
        return tentativaRepository.findById(id)
                .map(t -> ResponseEntity.ok(toDTO(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TentativaDTO> criar(@RequestBody TentativaDTO dto) {
        Tentativa tentativa = toEntity(dto);
        Tentativa salvo = tentativaRepository.save(tentativa);
        return ResponseEntity.ok(toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TentativaDTO> atualizar(@PathVariable Long id, @RequestBody TentativaDTO dto) {
        return tentativaRepository.findById(id).map(existente -> {
            existente.setResultado(dto.getResultado());
            existente.setMoedasGanhas(dto.getMoedasGanhas());
            existente.setReputacaoGanha(dto.getReputacaoGanha());

            usuarioRepository.findById(dto.getUsuarioId()).ifPresent(existente::setUsuario);
            faseRepository.findById(dto.getFaseId()).ifPresent(existente::setFase);
            diagramaRepository.findById(dto.getDiagramaId()).ifPresent(existente::setDiagrama);

            Tentativa atualizado = tentativaRepository.save(existente);
            return ResponseEntity.ok(toDTO(atualizado));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return tentativaRepository.findById(id).map(t -> {
            tentativaRepository.delete(t);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}