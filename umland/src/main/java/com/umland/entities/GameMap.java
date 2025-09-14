package com.umland.entities;

import jakarta.persistence.*;
import java.util.ArrayList;

@Entity
public class GameMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "gameMap", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Phase> phases;
    
    // getters e setters
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Phase> getPhases() {
		return phases;
	}

	public void setPhases(ArrayList<Phase> phases) {
		this.phases = phases;
	}
    
}