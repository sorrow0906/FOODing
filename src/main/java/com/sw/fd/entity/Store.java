package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "store_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    private int sno;

    private String sname;
    private String saddr;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> menus;
}
