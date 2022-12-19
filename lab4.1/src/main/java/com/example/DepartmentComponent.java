package com.example;

import com.example.entities.Department;
import com.example.repo.DepartmentRepository;
import com.example.repo.MedicineRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentComponent {
    @Autowired
    private EntityManager em;

    @Autowired
    private DepartmentRepository departmentRepository;

    public void addDepartment(){
        Department department = new Department();
        em.persist(department);
        System.out.println("Department add");
        List<Department> departments = departmentRepository.findAll();
    }
}
