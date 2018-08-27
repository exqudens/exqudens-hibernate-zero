package com.exqudens.hibernate.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.exqudens.hibernate.test.model.aa.GraphItem;
import com.exqudens.hibernate.test.model.aa.GraphOrder;
import com.exqudens.hibernate.test.model.aa.GraphSeller;
import com.exqudens.hibernate.test.model.aa.GraphUser;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestModelAA {

    private static EntityManagerFactory emf;

    private EntityManager em;

    @BeforeClass
    public static void beforeClass() {
        emf = JpaTest.createEntityManagerFactory(GraphUser.class, GraphSeller.class, GraphOrder.class, GraphItem.class);
    }

    @Before
    public void before() {
        em = emf.createEntityManager();
    }

    @Ignore
    @Test
    public void test0() {
    }

    //@Ignore
    @Test
    public void test1Create() {
        System.out.println("=== test1Create =========================================================================");
        List<GraphUser> users = new ArrayList<>();
        List<GraphSeller> sellers = new ArrayList<>();
        List<GraphOrder> orders = new ArrayList<>();
        List<GraphItem> items = new ArrayList<>();

        users.add(new GraphUser(null, null, "email_" + 1, new ArrayList<>()));

        sellers.add(new GraphSeller(null, null, "name_1", new ArrayList<>()));

        orders.add(new GraphOrder(null, null, "orderNumber_" + 1, null, null, new ArrayList<>()));
        orders.add(new GraphOrder(null, null, "orderNumber_" + 2, null, null, new ArrayList<>()));
        orders.add(new GraphOrder(null, null, "orderNumber_" + 3, null, null, new ArrayList<>()));

        items.add(new GraphItem(null, null, "description_" + 1, null, null, new ArrayList<>()));
        items.add(new GraphItem(null, null, "description_" + 2, null, null, new ArrayList<>()));
        items.add(new GraphItem(null, null, "description_" + 3, null, null, new ArrayList<>()));

        users.get(0).getOrders().addAll(orders);

        sellers.get(0).getOrders().addAll(orders);

        orders.stream().forEach(o -> o.setUser(users.get(0)));
        orders.stream().forEach(o -> o.setSeller(sellers.get(0)));
        orders.get(1).setItems(items);

        items.stream().forEach(i -> i.setOrder(orders.get(1)));

        items.get(1).getChildren().add(items.get(0));
        items.get(1).getChildren().add(items.get(2));
        items.get(0).setParent(items.get(1));
        items.get(2).setParent(items.get(1));

        try {
            em.persist(users.get(0));
            em.persist(sellers.get(0));
            em.getTransaction().begin();
            em.flush();
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("=========================================================================================");
    }

    //@Ignore
    @Test
    public void test2Read() {
        System.out.println("=== test2Read ===========================================================================");
        GraphUser user = em.find(GraphUser.class, 1L);
        em.clear();
        System.out.println(user.getOrders().size());
        System.out.println("=========================================================================================");
    }

    @Ignore
    @Test
    public void test3Update() {
        System.out.println("=== test3Update =========================================================================");

        GraphUser user = em.find(GraphUser.class, 1L);
        user.setEmail("email_999");

        user.getOrders().get(1).getItems().add(
            new GraphItem(null, null, "description_4", user.getOrders().get(1), null, new ArrayList<>())
        );

        try {
            em.merge(user);
            em.getTransaction().begin();
            em.flush();
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        user = em.find(GraphUser.class, 1L);
        System.out.println(user);
        System.out.println("=========================================================================================");
    }

    @Ignore
    @Test
    public void test4Delete() {
        System.out.println("=== test4Delete =========================================================================");
        GraphUser user = em.find(GraphUser.class, 1L);
        em.remove(user);
        em.remove(user.getOrders().get(0).getSeller());
        em.getTransaction().begin();
        em.flush();
        em.getTransaction().commit();
        em.clear();
        System.out.println("=========================================================================================");
    }

    @After
    public void after() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @AfterClass
    public static void afterClass() {
        if (emf != null && emf.isOpen()) {
            // emf.close();
        }
    }

}
