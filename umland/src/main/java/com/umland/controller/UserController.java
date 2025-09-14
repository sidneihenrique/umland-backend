package com.umland.controller;

import com.umland.entities.User;
import com.umland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.umland.service.AvatarService;
import com.umland.entities.Avatar;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AvatarService avatarService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public User createUser(
        @RequestParam String name,
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam Integer idAvatar
    ) {
        Avatar avatar = avatarService.findById(idAvatar);
        if (avatar == null) {
            throw new RuntimeException("Avatar não encontrado com id: " + idAvatar);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatar(avatar);

        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
    
    @PostMapping("/login")
    public List<User> login(@RequestParam String email, @RequestParam String password) {
        return userService.findByEmailAndPassword(email, password);
    }
}
