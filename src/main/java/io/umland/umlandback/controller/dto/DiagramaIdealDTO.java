package io.umland.umlandback.controller.dto;

public class DiagramaIdealDTO {
    private Long id;
    private String representacao;
    private Long faseId;

    public DiagramaIdealDTO() {}

    public DiagramaIdealDTO(Long id, String representacao, Long faseId) {
        this.id = id;
        this.representacao = representacao;
        this.faseId = faseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRepresentacao() {
        return representacao;
    }

    public void setRepresentacao(String representacao) {
        this.representacao = representacao;
    }

    public Long getFaseId() {
        return faseId;
    }

    public void setFaseId(Long faseId) {
        this.faseId = faseId;
    }
}

