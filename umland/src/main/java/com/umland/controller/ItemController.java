package com.umland.controller;

import com.umland.entities.Item;
import com.umland.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;


import java.util.List;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    
    @Value("${app.files.items.path}")
    private String basePath;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Integer id) {
        return itemService.findById(id);
    }

    @PostMapping
    public Item createItem(
            @RequestPart("item") Item item,
            @RequestPart("image") MultipartFile imageFile
    ) throws IOException {
        if (!imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(basePath, fileName);
            imageFile.transferTo(dest);
            item.setFilePath(fileName); // O campo deve existir na entidade Item
        }
        return itemService.save(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Integer id, @RequestPart("item") Item item, @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
        item.setId(id);
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(basePath + fileName);
            imageFile.transferTo(dest);
            item.setFilePath(fileName);
        }
        return itemService.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        itemService.delete(id);
    }
}

