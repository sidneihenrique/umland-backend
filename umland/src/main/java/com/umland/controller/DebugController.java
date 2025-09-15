package com.umland.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Value("${app.files.path}")
    private String basePath;

    @Value("${app.files.avatars.path}")
    private String avatarsPath;

    @GetMapping("/paths")
    public Map<String, String> getPaths() {
        Map<String, String> paths = new HashMap<>();
        paths.put("basePath", basePath);
        paths.put("avatarsPath", avatarsPath);
        paths.put("basePathAbsolute", new File(basePath).getAbsolutePath());
        paths.put("avatarsPathAbsolute", new File(avatarsPath).getAbsolutePath());
        return paths;
    }
}