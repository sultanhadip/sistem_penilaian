package com.polstat.mp2k.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Nilai")
public class Nilai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private float Nilai_Pretest;
    @Column(nullable = false)
    private float Nilai_Posttest;
    @Column(nullable = false)
    private float Nilai_Angka;
    @Column(nullable = true)
    private String Nilai_Huruf;
    @Column(nullable = true)
    private float bobot;
    
    @ManyToOne
    @JoinColumn(name= "materiKU_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MateriKU materiKU;
    
    @ManyToOne
    @JoinColumn(name= "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
