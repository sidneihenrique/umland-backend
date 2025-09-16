package com.umland.service;

import com.umland.dao.PhaseUserDao;
import com.umland.entities.PhaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseUserService {

    @Autowired
    private PhaseUserDao phaseUserDao;

    public List<PhaseUser> findAll() {
        return phaseUserDao.findAll();
    }

    public PhaseUser findById(Integer id) {
        return phaseUserDao.findById(id).orElse(null);
    }

    public PhaseUser save(PhaseUser phaseUser) {
        return phaseUserDao.save(phaseUser);
    }

    public void delete(Integer id) {
        phaseUserDao.deleteById(id);
    }
}
