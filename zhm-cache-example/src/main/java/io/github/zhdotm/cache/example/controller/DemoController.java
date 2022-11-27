package io.github.zhdotm.cache.example.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.zhdotm.cache.core.annotation.MultiCachePut;
import io.github.zhdotm.cache.core.annotation.MultiCacheable;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhihao.mao
 */

@RestController
public class DemoController {

    @MultiCacheable(cacheNames = "testMethod", key = "#id", sync = true)
    @GetMapping("test1")
    @SneakyThrows
    public JSONObject testMethod(@RequestParam(value = "id") String id) {
        Thread.sleep(10 * 1000);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "code1");
        jsonObject.put("message", "xxxxxlll");

        return jsonObject;
    }

    @MultiCachePut(cacheNames = "testMethod", key = "#id")
    @GetMapping("test2")
    @SneakyThrows
    public JSONObject testMethod01(@RequestParam(value = "id") String id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "code2");
        jsonObject.put("message", "zzzzooooo");

        return jsonObject;
    }

}
