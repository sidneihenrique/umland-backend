package com.umland.dto;

import java.util.List;

import com.umland.entities.enums.PhaseType;
import com.umland.entities.enums.PhaseMode;
import com.umland.entities.Character;
import com.umland.entities.GameMap;
import com.umland.entities.enums.PhaseNodeType;



public class PhaseDTO {
    private Integer id;
    private String title;
    private String description;
    private PhaseType type;
    private PhaseMode mode;
    private int maxTime;
    private Character character;
    private GameMap gameMap;
    private String diagramInitial;
    private List<String> correctDiagrams;
    private List<String> characterDialogues;
    private PhaseNodeType nodeType;
    private List<Integer> outgoingTransitionIds;
    private List<Integer> incomingTransitionIds;
    
    // Getters e setters
    
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getDiagramInitial() {
		return diagramInitial;
	}
	public void setDiagramInitial(String diagramInitial) {
		this.diagramInitial = diagramInitial;
	}
	public List<String> getCorrectDiagrams() {
		return correctDiagrams;
	}
	public void setCorrectDiagrams(List<String> correctDiagrams) {
		this.correctDiagrams = correctDiagrams;
	}
	public List<String> getCharacterDialogues() {
		return characterDialogues;
	}
	public void setCharacterDialogues(List<String> characterDialogues) {
		this.characterDialogues = characterDialogues;
	}
	public PhaseNodeType getNodeType() {
		return nodeType;
	}
	public void setNodeType(PhaseNodeType nodeType) {
		this.nodeType = nodeType;
	}
	public List<Integer> getOutgoingTransitionIds() {
		return outgoingTransitionIds;
	}
	public void setOutgoingTransitionIds(List<Integer> outgoingTransitionIds) {
		this.outgoingTransitionIds = outgoingTransitionIds;
	}
	public List<Integer> getIncomingTransitionIds() {
		return incomingTransitionIds;
	}
	public void setIncomingTransitionIds(List<Integer> incomingTransitionIds) {
		this.incomingTransitionIds = incomingTransitionIds;
	}

    // Getters e setters
    
}
