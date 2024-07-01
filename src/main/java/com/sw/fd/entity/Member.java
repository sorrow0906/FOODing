package com.sw.fd.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "member_t") // 데이터베이스 테이블과 매핑
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

    // 기본 생성자
    public Member() {}

    // 필요한 생성자들
    public Member(String mid, String mname, String mpass, int mtype, String mnick, String mbirth, String mphone, String memail, String maddr, LocalDate mdate) {
        this.mid = mid;
        this.mname = mname;
        this.mpass = mpass;
        this.mtype = mtype;
        this.mnick = mnick;
        this.mbirth = mbirth;
        this.mphone = mphone;
        this.memail = memail;
        this.maddr = maddr;
        this.mdate = mdate;
    }

    // Getters and Setters
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMpass() {
        return mpass;
    }

    public void setMpass(String mpass) {
        this.mpass = mpass;
    }

    public int getMtype() {
        return mtype;
    }

    public void setMtype(int mtype) {
        this.mtype = mtype;
    }

    public String getMnick() {
        return mnick;
    }

    public void setMnick(String mnick) {
        this.mnick = mnick;
    }

    public String getMbirth() {
        return mbirth;
    }

    public void setMbirth(String mbirth) {
        this.mbirth = mbirth;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getMemail() {
        return memail;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }

    public String getMaddr() {
        return maddr;
    }

    public void setMaddr(String maddr) {
        this.maddr = maddr;
    }

    public LocalDate getMdate() {
        return mdate;
    }

    public void setMdate(LocalDate mdate) {
        this.mdate = mdate;
    }

    @PrePersist
    protected void onCreate() {
        mdate = LocalDate.now();
    }
}
