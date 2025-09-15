package com.umland.service;

import com.umland.dao.PhaseDao;
import com.umland.entities.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseService {

    @Autowired
    private PhaseDao phaseDao;

    public List<Phase> findAll() {
        return phaseDao.findAll();
    }

    public Phase findById(Integer id) {
        return phaseDao.findById(id).orElse(null);
    }

    public Phase save(Phase phase) {
        return phaseDao.save(phase);
    }

    public Phase update(Integer id, Phase phase) {
        phase.setId(id);
        return phaseDao.save(phase);
    }

    public void delete(Integer id) {
        phaseDao.deleteById(id);
    }
}
