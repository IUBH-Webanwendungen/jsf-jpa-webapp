package com.byteslounge.jsf.war;;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Simple storage DAO which will use the environment found JPA's EntityManager
 * to store and read data.
 * <p>
 *     In its current form: if it inserts basic content if did not find content
 *     and otherwise makes the current content available.
 *     It is useful, for this to comment -in our -out the property
 *     javax.persistence.schema-generation.database.action in the persistence.xml.
 * </p>
 *
 */
@ApplicationScoped
@ManagedBean
public class DataStore
{
    public static void main( String[] args )
    {
        DataStore app = new DataStore();
    }

    public DataStore() {
        System.out.println("Starting app...");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("simple-jps-app");
        entityManager = factory.createEntityManager();
        System.err.println("EntityManager: " + entityManager);

        try {
            List<Pojo1> r= readData();
            if(r.isEmpty()) {
                System.out.println("Populating...");
                insertData();
            }
            r = readData();
            for(Pojo1 p: r) {
                System.out.println("- " + p.getId() + " : " + p.getAttr());
            }
            System.err.println("Finished");

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public String getPojos() {
        List<Pojo1> list = readData();
        return list.toString();
    }

    EntityManager entityManager;

    private void insertData() throws Throwable {

        entityManager.getTransaction().begin();
        Pojo1 p1 = new Pojo1(), p2 = new Pojo1();
        p1.setAttr("this is one");
        p2.setAttr("composed by user " + System.getProperty("user.name") + " on " + new Date());
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.getTransaction().commit();
    }

    private List<Pojo1> readData() {
        String jpql = "SELECT p from Pojo1 p";
        Query query = entityManager.createQuery(jpql);
        List<Pojo1> result = (List<Pojo1>) query.getResultList();
        return result;
    }

}
