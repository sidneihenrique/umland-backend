package com.umland.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    @JsonBackReference
    private Inventory inventory;

    @Column(name = "item_name", nullable = false)
    private String itemName;

	@Column(name = "quantity", nullable = false)
    private Integer quantity;

    // getters e setters
	
    public Inventory getInventory() {
        return inventory;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
