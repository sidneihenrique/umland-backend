package io.umland.umlandback.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "dicas")
public class Dica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(nullable = false)
    private String texto;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fase_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_dica_fase"))
    @Lob
    private Fase fase;


    public Dica() {}


    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Fase getFase() { return fase; }
    public void setFase(Fase fase) { this.fase = fase; }
}