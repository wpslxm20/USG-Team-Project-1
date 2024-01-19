package com.example.back.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="hotel")
public class Hotel {
    
    @Id
    @Column(name = "hotel_num")
    private String hotel_num;

    @OneToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "status")
    private String status;

    @Column(name = "option")
    private String option;
 
}
