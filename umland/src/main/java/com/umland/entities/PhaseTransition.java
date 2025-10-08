package com.umland.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class PhaseTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String optionText; // Texto da escolha exibida ao jogador

    @ManyToOne
    @JoinColumn(name = "from_phase_id", nullable = true)
    private Phase fromPhase;

    @ManyToOne
    @JoinColumn(name = "to_phase_id", nullable = true)
    private Phase toPhase;
    
    // Getters e setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public Phase getFromPhase() {
		return fromPhase;
	}

	public void setFromPhase(Phase fromPhase) {
		this.fromPhase = fromPhase;
	}

	public Phase getToPhase() {
		return toPhase;
	}

	public void setToPhase(Phase toPhase) {
		this.toPhase = toPhase;
	}
    
    
}