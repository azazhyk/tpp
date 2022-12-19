package com.example.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="department")
public class Department {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="department_id")
    private Long departmentId;

    @Column(name="name_department")
    private String nameDepartment;

    @Column(name="people")
    private Integer people;

    @ManyToMany(mappedBy = "departments",fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private List<Medicine> medicineList = new ArrayList<>();

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public List<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    public Department() {
    }

    @Override
    public String toString() {
        String medicineName = "'";
        for(Medicine medicine : medicineList){
            medicineName += medicine.getNameMedicine() + "', ";
        }
        return "Department{" +
                "nameDepartment='" + nameDepartment + '\'' +
                ", people=" + people +
                ", medicineList=" + medicineName +
                '}';
    }
}