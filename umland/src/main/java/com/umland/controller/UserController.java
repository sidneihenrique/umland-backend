package com.umland.controller;

import com.umland.entities.User;
import com.umland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.umland.service.AvatarService;
import com.umland.entities.Avatar;
import com.umland.service.GameMapService;
import com.umland.entities.GameMap;
import com.umland.service.InventoryService;
import com.umland.entities.Inventory;
import com.umland.service.PhaseUserService;
import com.umland.entities.PhaseUser;
import com.umland.entities.Phase;
import com.umland.entities.enums.PhaseStatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AvatarService avatarService;
    
    @Autowired
    private GameMapService gameMapService;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private PhaseUserService phaseUserService;


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
    	
    	// Verifica se o email já está em uso
        if (userService.findByEmail(email) != null) {
            throw new RuntimeException("Email já está em uso: " + email);
        }
    	
        Avatar avatar = avatarService.findById(idAvatar);
        if (avatar == null) {
            throw new RuntimeException("Avatar não encontrado com id: " + idAvatar);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatar(avatar);
        user.setReputation(100); // valor inicial de reputation
        user.setCoins(100); // valor inicial de coins
        
        
        // Cria e associa o inventário
        Inventory inventory = new Inventory();
        inventory.setUser(user);
        user.setInventory(inventory);
        
        // Salva o usuário
        User savedUser = userService.save(user);
        
        // Salva o inventário
        inventoryService.save(inventory);


        return savedUser;
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
    
    // endpoint para resetar dados do jogo do usuário
    @PostMapping("/{id}/reset-game")
    public User resetGameData(@PathVariable Long id) {
        try {
            return userService.resetGameData(id);
        } catch (ResponseStatusException ex) {
            // propaga 404 corretamente
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao resetar dados do usuário", ex);
        }
    }
}
