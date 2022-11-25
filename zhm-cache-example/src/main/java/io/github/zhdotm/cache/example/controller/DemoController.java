package io.github.zhdotm.cache.example.controller;

import io.github.zhdotm.cache.core.annotation.MultiCacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhihao.mao
 */

@RestController
public class DemoController {

    @MultiCacheable(cacheNames = "testMethod", key = "#id")
    @GetMapping("test")
    public String testMethod(@RequestParam(value = "id") String id) {

        return "xxxlllxxxlll";
    }
}
