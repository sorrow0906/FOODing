package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "member_t") // 데이터베이스 테이블과 매핑
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private String mid; // 기본 키로 사용될 필드

    private String mname;
    private String mpass;
    private int mtype;
    private String mnick;
    private String mbirth;
    private String mphone;
    private String memail;
    private String maddr;
    private LocalDate mdate;


    @PrePersist
    protected void onCreate() {
        mdate = LocalDate.now();
    }
}
