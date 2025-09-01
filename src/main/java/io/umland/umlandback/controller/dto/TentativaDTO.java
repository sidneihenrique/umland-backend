package io.umland.umlandback.controller.dto;

import java.time.LocalDateTime;

public class TentativaDTO {
    private Long id;
    private Long usuarioId;
    private Long faseId;
    private Long diagramaId;
    private Double resultado;
    private Long moedasGanhas;
    private Long reputacaoGanha;
    private LocalDateTime criadoEm;

    public TentativaDTO() {}

    public TentativaDTO(Long id, Long usuarioId, Long faseId, Long diagramaId,
                        Double resultado, Long moedasGanhas, Long reputacaoGanha,
                        LocalDateTime criadoEm) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.faseId = faseId;
        this.diagramaId = diagramaId;
        this.resultado = resultado;
        this.moedasGanhas = moedasGanhas;
        this.reputacaoGanha = reputacaoGanha;
        this.criadoEm = criadoEm;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getFaseId() { return faseId; }
    public void setFaseId(Long faseId) { this.faseId = faseId; }

    public Long getDiagramaId() { return diagramaId; }
    public void setDiagramaId(Long diagramaId) { this.diagramaId = diagramaId; }

    public Double getResultado() { return resultado; }
    public void setResultado(Double resultado) { this.resultado = resultado; }

    public Long getMoedasGanhas() { return moedasGanhas; }
    public void setMoedasGanhas(Long moedasGanhas) { this.moedasGanhas = moedasGanhas; }

    public Long getReputacaoGanha() { return reputacaoGanha; }
    public void setReputacaoGanha(Long reputacaoGanha) { this.reputacaoGanha = reputacaoGanha; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}