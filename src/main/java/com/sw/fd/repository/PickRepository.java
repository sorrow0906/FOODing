package com.sw.fd.repository;

import com.sw.fd.entity.Pick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PickRepository extends JpaRepository<Pick, Integer> {
    Pick findByMnoAndSno(int mno, int sno);

    @Query("SELECT COUNT(p) FROM Pick p WHERE p.sno = :sno")
    int countBySno(@Param("sno") int sno);
}