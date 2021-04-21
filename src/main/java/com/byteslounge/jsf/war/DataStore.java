package com.byteslounge.jsf.war;

;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Simple storage DAO which will use the environment found JPA's EntityManager
 * to store and read data.
 * <p>
 * In its current form: if it inserts basic content if did not find content
 * and otherwise makes the current content available.
 * It is useful, for this to comment -in our -out the property
 * javax.persistence.schema-generation.database.action in the persistence.xml.
 * </p>
 */
@ApplicationScoped
@ManagedBean
public class DataStore {
    public static void main(String[] args) {
        DataStore app = new DataStore();
    }

    public DataStore() {
        System.out.println("Starting app...");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("simple-jps-app");
        entityManager = factory.createEntityManager();
        System.err.println("EntityManager: " + entityManager);

        try {
            long r = readDataCount();
            if (r == 0) {
                System.out.println("Populating...");
                insertData();
            }
            System.err.println("Finished");

        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("Count: " + readDataCount());

    }

    public String getPojos() {
        List<Pojo> list = readData();
        return list.toString();
    }

    EntityManager entityManager;

    private void insertData() throws Throwable {

        Pojo p1 = new Pojo();
        p1.setText("this is one");
        entityManager.getTransaction().begin();
        entityManager.persist(p1);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        for(int i=0; i<1000; i++) {
            Pojo pojo = new Pojo();
            pojo.setText("Random " + ((int) (Math.random()*1000000)) +" composed by user " + System.getProperty("user.name") + " on " + new Date());
            entityManager.persist(pojo);
            System.out.println("Persisting: " + pojo);
        }
        entityManager.getTransaction().commit();

        /*
        entityManager.getTransaction().begin();
        Pojo p1 = new Pojo(), p2 = new Pojo();
        p1.setText("this is one");
        p2.setText("composed by user " + System.getProperty("user.name") + " on " + new Date());
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.getTransaction().commit();*/
    }

    private List<Pojo> readData() {
        String jpql = "SELECT p from Pojo p";
        Query query = entityManager.createQuery(jpql);
        List<Pojo> result = (List<Pojo>) query.getResultList();
        return result;
    }
    private long readDataCount() {
        String jpql = "SELECT p FROM Pojo p";
        Query query = entityManager.createQuery(jpql);

        return query.getResultStream().count();
    }

    private List<Pojo> readData(int first, int maxCount) {
        String jpql = "SELECT p from Pojo p";
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult(first).setMaxResults(maxCount);
        List<Pojo> result = (List<Pojo>) query.getResultList();
        return result;
    }

    public LazyDataModel<Pojo> readDataLazyModel() {
        return new LazyDataModel<Pojo>() {
            @Override
            public int getRowCount() {
                return new Long(readDataCount()).intValue();
            }



            @Override
            public List<Pojo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                String jpql = "SELECT p from Pojo p";
                Query query = entityManager.createQuery(jpql);
                query.setFirstResult(first).setMaxResults(pageSize);
                List<Pojo> result = (List<Pojo>) query.getResultList();
                // currently ignoring sort-fields and filter
                return result;

            }
        };
    }

    public LazyDataModel<Pojo> getLazyModel() { return readDataLazyModel(); }

}
