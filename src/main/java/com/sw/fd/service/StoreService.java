package com.sw.fd.service;

import com.sw.fd.entity.ReviewTag;
import com.sw.fd.entity.Store;
import com.sw.fd.entity.StoreTag;
import com.sw.fd.entity.Tag;
import com.sw.fd.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class StoreService {
    @Autowired
    private LocationService locationService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PickRepository pickRepository;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @Autowired
    private StoreTagRepository storeTagRepository;


    @Transactional
    public void saveStore(Store store) {
        storeRepository.save(store);
/*        updateStoreTags(store);*/
    }

   /* public void updateStoreTags(Store store) {
        List<ReviewTag> reviewTags = reviewTagRepository.findByReview_Store_Sno(store.getSno());

        for (ReviewTag reviewTag : reviewTags) {
            System.out.println(reviewTag.getReview().getStore().getSno() + "에 대한 리뷰 태그: " + reviewTag.getTag());
        }

        Map<Integer, Long> tagCountMap = new HashMap<>();
        for (ReviewTag reviewTag : reviewTags) {
            int tno = reviewTag.getTag().getTno();
            Long count = tagCountMap.get(tno);
            if (count == null) {
                tagCountMap.put(tno, 1L);
            } else {
                tagCountMap.put(tno, count + 1);
            }
        }

        for (Map.Entry<Integer, Long> entry : tagCountMap.entrySet()) {
            StoreTag storeTag = new StoreTag();
            storeTag.setStore(store);
            Tag tag = new Tag();
            tag.setTno(entry.getKey());
            storeTag.setTag(tag);
            storeTag.setTCount(entry.getValue().intValue());
            storeTagRepository.save(storeTag);
        }
    }
*/

    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        List<Store> newStores = new ArrayList<>();
        for(Store store : stores) {
            newStores.add(getStoreAllInfo(store.getSno()));
        }

        return newStores;
    }

    public Store getStoreAllInfo(int sno) {
        Store store = storeRepository.findBySno(sno).orElse(null);
        if (store != null) {
/*            updateStoreTags(store);*/

            // 별점 평균 계산
            Double averageScore = reviewRepository.findAverageScoreBySno(sno);
            store.setScoreArg(averageScore != null ? averageScore : 0);

            // Pick 수 계산
            int pickCount = pickRepository.countBySno(sno);
            store.setPickNum(pickCount);
        }

        return store;
    }

    public Store getStoreById(int sno) {
        return storeRepository.findBySno(sno).orElse(null);
    }

    public List<Store> getStoresByCategory(String scate) {
        return storeRepository.findByScate(scate);
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
        List<Store> allStores = storeRepository.findAll();
        List<Store> nearbyStores = new ArrayList<>();

        for (Store store : allStores) {
            double[] coordinates = locationService.getCoordinates(store.getSaddr());
            double distance = calculateDistance(userLat, userLon, coordinates[0], coordinates[1]);
            if (distance <= 2) {
                store.setDistance(distance); //거리 저장하는 부분
                nearbyStores.add(store);
            }
        }
        return nearbyStores;
    }

    public List<Store> getStoresWithPick() {
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            int pickNum = pickRepository.countBySno(store.getSno());
            store.setPickNum(pickNum);
        }
        return stores;
    }

}
