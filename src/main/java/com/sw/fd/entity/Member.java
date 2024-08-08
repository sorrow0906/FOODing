package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "member_t") // 데이터베이스 테이블과 매핑
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;

    @OneToMany(mappedBy = "member") // 수정사항
    private List<MemberGroup> memberGroupList; // 수정사항

    private String mid;
    private String mname;
    private String mpass;

    // 비밀번호 확인 필드 (테이블에 저장되지 않음)
    private transient String mpassConfirm;

    private int mtype;
    private String mnick;
    private String mbirth;
    private String mphone;
    private String memail;
    private String maddr;
    private LocalDate mdate;

    private int mwarning;

    @PrePersist
    protected void onCreate() {
        mdate = LocalDate.now();
    }

    public String getMpassConfirm() {
        return mpassConfirm;
    }

    public void setMpassConfirm(String mpassConfirm) {
        this.mpassConfirm = mpassConfirm;
    }
}
