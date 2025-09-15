package com.umland.service;

import com.umland.dao.CharacterDao;
import com.umland.entities.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    @Autowired
    private CharacterDao characterDao;

    public List<Character> findAll() {
        return characterDao.findAll();
    }

    public Character findById(Integer id) {
        return characterDao.findById(id).orElse(null);
    }

    public Character save(Character character) {
        return characterDao.save(character);
    }

    public Character update(Integer id, Character character) {
        character.setId(id);
        return characterDao.save(character);
    }

    public void delete(Integer id) {
        characterDao.deleteById(id);
    }
}
