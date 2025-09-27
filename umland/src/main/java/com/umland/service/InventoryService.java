package com.umland.service;

import com.umland.dao.InventoryDao;
import com.umland.entities.Inventory;
import com.umland.entities.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    public Inventory save(Inventory inventory) {
        return inventoryDao.save(inventory);
    }
    
    public Inventory findById(Integer id) {
		return inventoryDao.findById(id).orElse(null);
	}
    
    public Inventory findByUserId(Integer userId) {
        return inventoryDao.findByUserId(userId);
    }
    
    // Lógica para adicionar item ao inventário
    public Inventory addItemToInventory(Integer userId, String itemName) {
        Inventory inventory = findByUserId(userId);
        if (inventory == null) {
            throw new RuntimeException("Inventário não encontrado para o usuário: " + userId);
        }

        InventoryItem found = null;
        for (InventoryItem item : inventory.getItems()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                found = item;
                break;
            }
        }

        if (found != null) {
            found.setQuantity(found.getQuantity() + 1);
        } else {
            InventoryItem newItem = new InventoryItem();
            newItem.setInventory(inventory);
            newItem.setItemName(itemName);
            newItem.setQuantity(1);
            inventory.getItems().add(newItem);
        }

        return save(inventory);
    }
}
