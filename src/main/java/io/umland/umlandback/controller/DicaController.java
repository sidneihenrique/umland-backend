package io.umland.umlandback.controller;

import io.umland.umlandback.domain.Dica;
import io.umland.umlandback.domain.Fase;
import io.umland.umlandback.controller.dto.DicaDTO;
import io.umland.umlandback.repository.DicaRepository;
import io.umland.umlandback.repository.FaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dicas")
class DicaController {

    private final DicaRepository dicaRepository;
    private final FaseRepository faseRepository;

    public DicaController(DicaRepository dicaRepository, FaseRepository faseRepository) {
        this.dicaRepository = dicaRepository;
        this.faseRepository = faseRepository;
    }

    @GetMapping
    public List<DicaDTO> listar() {
        return dicaRepository.findAll().stream()
                .map(DicaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DicaDTO> buscar(@PathVariable Long id) {
        return dicaRepository.findById(id)
                .map(dica -> ResponseEntity.ok(DicaDTO.fromEntity(dica)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DicaDTO> criar(@RequestBody DicaDTO dto) {
        return faseRepository.findById(dto.getFaseId())
                .map(fase -> {
                    Dica dica = new Dica();
                    dica.setTexto(dto.getTexto());
                    dica.setFase(fase);
                    Dica salvo = dicaRepository.save(dica);
                    return ResponseEntity.ok(DicaDTO.fromEntity(salvo));
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<DicaDTO> atualizar(@PathVariable Long id, @RequestBody DicaDTO dto) {
        return dicaRepository.findById(id).map(dica -> {
            dica.setTexto(dto.getTexto());

            if (dto.getFaseId() != null) {
                Fase fase = faseRepository.findById(dto.getFaseId())
                        .orElseThrow(() -> new IllegalArgumentException("Fase não encontrada"));
                dica.setFase(fase);
            }

            Dica atualizado = dicaRepository.save(dica);
            return ResponseEntity.ok(DicaDTO.fromEntity(atualizado));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return dicaRepository.findById(id).map(d -> {
            dicaRepository.delete(d);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Se quiser expor as 3 dicas aleatórias como DTOs
//    @GetMapping("/r")
//    public List<DicaDTO> tresAleatorias() {
//        List<Dica> todas = dicaRepository.findAll();
//        Collections.shuffle(todas);
//        return todas.stream()
//                .limit(3)
//                .map(DicaDTO::fromEntity)
//                .collect(Collectors.toList());
//    }
}
