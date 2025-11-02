package com.umland.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.annotations.Type;

import com.umland.entities.enums.*;
import com.vladmihalcea.hibernate.type.json.JsonType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    
    private String description;

    @Enumerated(EnumType.STRING)
    private PhaseType type;

    @Enumerated(EnumType.STRING)
    private PhaseMode mode;

    private int maxTime;


    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @ManyToOne
    @JoinColumn(name = "gamemap_id")
    @JsonIgnoreProperties("phases")
    private GameMap gameMap;
    
    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhaseUser> phaseUsers;
    
    @Type(JsonType.class)
    @Column(columnDefinition = "json", nullable = true) 
    private String diagramInitial;

    @ElementCollection
    @CollectionTable(name = "phase_correct_diagrams", joinColumns = @JoinColumn(name = "phase_id"))
    @Column(name = "diagram_json", columnDefinition = "TEXT")
    private List<String> correctDiagrams;
    
    @ElementCollection
    @OrderColumn(name = "dialogue_order")
    private List<String> characterDialogues;
    
    
    @Enumerated(EnumType.STRING)
    private PhaseNodeType nodeType;
    
    @Enumerated(EnumType.STRING)
    private DiagramType diagramType;
    
    @OneToMany(mappedBy = "fromPhase", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonBackReference("phase-from")
    private List<PhaseTransition> outgoingTransitions = new ArrayList<>();

    @OneToMany(mappedBy = "toPhase", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonBackReference("phase-to")
    private List<PhaseTransition> incomingTransitions = new ArrayList<>();
    
    @OneToMany(mappedBy = "fromPhase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhaseTransition> phaseTransitions;
    
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

	public String getDiagramInitial() {
		return diagramInitial;
	}

	public void setDiagramInitial(String diagramInitial) {
		this.diagramInitial = diagramInitial;
	}

	public PhaseNodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(PhaseNodeType nodeType) {
		this.nodeType = nodeType;
	}

	public List<PhaseTransition> getOutgoingTransitions() {
		return outgoingTransitions;
	}

	public void setOutgoingTransitions(List<PhaseTransition> outgoingTransitions) {
		this.outgoingTransitions = outgoingTransitions;
	}

	public List<PhaseTransition> getIncomingTransitions() {
		return incomingTransitions;
	}

	public void setIncomingTransitions(List<PhaseTransition> incomingTransitions) {
		this.incomingTransitions = incomingTransitions;
	}


	
	
	
	
	
    
}