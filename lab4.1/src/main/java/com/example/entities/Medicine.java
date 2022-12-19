package com.example.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="medicine_id")
    private Long medicineId;

    @Column(name="name_medicine")
    private String nameMedicine;

    @Column(name="type")
    private String type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "medicine_department",
            joinColumns = @JoinColumn(name="medicine_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments = new ArrayList<>();

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getNameMedicine() {
        return nameMedicine;
    }

    public void setNameMedicine(String nameMedicine) {
        this.nameMedicine = nameMedicine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Medicine() {
    }

    @Override
    public String toString() {
        String departmentName = "";
        for(Department department:departments){
            departmentName += department.getNameDepartment() + ", ";
        }
        return "Medicine{" +
                "nameMedicine='" + nameMedicine + '\'' +
                ", type='" + type + '\'' +
                ", country=" + country.getNameCountry() +
                ", departments=" + departmentName +
                '}';
    }
}
