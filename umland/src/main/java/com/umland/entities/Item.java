package com.umland.entities;

import jakarta.persistence.*;
import java.util.ArrayList;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private int price;
    private String filePath; // Caminho da imagem associada ao item

	@ManyToMany(mappedBy = "items")
    private ArrayList<Inventory> inventories;
    

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ArrayList<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(ArrayList<Inventory> inventories) {
		this.inventories = inventories;
	}
	
    public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

    
}