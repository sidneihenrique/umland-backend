package com.umland.controller;

import com.umland.entities.Inventory;
import com.umland.entities.InventoryItem;
import com.umland.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    // Adicionar item ao inventário
    @PostMapping("/user/{userId}/items")
    public Inventory addItemToInventoryByUser(
        @PathVariable Integer userId,
        @RequestParam String itemName
    ) {
        return inventoryService.addItemToInventory(userId, itemName);
    }
    
    // Listar todos os itens do inventário de um usuário
    @GetMapping("/user/{userId}/items")
    public List<InventoryItem> listItemsByUser(@PathVariable Integer userId) {
        Inventory inventory = inventoryService.findByUserId(userId);
        if (inventory == null) {
            throw new RuntimeException("Inventário não encontrado para o usuário: " + userId);
        }
        return inventory.getItems();
    }
    
    // Utilizar (remover) um item específico do inventário de um usuário pelo nome
    @PostMapping("/user/{userId}/items/{itemName}/use")
    public Inventory useItemFromInventory(
        @PathVariable Integer userId,
        @PathVariable String itemName
    ) {
        Inventory inventory = inventoryService.findByUserId(userId);
        if (inventory == null) {
            throw new RuntimeException("Inventário não encontrado para o usuário: " + userId);
        }

        InventoryItem toRemove = null;
        for (InventoryItem item : inventory.getItems()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                toRemove = item;
                break;
            }
        }
        if (toRemove == null) {
            throw new RuntimeException("Item não está no inventário do usuário.");
        }

        if (toRemove.getQuantity() > 1) {
            toRemove.setQuantity(toRemove.getQuantity() - 1);
        } else {
            inventory.getItems().remove(toRemove);
        }

        return inventoryService.save(inventory);
    }
}
