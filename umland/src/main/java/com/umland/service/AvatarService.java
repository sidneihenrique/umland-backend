package com.umland.service;

import com.umland.dao.AvatarDao;
import com.umland.entities.Avatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvatarService {

    @Autowired
    private AvatarDao avatarDao;

    public List<Avatar> findAll() {
        return avatarDao.findAll();
    }

    public Avatar save(Avatar avatar) {
        return avatarDao.save(avatar);
    }

    public Avatar update(Integer id, Avatar avatar) {
        avatar.setId(id);
        return avatarDao.save(avatar);
    }

    public void delete(Integer id) {
        avatarDao.deleteById(id);
    }

    public Avatar findById(Integer id) {
        return avatarDao.findById(id).orElse(null);
    }
}