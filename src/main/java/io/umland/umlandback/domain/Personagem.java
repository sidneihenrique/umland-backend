package io.umland.umlandback.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "personagens")
public class Personagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 120)
    private String nome;


    @Lob
    @Column(nullable = false)
    private String fala;


    @Column(name = "imagem_url", length = 500)
    private String imagem; // URL


    public Personagem() {}


    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getFala() { return fala; }
    public void setFala(String fala) { this.fala = fala; }
    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
}