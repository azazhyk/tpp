package com.example;

import com.example.entities.Country;
import com.example.repo.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CountryService {
    @Autowired
    private  CountryRepository countryRepository;

    public  void addCountry(){
        //backupDB(em);
        Country newCountry = new Country();

        Scanner sc = new Scanner(System.in);

        System.out.println("Введіть назву");
        String name = sc.nextLine();
        newCountry.setNameCountry(name);

        System.out.println("Введіть бюджет");
        Integer money = sc.nextInt();
        newCountry.setMoney(money);

        try {
            countryRepository.save(newCountry);
        }catch (NullPointerException e){
            System.out.println(e);
        }
//        em.persist(newCountry);
        System.out.println("Country add");
        //List<Country> countryList = countryRepository.findAll();
    }
}
