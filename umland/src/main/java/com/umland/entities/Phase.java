package com.umland.entities;

import jakarta.persistence.*;
import java.util.List;
import com.umland.entities.enums.*;

@Entity
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Enumerated(EnumType.STRING)
    private PhaseType type;

    @Enumerated(EnumType.STRING)
    private PhaseMode mode;

    private int maxTime;

    @Enumerated(EnumType.STRING)
    private PhaseStatus status;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @ManyToOne
    @JoinColumn(name = "gamemap_id")
    private GameMap gameMap;

    @ElementCollection
    private List<String> correctDiagrams;
    
    // getters e setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PhaseType getType() {
		return type;
	}

	public void setType(PhaseType type) {
		this.type = type;
	}

	public PhaseMode getMode() {
		return mode;
	}

	public void setMode(PhaseMode mode) {
		this.mode = mode;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public PhaseStatus getStatus() {
		return status;
	}

	public void setStatus(PhaseStatus status) {
		this.status = status;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public List<String> getCorrectDiagrams() {
		return correctDiagrams;
	}

	public void setCorrectDiagrams(List<String> correctDiagrams) {
		this.correctDiagrams = correctDiagrams;
	}
    
}