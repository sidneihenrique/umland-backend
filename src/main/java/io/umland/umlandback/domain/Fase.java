package io.umland.umlandback.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "fases")
public class Fase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String titulo;

    // NPC que apresenta o problema
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personagem_id", foreignKey = @ForeignKey(name = "fk_fase_personagem"))
    private Personagem personagem;

    @Lob
    @Column(nullable = false)
    private String fala; // Fala do personagem nesta fase específica

    @Column(name = "pontuacao_reputacao", nullable = false)
    private Long pontuacaoReputacao = 0L;

    @Column(name = "moedas_recompensa", nullable = false)
    private Long moedas = 0L;

    // Um gabarito por fase (1:1). Mapeado do outro lado em DiagramaIdeal.fase
    @OneToOne(mappedBy = "fase", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private DiagramaIdeal diagramaIdeal;

    @OneToMany(mappedBy = "fase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dica> dicas = new ArrayList<>();

    public Fase() {}

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Personagem getPersonagem() { return personagem; }
    public void setPersonagem(Personagem personagem) { this.personagem = personagem; }
    public String getFala() { return fala; }
    public void setFala(String fala) { this.fala = fala; }
    public Long getPontuacaoReputacao() { return pontuacaoReputacao; }
    public void setPontuacaoReputacao(Long pontuacaoReputacao) { this.pontuacaoReputacao = pontuacaoReputacao; }
    public Long getMoedas() { return moedas; }
    public void setMoedas(Long moedas) { this.moedas = moedas; }
    public DiagramaIdeal getDiagramaIdeal() { return diagramaIdeal; }
    public void setDiagramaIdeal(DiagramaIdeal diagramaIdeal) { this.diagramaIdeal = diagramaIdeal; }
    public List<Dica> getDicas() { return dicas; }
    public void setDicas(List<Dica> dicas) { this.dicas = dicas; }
}