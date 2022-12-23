package com.example.repo;

import com.example.entities.Country;
import com.example.entities.Department;
import com.example.entities.Medicine;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class MedicineRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void closeEntityManager(){
        entityManager.close();
    }
    public void findAll() {
        TypedQuery<Medicine> query = entityManager.createQuery("SELECT t FROM Medicine t", Medicine.class);
        List<Medicine> list = query.getResultList();
        if(!list.isEmpty()){
            for(Medicine medicine : list){
                System.out.println(medicine);
            }
        }else System.out.println("Medicine table is empty!");
    }

    @Transactional
    public void save(Medicine medicine) {
        entityManager.persist(medicine);
    }

    public Medicine findById(Long id) {
        return (Medicine) entityManager.find(Medicine.class, id);
    }

    public void addMedicine(MedicineRepository medicineRepository){
        Medicine newMedicine = new Medicine();

        Scanner sc = new Scanner(System.in);

        System.out.println("id");
        Long id = Long.parseLong(sc.nextLine());

        System.out.println("Type");
        String type = sc.nextLine();

        System.out.println("Name");
        String name = sc.nextLine();

        System.out.println("Country");
        String countryName = sc.nextLine();
        Query q = entityManager.createQuery("SELECT c FROM Country c WHERE c.nameCountry = '" + countryName + "'");

        Country country = (Country) q.getResultList().get(0);

        newMedicine.setMedicineId(id);
        newMedicine.setType(type);
        newMedicine.setNameMedicine(name);
        newMedicine.setCountry(country);

        System.out.println("Departments id");
        String departmentsId = sc.nextLine();
        String[] departmentIdArr = departmentsId.replaceAll(" ", "").split(",");
        for(String number:departmentIdArr){
            q = entityManager.createQuery("SELECT c FROM Department c WHERE c.departmentId = " + Integer.parseInt(number));
            Department department = (Department) q.getResultList().get(0);
            newMedicine.getDepartments().add(department);
        }


        medicineRepository.update(newMedicine);
        medicineRepository.findAll();

    }
    @Transactional
    public Medicine update(Medicine Medicine) {
        entityManager.merge(Medicine);
        return Medicine;
    }

    @Transactional
    public Medicine deleteById(Long id) {
        Medicine Medicine = findById(id);
        if (Medicine != null) {
            entityManager.remove(Medicine);
        }
        return Medicine;
    }

    @Transactional
    public int deleteAll() {
        Query query = entityManager.createQuery("DELETE FROM Medicine");
        return query.executeUpdate();
    }

    public void findBySomething(String column, String value) {
        Query query = entityManager.createQuery("SELECT f FROM Medicine f WHERE f." + column + " = '" + value + "'");
        List list = query.getResultList();
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public void groupBy(String column){
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT f." + column + ", COUNT(f) FROM Medicine f GROUP BY f." + column, Object[].class);
        List<Object[]>list = query.getResultList();

        for (Object[] obj: list){
            Object row = (Object) obj[0];
            Long count = (Long)obj[1];
            System.out.println(row+": "+count);
        }
    }
}
