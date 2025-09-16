package com.umland.controller;

import com.umland.entities.PhaseUser;
import com.umland.service.PhaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phase-users")
public class PhaseUserController {

    @Autowired
    private PhaseUserService phaseUserService;

    @GetMapping
    public List<PhaseUser> getAll() {
        return phaseUserService.findAll();
    }

    @GetMapping("/{id}")
    public PhaseUser getById(@PathVariable Integer id) {
        return phaseUserService.findById(id);
    }

    @PostMapping
    public PhaseUser create(@RequestBody PhaseUser phaseUser) {
        return phaseUserService.save(phaseUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        phaseUserService.delete(id);
    }
}
