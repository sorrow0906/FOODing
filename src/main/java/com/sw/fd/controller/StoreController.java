package com.sw.fd.controller;

import com.sw.fd.entity.*;
import com.sw.fd.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private TagService tagService;
    @Autowired
    private LocationService locationService;

    private static final String DEFAULT_ARRD = "대구광역시 동구 동부로 121";
    private static final double DEFAULT_LAT = 35.8799906; // 대구광역시 동구 동부로 121의 위도
    private static final double DEFAULT_LON = 128.6286206; // 대구광역시 동구 동부로 121의 경도

    @GetMapping("/storeList")
    public String showStoreList(@RequestParam(value = "scate", required = false) String scate, Model model) {
        List<Store> stores;
        if (scate != null && !scate.isEmpty()) {
            stores = storeService.getStoresByCategory(scate);
        } else {
            stores = storeService.getAllStores();
        }
        model.addAttribute("stores", stores);
        return "storeList";
    }

    @GetMapping("/storeDetail")
    public String storeDetail(@RequestParam("sno") int sno, Model model) {
        Store store = storeService.getStoreAllInfo(sno);
        List<Menu> menus = menuService.getMenuBySno(sno);
        List<StoreTag> storeTags = storeService.getStoreTagsByStoreSno(sno);
        int rCount = reviewService.getReviewsBySno(sno).size();
        System.out.println("rCount = " + rCount);
        System.out.println("<s" + sno + "가게의 태그수>");
        for(StoreTag storeTag : storeTags) {
            System.out.println(storeTag.getTag().getTtag() +"의 수: " + storeTag.getTagCount());
        }
        model.addAttribute("rCount", rCount);
        model.addAttribute("store", store);
        model.addAttribute("menus", menus);
        model.addAttribute("storeTags", storeTags);
        return "storeDetail";
    }

    @GetMapping("/storeInfo")
    public String storeInfo(@RequestParam("sno") int sno, Model model) {
        Store store = storeService.getStoreById(sno);
        List<Menu> menus = menuService.getMenuBySno(sno);
        model.addAttribute("store", store);
        model.addAttribute("menus", menus);
        return "storeInfo";
    }

    @GetMapping("/storeListByLocation")
    public String showStoreListByLocation(
            @RequestParam(value = "userLat", required = false) Double userLat,
            @RequestParam(value = "userLon", required = false) Double userLon,
            @RequestParam(value = "inputAddr", required = false) String inputAddr,
            Model model) throws Exception {

        System.out.println("inputAddr = "+ inputAddr);
        if (userLat == null || userLon == null) {
            userLat = DEFAULT_LAT;
            userLon = DEFAULT_LON;
        }
        if (inputAddr != null) {
            double[] coordinates = locationService.getCoordinates(inputAddr);
            userLat = coordinates[0];
            userLon = coordinates[1];
        }

        if(inputAddr == null)
            inputAddr = LocationService.getAddr(userLat, userLon);

        List<Store> stores = storeService.getNearbyStores(userLat, userLon);
        stores.sort(Comparator.comparingDouble(Store::getDistance));

/*        for (Store store : stores) {
            System.out.println(store.getSname() + "의 거리는 " + store.getDistance());
        }*/


        model.addAttribute("stores", stores);
        model.addAttribute("nowAddr", inputAddr);
        model.addAttribute("defaultLat", DEFAULT_LAT);
        model.addAttribute("defaultLon", DEFAULT_LON);
        return "storeListByLocation";
    }


    @GetMapping("/storeListByRank")
    public String showStoreListByPick(@RequestParam(value = "sortBy", required = false) String sortBy, Model model) {
        List<Store> stores = storeService.getAllStores();

        if ("score".equals(sortBy)) {
            stores.sort(Comparator.comparingDouble(Store::getScoreArg).reversed());
            model.addAttribute("sortStandard", "score");
        } else {
            stores.sort(Comparator.comparingInt(Store::getPickNum).reversed());
            model.addAttribute("sortStandard", "pick");
        }

        model.addAttribute("stores", stores);

        return "storeListByRank";
    }

    @GetMapping("/storeListByTag")
    public String showStoreListByTag(@RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "tnos", required = false) String tnos, Model model/*, Integer tno*/) {

        List<Tag> allTags = tagService.getAllTags();
        model.addAttribute("allTags", allTags);

        List<StoreTag> storeTags;
        if (tnos != null && !tnos.isEmpty()) {
            String[] stringTnos = tnos.split(",");
            // 가져온 태그들을 서버로그에 띄우기 위해서 사용
            for (String tno : stringTnos) {
                System.out.println(tno);
            }
            List<Integer> numTnos = new ArrayList<>();
            for (String tno : stringTnos) {
                numTnos.add(Integer.parseInt(tno));
            }
            storeTags = storeService.getStoreTagsByTnos(numTnos);
        } else {
            // 기본적으로 첫 번째 태그를 사용하거나 다른 기본 값을 사용
            storeTags = storeService.getStoreTagsByTno(1);
        }

        for(StoreTag storeTag : storeTags) {
            System.out.println("storeTag.getStore().getScoreArg() = "+ storeTag.getStore().getScoreArg());
            System.out.println("storeTag.getStore().getPickNum() = "+ storeTag.getStore().getPickNum());
        }

        if ("score".equals(sortBy)) {
            /*storeTags.sort(Comparator.comparingDouble(storeTag-> storeTag.getStore().getScoreArg()).reversed());*/
            model.addAttribute("sortStandard", "score");
        } else {
            /*storeTags.sort(Comparator.comparingDouble(storeTag-> storeTag.getStore().getPickNum()).reversed());*/
            model.addAttribute("sortStandard", "pick");
        }

        model.addAttribute("stores", storeTags);

        return "storeListByTag";
    }
}
