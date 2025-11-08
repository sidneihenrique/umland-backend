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

    @Transactional
    public User update(Long id, User incoming) {
        User existing = userDao.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + id));

        // Atualiza apenas os campos esperados (evita sobrescrever relacionamentos ausentes)
        if (incoming.getName() != null) existing.setName(incoming.getName());
        if (incoming.getEmail() != null) existing.setEmail(incoming.getEmail());
        if (incoming.getPassword() != null) existing.setPassword(incoming.getPassword());

        // Para numéricos/booleanos: se sua entidade usa wrapper types (Integer/Long/Boolean) é mais fácil detectar null.
        // Caso use primitivos (int/boolean), trate de forma apropriada (usar DTO é melhor).
        if (incoming.getCoins() != existing.getCoins()) existing.setCoins(incoming.getCoins());
        if (incoming.getReputation() != existing.getReputation()) existing.setReputation(incoming.getReputation());
        if (incoming.getAvatar() != null) existing.setAvatar(incoming.getAvatar());

        // NÃO tocar em existing.getInventory() a menos que incoming forneça algo intencionalmente:
        if (incoming.getInventory() != null) {
            existing.setInventory(incoming.getInventory());
        }

        return userDao.save(existing);
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

