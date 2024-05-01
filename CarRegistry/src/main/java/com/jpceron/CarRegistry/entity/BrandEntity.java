package com.jpceron.CarRegistry.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brand")
public class BrandEntity {

    @Id
    private int id;

    @JsonManagedReference
    @OneToMany(mappedBy = "brand",cascade = CascadeType.ALL)
    private List<CarEntity> carEntityList;

    @Column(name = "name")
    private String name;

    @Column(name = "warranty")
    private int warranty;

    @Column(name =  "country")
    private String country;


}
