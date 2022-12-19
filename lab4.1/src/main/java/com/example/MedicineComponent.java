package com.example;

import com.example.entities.Medicine;
import com.example.repo.MedicineRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MedicineComponent {
    @Autowired
    private EntityManager em;

    @Autowired
    private MedicineRepository medicineRepository;

    public void addMedicine(){
        Medicine medicine = new Medicine();
        Scanner sc = new Scanner(System.in);
        System.out.println("Введіть перше значення");

        em.persist(medicine);
        System.out.println("Medicine add");
        List<Medicine> medicineList = medicineRepository.findAll();
    }
}
