package stp.tpp;

import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:postgresql://localhost:5432/postgres";

        Properties prop = new Properties();
        prop.setProperty("user", "postgres");
        prop.setProperty("password", "password");

        //connection
        Connection conn = DriverManager.getConnection(url, prop);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from films");

//        while(resultSet.next()){
//            System.out.print("Id: "+resultSet.getString("id_films")+", ");
//            System.out.print("Name: "+resultSet.getString("name")+", ");
//            System.out.print("Year: "+resultSet.getString("year")+", ");
//            System.out.println("Rating: "+resultSet.getString("rating"));
//        }

        //scan input data
        Scanner sc = new Scanner(System.in);
        System.out.println("Можливі операції: select, delete, insert, update");
        System.out.println("Виберіть операцію: ");
        String operation = sc.nextLine();
        System.out.println("Яку таблицю будемо використовувати? ");
        String table = sc.nextLine();

        switch (operation) {
            case "select" -> {
                System.out.println("Яку колонку будемо виводити? (для виводу таблиці *) ");
                String column = sc.nextLine();
                if(Objects.equals(column, "*")){
                    resultSet = statement.executeQuery("select * from " + table);
                } else{
                    System.out.println("Яке значення хочете знайти? ");
                    String value = sc.nextLine();

                    System.out.println("Пошук значень в таблиці " + table + " по колонці " + column + " за значенням " + value);
                    resultSet = statement.executeQuery("select * from " + table + " where " + column + " = '" + value + "'");
                }
            }
            case "delete" -> {
                switchTables(resultSet, table);
                System.out.println("По якій колонці будемо шукати запис для видалення? ");
                String column = sc.nextLine();

                System.out.println("По якому значенню хочете видалити? ");
                String value = sc.nextLine();

                statement.executeUpdate("delete from " + table + " where " + column + " = '" + value + "'");

                System.out.println("Вивід таблиці " + table + " :");
                resultSet = statement.executeQuery("select * from " + table);
            }
            case "insert" -> {
                System.out.println("Значення будуть вставлятись в такій послідовності (name, year, rating,director number): ");
                String columns = "name, year, rating, director";

                System.out.println("Яке значення хочете додати в поле name?");
                String name ="'"+ sc.nextLine()+"',";
                System.out.println("Яке значення хочете додати в поле year?");
                String year = "'"+ sc.nextLine()+"',";
                System.out.println("Яке значення хочете додати в поле rating?");
                String rating ="'"+ sc.nextLine()+"',";
                System.out.println("Яке значення хочете додати в поле director?(id director)");
                String director = "'"+ sc.nextLine()+"'";

                statement.executeUpdate("insert into " + table + "(" + columns + ") values (" + name+year+rating+director + ")");

                System.out.println("Вивід таблиці " + table + " :");
                resultSet = statement.executeQuery("select * from " + table);
            }
            case "update" -> {
                switchTables(resultSet, table);
                System.out.println("За яким id змінювати дані?");
                String id = sc.nextLine();

                System.out.println("Яку колонку хочете змінити? ");
                String column = sc.nextLine();

                System.out.println("Яке нове значення для колонки " + column + "? ");
                String newValue = sc.nextLine();

                statement.executeUpdate("update " + table + " set " + column + " = '" + newValue + "' where id_"
                        + table + "= '" + id + "';");
                System.out.println("Вивід оновленого рядка таблиці " + table + " :");
                resultSet = statement.executeQuery("select * from " + table + " where " + column + " = " + newValue);
            }
            default -> System.out.println("Операція "+operation+" не знайдена.");
        }
        sc.close();
        switchTables(resultSet, table);
    }

    private static void switchTables(ResultSet resultSet, String table) throws SQLException {
        switch (table) {
            case "directors" -> printDirectors(resultSet);
            case "films" -> printFilms(resultSet);
            case "test" -> printTestDeleting(resultSet);
        }
    }

    private static void printFilms(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.print("\tId: "+resultSet.getString("id_films")+", ");
            System.out.print("Name: "+resultSet.getString("name")+", ");
            System.out.print("Year: "+resultSet.getString("year")+", ");
            System.out.println("Rating: "+resultSet.getString("rating"));
        }
    }
    private static void printDirectors(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.print("\tName: " + resultSet.getString("name") + ", ");
            System.out.println("Surname: " + resultSet.getString("surname"));
        }
    }
    private static void printTestDeleting(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.print("\tId: " + resultSet.getString("id_test") + ", ");
            System.out.println("Text: " + resultSet.getString("text"));
        }
    }
}