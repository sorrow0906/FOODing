package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @ManyToOne
    @JoinColumn(name = "mno", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "sno", nullable = false)
    private Store store;

    private int rstar;
    private String rcomm;
    private LocalDateTime rdate;

    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
    private List<ReviewTag> reviewTags;

    @Transient
    private List<Tag> tags;

    @Transient
    private String dateToString;

    @PrePersist
    protected void onCreate() {
        rdate = LocalDateTime.now();
    }
}
