package io.umland.umlandback.controller.dto;

public class DicaDTO {
    private Long id;
    private String texto;
    private Long faseId;

    public DicaDTO() {}

    public DicaDTO(Long id, String texto, Long faseId) {
        this.id = id;
        this.texto = texto;
        this.faseId = faseId;
    }

    // Factory method para converter da entidade para DTO
    public static DicaDTO fromEntity(io.umland.umlandback.domain.Dica dica) {
        return new DicaDTO(
                dica.getId(),
                dica.getTexto(),
                dica.getFase() != null ? dica.getFase().getId() : null
        );
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public Long getFaseId() { return faseId; }
    public void setFaseId(Long faseId) { this.faseId = faseId; }
}
