package com.umland.controller;

import com.umland.entities.Inventory;
import com.umland.entities.Item;
import com.umland.service.InventoryService;
import com.umland.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/user/{userId}/items")
    public Inventory addItemToInventoryByUser(
        @PathVariable Integer userId,
        @RequestParam Integer itemId
    ) {
        Inventory inventory = inventoryService.findByUserId(userId);
        if (inventory == null) {
            throw new RuntimeException("Inventário não encontrado para o usuário: " + userId);
        }

        Item item = itemService.findById(itemId);
        if (item == null) {
            throw new RuntimeException("Item não encontrado: " + itemId);
        }

        inventory.getItems().add(item);
        return inventoryService.save(inventory);
    }
    
    // Listar todos os itens do inventário de um usuário
    @GetMapping("/user/{userId}/items")
    public List<Item> listItemsByUser(@PathVariable Integer userId) {
        Inventory inventory = inventoryService.findByUserId(userId);
        if (inventory == null) {
            throw new RuntimeException("Inventário não encontrado para o usuário: " + userId);
        }
        return inventory.getItems();
    }
    
    // Utilizar (remover) um item específico do inventário de um usuário
    @PostMapping("/user/{userId}/items/{itemId}/use")
    public Inventory useItemFromInventory(
        @PathVariable Integer userId,
        @PathVariable Integer itemId
    ) {
        Inventory inventory = inventoryService.findByUserId(userId);
        if (inventory == null) {
            throw new RuntimeException("Inventário não encontrado para o usuário: " + userId);
        }

        Item item = itemService.findById(itemId);
        if (item == null) {
            throw new RuntimeException("Item não encontrado: " + itemId);
        }

        boolean removed = inventory.getItems().remove(item);
        if (!removed) {
            throw new RuntimeException("Item não está no inventário do usuário.");
        }

        return inventoryService.save(inventory);
    }
}
