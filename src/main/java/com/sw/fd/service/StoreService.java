package com.sw.fd.service;

import com.sw.fd.entity.ReviewTag;
import com.sw.fd.entity.Store;
import com.sw.fd.entity.StoreTag;
import com.sw.fd.entity.Tag;
import com.sw.fd.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @Autowired
    private StoreTagRepository storeTagRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PickRepository pickRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private TagRepository tagRepository;

    // Store 객체 캐시를 위한 맵
    private Map<Integer, Store> storeCache = new HashMap<>();

    // 처음 프로그램을 시작할 때 pick수와 별점 평균을 계산해서 캐시로 저장하기 위해 추가함
    @PostConstruct
    public void initializeStoreScores() {
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            updateStoreTags(store);
            calculateAndCacheStoreScores(store);
        }
    }

    @Transactional
    public void calculateAndCacheStoreScores(Store store) {
        Double averageScore = reviewRepository.findAverageScoreBySno(store.getSno());
        store.setScoreArg(averageScore != null ? averageScore : 0);

        int pickCount = pickRepository.countBySno(store.getSno());
        store.setPickNum(pickCount);

        // 캐시에 저장
        storeCache.put(store.getSno(), store);
    }


    public void saveStore(Store store) {
//        System.out.println("saveStore에 진입");
        storeRepository.save(store);
        updateStoreTags(store);
        calculateAndCacheStoreScores(store);
    }

    public List<StoreTag> getStoreTagsByStoreSno(int sno) {
        return storeTagRepository.findByStore_Sno(sno);
    }

    public List<StoreTag> getStoreTagsByTno(int tno) {
        List<StoreTag> storeTags = storeTagRepository.findByTag_Tno(tno);
        for (StoreTag storeTag : storeTags) {
            Store cachedStore = storeCache.get(storeTag.getStore().getSno());

            // 캐시된 값을 사용하여 별점 평균과 pick수를 set
            storeTag.getStore().setScoreArg(cachedStore.getScoreArg());
            storeTag.getStore().setPickNum(cachedStore.getPickNum());

        }
        return storeTags;
    }

    @Transactional
    public void updateStoreTags(Store store) {
        List<ReviewTag> reviewTags = reviewTagRepository.findByReview_Store_Sno(store.getSno());

        if (reviewTags == null || reviewTags.isEmpty()) {
            return;
        }

        Map<Integer, Long> tagCountMap = new HashMap<>();
        for (ReviewTag reviewTag : reviewTags) {
            int tno = reviewTag.getTag().getTno();
            tagCountMap.put(tno, tagCountMap.getOrDefault(tno, 0L) + 1);
        }

        for (Map.Entry<Integer, Long> entry : tagCountMap.entrySet()) {
            int tno = entry.getKey();
            long count = entry.getValue();

            StoreTag storeTag = storeTagRepository.findStoreTagByStoreSnoAndTagTno(store.getSno(), tno);

            if (storeTag == null) {
                storeTag = new StoreTag();
                storeTag.setStore(store);
                Tag tag = tagRepository.findByTno(tno);
                storeTag.setTag(tag);
                storeTag.setTagCount((int) count);
            } else {
                storeTag.setTagCount((int) count);
            }

            storeTagRepository.save(storeTag);
        }
        /*System.out.println("updateStoreTags 동작 완료");*/
    }


    public List<Store> getAllStores() {
        //        System.out.println("getAllStores에 진입");
        if (storeCache.isEmpty()) {
            initializeStoreScores();
        }

        // 그냥 list로 return하면 불변 list라서 정렬이 안 되기 때문에 복사해서 return함
        return new ArrayList<>(storeCache.values());
    }
    public Store getStoreAllInfo(int sno) {
        if (storeCache.isEmpty()) {
            initializeStoreScores();
        }
        return storeCache.get(sno);
    }
/*-----------------------별점과 Pick수를 초기화할 때마다 미리 계산해두어서 더이상 필요없어진 부분----------------*/

    /*    public List<Store> getAllStoresWithRank(){
        *//*        System.out.println("getAllStoresWithRank에 진입");*//*
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            Double averageScore = reviewRepository.findAverageScoreBySno(store.getSno());
            store.setScoreArg(averageScore != null ? averageScore : 0);

            // Pick 수 계산
            int pickCount = pickRepository.countBySno(store.getSno());
            store.setPickNum(pickCount);
            updateStoreTags(store);
        }
        return stores;
    }*/

/*    public Store getStoreAllInfo(int sno) {
        *//*        System.out.println("getStoreAllInfo에 진입");*//*
        Store store = storeRepository.findBySno(sno).orElse(null);
        if (store != null) {
            updateStoreTags(store);

            // 별점 평균 계산
            Double averageScore = reviewRepository.findAverageScoreBySno(sno);
            store.setScoreArg(averageScore != null ? averageScore : 0);

            // Pick 수 계산
            int pickCount = pickRepository.countBySno(sno);
            store.setPickNum(pickCount);
        }

        return store;
    }*/

    public List<StoreTag> getStoreTagsByTnos(List<Integer> tnos){
        return storeTagRepository.findByTag_tno(tnos);
    }

    public List<Store> getStoresByTagCountAndTno(int tno, List<Store> stores) {
        int rCount;
        int minTagCount = 2;
        List<Store> selectedStores = new ArrayList<>();

        for (Store store : stores) {
            StoreTag storeTag = storeTagRepository.findStoreTagByStoreSnoAndTagTno(store.getSno(), tno);

            System.out.println("서비스 단계에서 " + store.getSname() +"의 별점 평균: " + store.getScoreArg());
            System.out.println("서비스 단계에서 " + store.getSname() +"의 픽 수: " + store.getPickNum());


            // 해당 가게의 전체 리뷰수를 가져와서 태그가 리뷰의 30%이상을 차지했을 때 대표 태그로 판단
            rCount = reviewService.getReviewsBySno(store.getSno()).size();
            if (storeTag != null && storeTag.getTagCount() >= rCount*0.3) {
                selectedStores.add(store);
            }
        }

        return selectedStores;
    }

    public Store getStoreById(int sno) {
        if (storeCache.isEmpty()) {
            initializeStoreScores();
        }
        return storeCache.get(sno);
    }

    public List<Store> getStoresByCategory(String scate) {
        if (storeCache.isEmpty()) {
            initializeStoreScores();
        }
        List<Store> resultStores = new ArrayList<>();

        for (Store store : storeCache.values()) {

            // 카테고리가 일치하는 가게만 리스트에 추가
            if (store.getScate().equals(scate)) {
                resultStores.add(store);
            }
        }
        return resultStores;
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    public List<Store> getNearbyStores(double userLat, double userLon) {
        if (storeCache.isEmpty()) {
            initializeStoreScores();
        }
        List<Store> nearbyStores = new ArrayList<>();
        for (Store store : storeCache.values()) {
            double[] coordinates = locationService.getCoordinates(store.getSaddr());
            double distance = calculateDistance(userLat, userLon, coordinates[0], coordinates[1]);
            if (distance <= 2) {
                store.setDistance(distance); // 거리 저장하는 부분
                nearbyStores.add(store);
            }
        }
        return nearbyStores;
    }

}