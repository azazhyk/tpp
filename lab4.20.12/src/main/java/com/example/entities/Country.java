package com.example.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="country")
public class Country {

    @Id
    @Column(name="country_id")
    private Long countryId;
    @Column(name="name_country")
    private String nameCountry;

    @Column(name="money")
    private Integer money;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<Medicine> medicineList = new ArrayList<>();

    public Country() {
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public List<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    public Country (String nameCountry, Integer money) {
        this.nameCountry = nameCountry;
        this.money = money;
        //this.medicineList = medicineList;
    }

    @Override
    public String toString() {
        String medicineName = "'";
        for(Medicine medicine : medicineList){
            medicineName += medicine.getNameMedicine() + "', ";
        }
        return "Country{" +countryId+
                ". nameCountry='" + nameCountry + '\'' +
                ", money=" + money +
                ", medicineList=" + medicineName +
                '}';
    }
}
