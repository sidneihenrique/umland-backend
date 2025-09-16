package com.umland.dao;

import com.umland.entities.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipDao extends JpaRepository<Tip, Integer> {
}
