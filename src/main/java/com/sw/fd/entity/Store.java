package com.sw.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "store_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    private String sid;

    private String sname;
    private String saddr;
}