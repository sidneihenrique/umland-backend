package com.umland.service;

import com.umland.dao.TipDao;
import com.umland.entities.Tip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipService {

    @Autowired
    private TipDao tipDao;

    public List<Tip> findAll() {
        return tipDao.findAll();
    }

    public Tip findById(Integer id) {
        return tipDao.findById(id).orElse(null);
    }

    public Tip save(Tip tip) {
        return tipDao.save(tip);
    }

    public void delete(Integer id) {
        tipDao.deleteById(id);
    }
}
