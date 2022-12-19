package stu.tpp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import stu.tpp.util.HibernateUtil;
import stu.tpp.components.Country;
import stu.tpp.components.Director;
import stu.tpp.components.Film;
import stu.tpp.operations.Insert;
import stu.tpp.operations.Select;

public class Main {
    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.OFF);
        SessionFactory factory = HibernateUtil.getSessionFactory();
        EntityManager entityManager = factory.createEntityManager();

        backupDB(entityManager);

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
                    case "1" -> Select.selectFrom(entityManager, table);
                    case "2" -> {
                        System.out.println("Введіть колонку:");
                        String column = scanner.nextLine();
                        System.out.println("Введіть значення "+column+" для пошуку: ");
                        String value = scanner.nextLine();
                        Select.selectWhere(entityManager, table, column, value);
                    }
                    case "3" -> {
                        System.out.println("Введіть колонку для групування:");
                        String column1 = scanner.nextLine();
                        Select.selectGroupBy(entityManager, table, column1);
                    }
                    default -> System.out.println("Такої операції не існує");
                }
            }
            case "insert" -> {
                System.out.println("Виберіть таблицю:");
                String table = scanner.nextLine();
                switch (table) {
                    case "Film" -> Insert.insertFilm(entityManager, scanner);
                    case "Director" -> Insert.insertDirector(entityManager, scanner);
                    case "Country" -> Insert.insertCountry(entityManager, scanner);
                }
            }
            default -> System.out.println("Такої операції не існує");
        }
        scanner.close();
        entityManager.close();
        factory.close();
    }
    private static void backupDB(EntityManager entityManager){
        EntityTransaction tx;
        tx = entityManager.getTransaction();
        tx.begin();

        Country country1 = new Country();
        country1.setCountry("USA");
        country1.setRating(1);

        Country country2 = new Country();
        country2.setCountry("Germany");
        country2.setRating(3);

        Director director1 = new Director();
        director1.setName("Anthony");
        director1.setSurname("Russo");
        Director director2 = new Director();
        director2.setName("Joe");
        director2.setSurname("Russo");
        List<Director> directors1 = new ArrayList<>();
        directors1.add(director1);
        directors1.add(director2);

        Director director3 = new Director();
        director3.setName("James");
        director3.setSurname("Wan");
        List<Director> directors2 = new ArrayList<>();
        directors2.add(director3);


        Film film1 = new Film();
        film1.setName("Avengers: Endgame");
        film1.setYear(2019);
        film1.setCountry(country1);
        film1.setDirectors(directors1);
        List<Film> films1 = new ArrayList<>();
        films1.add(film1);

        //connection1
        country1.setFilms(films1);
        director1.setFilms(films1);
        director2.setFilms(films1);


        Film film2 = new Film();
        film2.setName("You, Me and Dupree");
        film2.setYear(2006);
        film2.setCountry(country2);
        film2.setDirectors(directors1);
        List<Film> films2 = new ArrayList<>();
        films2.add(film2);

        //connection2
        country2.setFilms(films2);
        films1.add(film2);
        director1.getFilms().add(film2);
        director2.getFilms().add(film2);

        Film film3 = new Film();
        film3.setName("Furious 7");
        film3.setYear(2015);
        film3.setCountry(country1);
        film3.setDirectors(directors2);
        List<Film> films3 = new ArrayList<>();
        films3.add(film3);

        //connection3
        country1.getFilms().add(film3);
        director3.setFilms(films3);

        entityManager.persist(country1);
        entityManager.persist(country2);
        entityManager.persist(director1);
        entityManager.persist(director2);
        entityManager.persist(director3);
        entityManager.persist(film1);
        entityManager.persist(film2);
        entityManager.persist(film3);

        tx.commit();
    }
}