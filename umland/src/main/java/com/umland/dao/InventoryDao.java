package com.umland.dao;

import com.umland.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryDao extends JpaRepository<Inventory, Integer> {
	Inventory findByUserId(Integer userId);
}
