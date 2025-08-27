package io.umland.umlandback.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_email", columnNames = {"email"})
})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 120)
    private String nome;


    @Column(nullable = false, length = 160)
    private String email;


    @Column(nullable = false, length = 255)
    private String senha; // armazenar hash (ex.: BCrypt)


    @Column(nullable = false)
    private Long reputacao = 0L;


    @Column(nullable = false)
    private Long moedas = 0L;


    // fase atual do jogador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fase_atual_id", foreignKey = @ForeignKey(name = "fk_usuario_fase_atual"))
    private Fase faseAtual;


    public Usuario() {}


    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Long getReputacao() { return reputacao; }
    public void setReputacao(Long reputacao) { this.reputacao = reputacao; }
    public Long getMoedas() { return moedas; }
    public void setMoedas(Long moedas) { this.moedas = moedas; }
    public Fase getFaseAtual() { return faseAtual; }
    public void setFaseAtual(Fase faseAtual) { this.faseAtual = faseAtual; }
}