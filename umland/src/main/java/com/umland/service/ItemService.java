// ItemService.java
package com.umland.service;

import com.umland.dao.ItemDao;
import com.umland.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    public Item findById(Integer id) {
        return itemDao.findById(id).orElse(null);
    }

    public List<Item> findAll() {
        return itemDao.findAll();
    }

    public Item save(Item item) {
        return itemDao.save(item);
    }
    
    public void delete(Integer id) {
        itemDao.deleteById(id);
    }
}
