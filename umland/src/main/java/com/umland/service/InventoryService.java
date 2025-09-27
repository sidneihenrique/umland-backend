package com.umland.service;

import com.umland.dao.InventoryDao;
import com.umland.entities.Inventory;
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
}
