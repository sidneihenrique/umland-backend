package com.umland.service;


import com.umland.dao.UserDao;
import com.umland.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    
    // injetar PhaseUserService para remoção das PhaseUser do usuário
    @Autowired
    private PhaseUserService phaseUserService;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    public User save(User user) {
        return userDao.save(user);
    }

    public User update(Long id, User user) {
        user.setId(id.intValue());
        return userDao.save(user);
    }

    public void delete(Long id) {
        userDao.deleteById(id);
    }

    public List<User> findByEmailAndPassword(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(List::of)
                .orElse(List.of());
    }
    
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElse(null);
    }
    
    /**
     * Reseta os dados de jogo do usuário:
     * - deleta todas as PhaseUser relacionadas ao usuário
     * - coins = 0
     * - reputation = 50
     *
     * Operação transacional para consistência.
     */
    @Transactional
    public User resetGameData(Long id) {
        User user = userDao.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + id);
        }

        // 1) deletar phaseUsers associados (fazemos antes para evitar FK problems)
        // Implementação assume PhaseUserService exponha deleteByUserId(Long)
        phaseUserService.deleteByUserId(id);

        // 2) atualizar campos do usuário
        user.setCoins(0);
        user.setReputation(50);

        // 3) persistir e retornar o usuário atualizado
        return userDao.save(user);
    }
}

