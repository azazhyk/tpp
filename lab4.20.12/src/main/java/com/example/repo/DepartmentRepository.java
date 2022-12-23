package com.example.repo;

import com.example.entities.Department;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;
import com.example.entities.Medicine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Repository
public class DepartmentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void closeEntityManager(){
        entityManager.close();
    }

    public void findAll() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT t FROM Department t", Department.class);
        List<Department> list = query.getResultList();
        if(!list.isEmpty()){
            for(Department Department : list){
                System.out.println(Department);
            }
        }else System.out.println("Department table is empty!");

    }

    @Transactional
    public void save(Department Department) {
        entityManager.persist(Department);
    }

    public Department findById(Integer id) {
        return (Department) entityManager.find(Department.class, id);
    }

    public void addDepartment(DepartmentRepository departmentRepository, MedicineRepository medicineRepository){
        Department newDepartment = new Department();

        Scanner sc = new Scanner(System.in);
        System.out.println("id");
        Long id = Long.parseLong(sc.nextLine());
        System.out.println("Name");
        String name = sc.nextLine();

        newDepartment.setDepartmentId(id);
        newDepartment.setNameDepartment(name);
        System.out.println("People");

        Integer people = Integer.parseInt(sc.nextLine());
        newDepartment.setPeople(people);

        System.out.println("Medicines");
        String medicineName = sc.nextLine();
        String[] medicineNameArr = medicineName.split(",");
        for(String medicineItem:medicineNameArr){
            Query q = entityManager.createQuery("SELECT c FROM Medicine c WHERE c.nameMedicine = '" + medicineItem + "'");
            Medicine medicine = (Medicine) q.getResultList().get(0);
            medicine.getDepartments().add(newDepartment);
            medicineRepository.update(medicine);
        }

        departmentRepository.update(newDepartment);
        departmentRepository.findAll();

    }
    @Transactional
    public Department update(Department Department) {
        entityManager.merge(Department);
        return Department;
    }

    @Transactional
    public Department deleteById(Integer id) {
        Department Department = findById(id);
        if (Department != null) {
            entityManager.remove(Department);
        }
        return Department;
    }

    @Transactional
    public int deleteAll() {
        Query query = entityManager.createQuery("DELETE FROM Department");
        return query.executeUpdate();
    }

    public void findBySomething(String column, String value) {
        Query query = entityManager.createQuery("SELECT f FROM Department f WHERE f." + column + " = '" + value + "'");
        List list = query.getResultList();
        for (Object obj : list) System.out.println(obj);
    }

    public void groupBy(String column){
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT f." + column + ", COUNT(f) FROM Department f GROUP BY f." + column, Object[].class);
        List<Object[]>list = query.getResultList();

        for (Object[] obj: list){
            Object row = (Object) obj[0];
            Long count = (Long)obj[1];
            System.out.println(row+": "+count);
        }
    }
}
