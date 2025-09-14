package com.umland.dao;

import com.umland.entities.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarDao extends JpaRepository<Avatar, Integer> {
    // Métodos customizados podem ser adicionados aqui
}
