package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "alarm_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ano;

    @Column(name = "`from`")
    private String from; // 출처

    private String atype; // 알림유형

    @ManyToOne
    @JoinColumn(name = "mno")
    private Member member; // 회원

    private int check; // 확인여부

    @Override
    public String toString() {
        return "Alarm{ano=" + ano + ", from='" + from + "', atype='" + atype + "'}";
    }
}
