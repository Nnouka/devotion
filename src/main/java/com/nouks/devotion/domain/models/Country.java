package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iso2;
    private String name;
    private String iso3;
    @Column(name = "num_code")
    private int numCode;
    @Column(name = "phone_code")
    private String phoneCode;
    @Column(name = "currency_name")
    private String currencyName;
    @Column(name = "currency_code")
    private String currencyCode;
    private String lang;
    private String locale;
}
