package com.example.repo;

import com.example.entities.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Scanner;

@Repository
public class CountryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void closeEntityManager(){
        entityManager.close();
    }
    public void findAll() {
        TypedQuery<Country> query = entityManager.createQuery("SELECT t FROM Country t", Country.class);
        List<Country> list = query.getResultList();
        if(!list.isEmpty()){
            for(Country country : list){
                System.out.println(country);
            }
        }else System.out.println("Country table is empty!");

    }

    @Transactional
    public void save(Country country) {
        entityManager.persist(country);
    }

    public Country findById(Long id) {
        Country country = (Country) entityManager.find(Country.class, id);
        return country;
    }

    public void addCountry(CountryRepository countryRepository){
        Scanner sc = new Scanner(System.in);

        System.out.println("id");
        Long id = Long.parseLong(sc.nextLine());

        System.out.println("Name");
        String name = sc.nextLine();

        System.out.println("Money");
        Integer money = sc.nextInt();

        Country newCountry = new Country();

        newCountry.setCountryId(id);
        newCountry.setNameCountry(name);
        newCountry.setMoney(money);
        countryRepository.save(newCountry);
        countryRepository.findAll();

    }
    @Transactional
    public Country update(Country country) {
        entityManager.merge(country);
        return country;
    }

    @Transactional
    public Country deleteById(Long id) {
        Country country = findById(id);
        if (country != null) {
            entityManager.remove(country);
        }
        return country;
    }

    @Transactional
    public int deleteAll() {
        Query query = entityManager.createQuery("DELETE FROM Country");
        return query.executeUpdate();
    }

    public void findBySomething(String column, String value) {
        Query query = entityManager.createQuery("SELECT f FROM Country f WHERE f." + column + " = '" + value + "'");
        List list = query.getResultList();
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public void groupBy(String column){
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT f." + column + ", COUNT(f) FROM Country f GROUP BY f." + column, Object[].class);
        List<Object[]>list = query.getResultList();

        for (Object[] obj: list){
            Object row = (Object) obj[0];
            Long count = (Long)obj[1];
            System.out.println(row+": "+count);
        }
    }
}
