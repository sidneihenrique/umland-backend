package com.umland.entities;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;

@Entity
public class GameMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String title;

    @ManyToMany(mappedBy = "gameMaps")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "gameMap", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("gameMap")
    private List<Phase> phases = new ArrayList<>();
    
    public GameMap() {
        this.phases = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    
    // getters e setters
	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public void setPhases(ArrayList<Phase> phases) {
		this.phases = phases;
	}
    
    
}