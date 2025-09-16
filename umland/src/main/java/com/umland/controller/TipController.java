package com.umland.controller;

import com.umland.entities.Tip;
import com.umland.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tips")
public class TipController {

    @Autowired
    private TipService tipService;

    @GetMapping
    public List<Tip> getAllTips() {
        return tipService.findAll();
    }

    @GetMapping("/{id}")
    public Tip getTipById(@PathVariable Integer id) {
        return tipService.findById(id);
    }

    @PostMapping
    public Tip createTip(@RequestBody Tip tip) {
        return tipService.save(tip);
    }

    @DeleteMapping("/{id}")
    public void deleteTip(@PathVariable Integer id) {
        tipService.delete(id);
    }
}
