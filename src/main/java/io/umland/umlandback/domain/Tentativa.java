package io.umland.umlandback.domain;

import jakarta.persistence.*;
import java.time.*;


@Entity
@Table(name = "tentativas")
public class Tentativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tentativa_usuario"))
    private Usuario usuario;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fase_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tentativa_fase"))
    private Fase fase;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diagrama_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tentativa_diagrama"))
    private Diagrama diagrama;


    @Column(nullable = false)
    private Double resultado; // ex.: percentual de similaridade (0..100)


    @Column(name = "moedas_ganhas", nullable = false)
    private Long moedasGanhas = 0L;


    @Column(name = "reputacao_ganha", nullable = false)
    private Long reputacaoGanha = 0L;


    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();


    public Tentativa() {}


    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Fase getFase() { return fase; }
    public void setFase(Fase fase) { this.fase = fase; }
    public Diagrama getDiagrama() { return diagrama; }
    public void setDiagrama(Diagrama diagrama) { this.diagrama = diagrama; }
    public Double getResultado() { return resultado; }
    public void setResultado(Double resultado) { this.resultado = resultado; }
    public Long getMoedasGanhas() { return moedasGanhas; }
    public void setMoedasGanhas(Long moedasGanhas) { this.moedasGanhas = moedasGanhas; }
    public Long getReputacaoGanha() { return reputacaoGanha; }
    public void setReputacaoGanha(Long reputacaoGanha) { this.reputacaoGanha = reputacaoGanha; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
