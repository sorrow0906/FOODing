package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "group_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gno")
    private int gno;

    @OneToMany(mappedBy = "group")
    private List<MemberGroup> memberGroupList;

    @Column(name = "gname", nullable = false)
    private String gname;

    @Column(name = "gdate", nullable = false)
    private LocalDateTime gdate;

    private String gimage;

    @PrePersist
    protected void onCreate() {
        gdate = LocalDateTime.now();
    }
}