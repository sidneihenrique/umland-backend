package com.umland.service;


import com.umland.dao.UserDao;
import com.umland.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

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
}

