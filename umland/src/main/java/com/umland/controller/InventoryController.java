package com.umland.controller;

import com.umland.entities.Inventory;
import com.umland.entities.Item;
import com.umland.service.InventoryService;
import com.umland.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/{inventoryId}/items")
    public Inventory addItemToInventory(
        @PathVariable Integer inventoryId,
        @RequestParam Integer itemId
    ) {
        Inventory inventory = inventoryService.findById(inventoryId);
        if (inventory == null) {
            throw new RuntimeException("Inventário não encontrado: " + inventoryId);
        }

        Item item = itemService.findById(itemId);
        if (item == null) {
            throw new RuntimeException("Item não encontrado: " + itemId);
        }

        inventory.getItems().add(item);
        return inventoryService.save(inventory);
    }
}
