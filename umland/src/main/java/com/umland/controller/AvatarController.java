package com.umland.controller;

import com.umland.entities.Avatar;
import com.umland.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @GetMapping
    public List<Avatar> getAllAvatars() {
        return avatarService.findAll();
    }

    @GetMapping("/{id}")
    public Avatar getAvatarById(@PathVariable Integer id) {
        return avatarService.findById(id);
    }

    @PostMapping
    public Avatar createAvatar(@RequestBody Avatar avatar) {
        return avatarService.save(avatar);
    }

    @PutMapping("/{id}")
    public Avatar updateAvatar(@PathVariable Integer id, @RequestBody Avatar avatar) {
        avatar.setId(id);
        return avatarService.save(avatar);
    }

    @DeleteMapping("/{id}")
    public void deleteAvatar(@PathVariable Integer id) {
        avatarService.delete(id);
    }
}
