package io.umland.umlandback.controller.dto;

import io.umland.umlandback.domain.Dica;
import io.umland.umlandback.domain.Fase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor   // ✅ cria construtor vazio
@AllArgsConstructor  // cria construtor completo
public class FaseDTO {
    private Long id;
    private String titulo;
    private Long pontuacaoReputacao;
    private Long moedas;
    private String personagemNome;
    private List<String> dicas;

    public FaseDTO(Fase fase) {
        this.id = fase.getId();
        this.titulo = fase.getTitulo();
        this.pontuacaoReputacao = fase.getPontuacaoReputacao();
        this.moedas = fase.getMoedas();
        this.personagemNome = fase.getPersonagem() != null ? fase.getPersonagem().getNome() : null;
        this.dicas = fase.getDicas() != null
                ? fase.getDicas().stream().map(Dica::getTexto).toList()
                : List.of();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getPontuacaoReputacao() {
        return pontuacaoReputacao;
    }

    public void setPontuacaoReputacao(Long pontuacaoReputacao) {
        this.pontuacaoReputacao = pontuacaoReputacao;
    }

    public Long getMoedas() {
        return moedas;
    }

    public void setMoedas(Long moedas) {
        this.moedas = moedas;
    }

    public String getPersonagemNome() {
        return personagemNome;
    }

    public void setPersonagemNome(String personagemNome) {
        this.personagemNome = personagemNome;
    }

    public List<String> getDicas() {
        return dicas;
    }

    public void setDicas(List<String> dicas) {
        this.dicas = dicas;
    }
}

