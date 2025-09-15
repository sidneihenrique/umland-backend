package com.umland.dao;

import com.umland.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterDao extends JpaRepository<Character, Integer> {
    // Métodos customizados podem ser adicionados aqui
}