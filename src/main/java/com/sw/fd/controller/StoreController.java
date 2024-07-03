package com.sw.fd.controller;
import com.sw.fd.entity.Store;
import com.sw.fd.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/stores")
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    @PostMapping("/stores")
    public void createStore(@RequestBody Store store) {
        storeService.saveStore(store);
    }
}