package stu.tpp.operations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import stu.tpp.components.Country;
import stu.tpp.components.Director;
import stu.tpp.components.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Insert {
    public static void insertFilm(EntityManager entityManager, Scanner scanner) {
        EntityTransaction tx;
        tx = entityManager.getTransaction();
        tx.begin();
        Film film = new Film();

        System.out.println("Введіть назву фільму: ");
        String name = scanner.nextLine();
        film.setName(name);

        System.out.println("Введіть країну: ");
        String countryName = scanner.nextLine();
        Query q = entityManager.createQuery("SELECT c FROM Country c WHERE c.country = '" + countryName + "'");
        List list = q.getResultList();
        Country country = (Country) list.get(0);
        country.getFilms().add(film);
        film.setCountry(country);

        System.out.println("Введіть id режисерів: ");
        String directorId = scanner.nextLine();
        String[] directorsId = directorId.split(",");
        List<Director> directors = new ArrayList<>();
        for(String id: directorsId){
            q = entityManager.createQuery("SELECT c FROM Director c WHERE c.directorId = '" + Integer.parseInt(id) + "'");
            List listDirector = q.getResultList();
            Director director = (Director) listDirector.get(0);
            director.getFilms().add(film);
            directors.add(director);
        }
        film.setDirectors(directors);

        System.out.println("Введіть рік: ");
        Integer year = scanner.nextInt();
        film.setYear(year);

        entityManager.persist(film);
        tx.commit();

        Select.selectFrom(entityManager, "Film");
    }

    public static void insertDirector(EntityManager entityManager, Scanner scanner) {
        EntityTransaction tx;
        tx = entityManager.getTransaction();
        tx.begin();
        Director director = new Director();

        System.out.println("Введіть ім'я режисера: ");
        String directorName = scanner.nextLine();
        director.setName(directorName);

        System.out.println("Введіть прізвище режисера: ");
        String directorSurname = scanner.nextLine();
        director.setSurname(directorSurname);

        System.out.println("Введіть назви фільмів:");
        String allFilms = scanner.nextLine();
        String[] filmName = allFilms.split(",");
        List<Film> films = new ArrayList<>();
        for(String film : filmName){
            Query q = entityManager.createQuery("SELECT c FROM Film c WHERE c.name = '" + film + "'");
            List listFilm = q.getResultList();
            Film newFilm = (Film) listFilm.get(0);
            newFilm.getDirectors().add(director);
            films.add(newFilm);
        }
        director.setFilms(films);

        entityManager.persist(director);
        tx.commit();

        Select.selectFrom(entityManager, "Director");
    }

    public static void insertCountry(EntityManager entityManager, Scanner scanner) {
        EntityTransaction tx;
        tx = entityManager.getTransaction();
        tx.begin();

        Country country = new Country();
        System.out.println("Введіть назву країни:");
        String countryName = scanner.nextLine();
        country.setCountry(countryName);

        System.out.println("Введіть рейтинг:");
        Integer rating = scanner.nextInt();
        country.setRating(rating);

        entityManager.persist(country);
        tx.commit();

        Select.selectFrom(entityManager, "Country");
    }
}
