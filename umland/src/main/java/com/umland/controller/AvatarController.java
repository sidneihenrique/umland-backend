package com.umland.controller;

import com.umland.entities.Avatar;
import com.umland.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;
    
    private String basePath = "src/main/resources/static/uploads/avatars";

    @GetMapping
    public List<Avatar> getAllAvatars() {
        return avatarService.findAll();
    }

    @GetMapping("/{id}")
    public Avatar getAvatarById(@PathVariable Integer id) {
        return avatarService.findById(id);
    }

    @PostMapping
    public Avatar createAvatar(
            @RequestPart("avatar") Avatar avatar,
            @RequestPart("image") MultipartFile imageFile
    ) throws IOException {
        if (!imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(basePath, fileName);
            File parentDir = dest.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            imageFile.transferTo(dest);
            avatar.setFilePath(fileName); // O campo deve existir na entidade Avatar
        }
        return avatarService.save(avatar);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public Avatar updateAvatar(
        @PathVariable Integer id,
        @RequestPart("avatar") Avatar avatar,
        @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        avatar.setId(id);
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(basePath, fileName);
            File parentDir = dest.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            imageFile.transferTo(dest);
            avatar.setFilePath(fileName); // O campo deve existir na entidade Avatar
        }
        return avatarService.save(avatar);
    }

    @DeleteMapping("/{id}")
    public void deleteAvatar(@PathVariable Integer id) {
        avatarService.delete(id);
    }
}
