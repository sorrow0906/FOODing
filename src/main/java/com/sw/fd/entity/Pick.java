package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pick_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;

    @ManyToOne
    @JoinColumn(name = "pfno", nullable = false)
    private Pick pick;

    @ManyToOne
    @JoinColumn(name = "mno", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "sno", nullable = false)
    private Store store;


    public Pick(Member member, Store store) {
        this.member = member;
        this.store = store;
    }

}
