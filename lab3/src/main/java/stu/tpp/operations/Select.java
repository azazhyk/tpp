package stu.tpp.operations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import stu.tpp.components.Director;

import java.util.List;

public class Select {
    public static void selectFrom(EntityManager entityManager, String table) {
        Query q = entityManager.createQuery("SELECT f FROM " + table + " f");
        List list = q.getResultList();
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void selectWhere(EntityManager entityManager, String table, String column, String value) {
        Query q = entityManager.createQuery("SELECT f FROM " + table + " f WHERE f." + column + " = '" + value + "'");
        List list = q.getResultList();
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void selectGroupBy(EntityManager entityManager, String table, String column) {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT f." + column + ", COUNT(f) FROM " + table + " f GROUP BY f." + column, Object[].class);
        List<Object[]>list = query.getResultList();

        for (Object[] obj: list){
            Object row = (Object) obj[0];
            Long count = (Long)obj[1];
            System.out.println(row+": "+count);
        }
    }
}
