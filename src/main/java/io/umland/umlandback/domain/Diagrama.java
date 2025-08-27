package io.umland.umlandback.domain;


import jakarta.persistence.*;
import java.time.*;


@Entity
@Table(name = "diagramas")
public class Diagrama {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Conteúdo do diagrama do usuário (JSON/texto)
    @Lob
    @Column(nullable = false)
    private String representacao;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiagramStatus status = DiagramStatus.PENDENTE;


    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();


    public Diagrama() {}


    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRepresentacao() { return representacao; }
    public void setRepresentacao(String representacao) { this.representacao = representacao; }
    public DiagramStatus getStatus() { return status; }
    public void setStatus(DiagramStatus status) { this.status = status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}