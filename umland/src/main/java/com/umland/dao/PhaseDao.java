package com.umland.dao;

import com.umland.entities.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseDao extends JpaRepository<Phase, Integer> {
    // Métodos customizados podem ser adicionados aqui
}

