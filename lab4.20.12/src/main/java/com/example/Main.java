package com.example;

import com.example.entities.Country;
import com.example.entities.Department;
import com.example.entities.Medicine;
import com.example.repo.CountryRepository;
import com.example.repo.DepartmentRepository;
import com.example.repo.MedicineRepository;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String...args) throws Exception{
       backup();
       operation();
    }
    private void backup(){
        Country country1 = new Country();
        country1.setCountryId(1L);
        country1.setNameCountry("USA");
        country1.setMoney(2000);

        Department department1 = new Department();
        department1.setDepartmentId(1L);
        department1.setNameDepartment("department1");
        department1.setPeople(30);

        Medicine medicine1 = new Medicine();
        medicine1.setMedicineId(1L);
        medicine1.setNameMedicine("medicine1");
        medicine1.setType("type1");
        medicine1.setCountry(country1);
        medicine1.getDepartments().add(department1);

        countryRepository.update(country1);
        departmentRepository.update(department1);
        medicineRepository.update(medicine1);
    }
    private void operation(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Можливі операції \n\tselect \n\tinsert");
        System.out.println("Виберіть операцію:");
        String operation = scanner.nextLine();

        switch (operation) {
            case "select" -> {
                System.out.println("""
                        Можливі запити:
                        \t1. Показати всю таблицю
                        \t2. Пошук рядка за значенням колонки
                        \t3. Згрупувати таблицю за значенням колонки""");
                System.out.println("Введіть номер запиту:");
                operation = scanner.nextLine();
                System.out.println("Виберіть таблицю:");
                String table = scanner.nextLine();

                switch (operation) {
                    case "1" -> {
                        switch (table){
                            case "country" -> countryRepository.findAll();
                            case "medicine" -> medicineRepository.findAll();
                            case "department" -> departmentRepository.findAll();
                            default -> System.out.println("Такої таблиці не існує");
                        }
                    }
                    case "2" -> {
                        System.out.println("Введіть колонку:");
                        String column = scanner.nextLine();
                        System.out.println("Введіть значення "+column+" для пошуку: ");
                        String value = scanner.nextLine();
                        switch (table){
                            case "country" -> countryRepository.findBySomething(column,value);
                            case "medicine" -> medicineRepository.findBySomething(column,value);
                            case "department" -> departmentRepository.findBySomething(column,value);
                            default -> System.out.println("Такої таблиці не існує");
                        }
                    }
                    case "3" -> {
                        System.out.println("Введіть колонку для групування:");
                        String column1 = scanner.nextLine();
                        switch (table){
                            case "country" -> countryRepository.groupBy(column1);
                            case "medicine" -> medicineRepository.groupBy(column1);
                            case "department" -> departmentRepository.groupBy(column1);
                            default -> System.out.println("Такої таблиці не існує");
                        }
                    }
                    default -> System.out.println("Такої операції не існує");
                }
            }
            case "insert" -> {
                System.out.println("Виберіть таблицю:");
                String table = scanner.nextLine();
                switch (table) {
                    case "country" -> countryRepository.addCountry(countryRepository);
                    case "department" -> departmentRepository.addDepartment(departmentRepository, medicineRepository);
                    case "medicine" -> medicineRepository.addMedicine(medicineRepository);
                    default -> System.out.println("Такої таблиці не існує");
                }
            }
            default -> System.out.println("Такої операції не існує");
        }
        scanner.close();
        countryRepository.closeEntityManager();
        medicineRepository.closeEntityManager();
        departmentRepository.closeEntityManager();
    }
}