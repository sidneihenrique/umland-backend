package com.umland.controller;

import com.umland.entities.Character;
import com.umland.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @Value("${app.files.characters.path}")
    private String characterImagePath;

    @GetMapping
    public List<Character> getAllCharacters() {
        return characterService.findAll();
    }

    @GetMapping("/{id}")
    public Character getCharacterById(@PathVariable Integer id) {
        return characterService.findById(id);
    }

    @PostMapping
    public Character createCharacter(
            @RequestPart("character") Character character,
            @RequestPart("image") MultipartFile imageFile
    ) throws IOException {
        if (!imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(characterImagePath, fileName);
            imageFile.transferTo(dest);
            character.setFilePath(fileName);
        }
        return characterService.save(character);
    }

    @PutMapping("/{id}")
    public Character updateCharacter(@PathVariable Integer id,
                                     @RequestPart("character") Character character,
                                     @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
        character.setId(id);
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(characterImagePath + fileName);
            imageFile.transferTo(dest);
            character.setFilePath(fileName);
        }
        return characterService.save(character);
    }

    @DeleteMapping("/{id}")
    public void deleteCharacter(@PathVariable Integer id) {
        characterService.delete(id);
    }
}
