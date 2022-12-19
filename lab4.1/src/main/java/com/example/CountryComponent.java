package com.example;

import com.example.entities.Country;
import com.example.repo.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@EnableJpaRepositories(basePackages = "com.example.repo")
public class CountryComponent {
//    @Autowired
//    private static EntityManager em;

//    @PersistenceContext
//    private static EntityManager em;

    @Autowired
    private static CountryRepository countryRepository;

    public static void addCountry(){
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

//    static void backupDB(EntityManager entityManager){
//        EntityTransaction tx;
//        tx = entityManager.getTransaction();
//        tx.begin();
//
//        Country country1 = new Country();
//        country1.setNameCountry("USA");
//        country1.setMoney(2000);
//
//
//        Director director1 = new Director();
//        director1.setName("Anthony");
//        director1.setSurname("Russo");
//        Director director2 = new Director();
//        director2.setName("Joe");
//        director2.setSurname("Russo");
//        List<Director> directors1 = new ArrayList<>();
//        directors1.add(director1);
//        directors1.add(director2);


//
//        Film film1 = new Film();
//        film1.setName("Avengers: Endgame");
//        film1.setYear(2019);
//        film1.setCountry(country1);
//        film1.setDirectors(directors1);
//        List<Film> films1 = new ArrayList<>();
//        films1.add(film1);
//
//        //connection1
//        country1.setFilms(films1);
//        director1.setFilms(films1);
//        director2.setFilms(films1);


//        entityManager.persist(country1);
////        entityManager.persist(director1);
////        entityManager.persist(director2);
////        entityManager.persist(film1);
//
//        tx.commit();
//    }
}
