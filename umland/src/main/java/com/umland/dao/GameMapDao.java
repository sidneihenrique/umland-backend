package com.umland.dao;

import com.umland.entities.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMapDao extends JpaRepository<GameMap, Integer> {
	// Métodos customizados podem ser adicionados aqui
}
