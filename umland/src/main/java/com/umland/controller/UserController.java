package com.umland.controller;

import com.umland.entities.User;
import com.umland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    
    @Autowired
    private Environment env;

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
        
        // Associa o GameMap de id 1
        GameMap gameMap = gameMapService.findById(1);
        if (gameMap == null) {
            throw new RuntimeException("GameMap não encontrado com id: 1");
        }
        user.getGameMaps().add(gameMap);
        
        // Cria e associa o inventário
        Inventory inventory = new Inventory();
        inventory.setUser(user);
        user.setInventory(inventory);
        
        // Salva o usuário
        User savedUser = userService.save(user);

        int countPhases = 0; 
        // Cria PhaseUser para cada Phase do GameMap
        for (Phase phase : gameMap.getPhases()) {
            PhaseUser phaseUser = new PhaseUser();
            phaseUser.setUser(savedUser);
            phaseUser.setPhase(phase);
            if (countPhases == 0) {
				phaseUser.setStatus(PhaseStatus.AVAILABLE); // primeira fase disponível
				phaseUser.setCurrent(true);
			} else {
				phaseUser.setStatus(PhaseStatus.LOCKED); // fases subsequentes bloqueadas
				phaseUser.setCurrent(false);
			}
            phaseUser.setReputation(0);
            phaseUser.setCoins(0);
            phaseUserService.save(phaseUser);
            countPhases++;
        }

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
}
