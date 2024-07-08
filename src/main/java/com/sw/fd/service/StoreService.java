package com.sw.fd.service;

import com.sw.fd.entity.Store;
import com.sw.fd.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Transactional
    public void saveStore(Store store) {
        storeRepository.save(store);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStoreById(int sno) {
        return storeRepository.findBySno(sno).orElse(null);
    }
}
