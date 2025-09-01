package io.umland.umlandback.domain;


import jakarta.persistence.*;


@Entity
@Table(name = "diagramas_ideais")
public class DiagramaIdeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(nullable = false, columnDefinition = "json")
    private String representacao; // JSON/texto do gabarito


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fase_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_diagramaideal_fase"))
    private Fase fase;


    public DiagramaIdeal() {}


    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRepresentacao() { return representacao; }
    public void setRepresentacao(String representacao) { this.representacao = representacao; }
    public Fase getFase() { return fase; }
    public void setFase(Fase fase) { this.fase = fase; }
}