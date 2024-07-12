package com.sw.fd.repository;

import com.sw.fd.entity.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Integer> {

}