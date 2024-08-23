package com.sw.fd.repository;

import com.sw.fd.entity.Member;
import com.sw.fd.entity.Pfolder;
import com.sw.fd.entity.Pick;
import com.sw.fd.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PickRepository extends JpaRepository<Pick, Integer> {
    Pick findByMemberAndStore(Member member, Store store);

    List<Pick> findByMemberMno(@Param("mno") int mno);

    // pick수 계산을 위해 추가한 부분 (다혜)
    @Query("SELECT COUNT(p) FROM Pick p WHERE p.store.sno = :sno")
    int countBySno(@Param("sno") int sno);

    List<Pick> findByPfolder(Pfolder pfolder);

    List<Pick> findByPfolder_PfnoAndMember_Mno(int pfno, int mno);

    void removeByStore_Sno(int sno);

    List<Pick> findByPfolder_PfnoAndMember_MnoAndStore_Sno(int pfno, int mno, int sno);

}