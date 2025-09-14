package com.umland.entities;

import jakarta.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "users") // evita conflito com palavra reservada
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String password;

    private int reputation;
    private int coins;

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<GameMap> gameMaps;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public ArrayList<GameMap> getGameMaps() {
		return gameMaps;
	}

	public void setGameMaps(ArrayList<GameMap> gameMaps) {
		this.gameMaps = gameMaps;
	}

    // getters e setters
    
    
}