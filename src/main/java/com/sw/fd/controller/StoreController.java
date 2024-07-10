package com.sw.fd.controller;

import com.sw.fd.entity.Menu;
import com.sw.fd.entity.Store;
import com.sw.fd.service.MenuService;
import com.sw.fd.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/storeList")
    public String showStoreList(Model model) {
        List<Store> stores = storeService.getAllStores();
        model.addAttribute("stores", stores);
        return "storeList";
    }
    @GetMapping("/storeDetail")
    public String storeDetail(@RequestParam("sno") int sno, Model model) {
        Store store = storeService.getStoreById(sno);
        List<Menu> menus = menuService.getMenuBySno(sno);
        model.addAttribute("store", store);
        model.addAttribute("menus", menus);
        return "storeDetail";
    }
    @GetMapping("/storeInfo")
    public String storeInfo(@RequestParam("sno") int sno, Model model) {
        Store store = storeService.getStoreById(sno);
        List<Menu> menus = menuService.getMenuBySno(sno);
        model.addAttribute("store", store);
        model.addAttribute("menus", menus);
        return "storeInfo"; // storeInfo.jsp로 포워드
    }


}
