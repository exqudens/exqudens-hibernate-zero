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

import com.exqudens.hibernate.test.model.a.Item;
import com.exqudens.hibernate.test.model.a.Order;
import com.exqudens.hibernate.test.model.a.Seller;
import com.exqudens.hibernate.test.model.a.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestModelA {

    private static EntityManagerFactory emf;

    private EntityManager em;

    @BeforeClass
    public static void beforeClass() {
        emf = JpaTest.createEntityManagerFactory();
    }

    @Before
    public void before() {
        em = emf.createEntityManager();
    }

    //@Ignore
    @Test
    public void test1Create() {
        System.out.println("=== test1Create =========================================================================");
        List<User> users = new ArrayList<>();
        List<Seller> sellers = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<Item> items = new ArrayList<>();

        users.add(new User(null, null, "email_" + 1, new ArrayList<>()));

        sellers.add(new Seller(null, null, "name_1", new ArrayList<>()));

        orders.add(new Order(null, null, "orderNumber_" + 1, null, null, new ArrayList<>()));
        orders.add(new Order(null, null, "orderNumber_" + 2, null, null, new ArrayList<>()));
        orders.add(new Order(null, null, "orderNumber_" + 3, null, null, new ArrayList<>()));

        items.add(new Item(null, null, "description_" + 1, null, null, new ArrayList<>()));
        items.add(new Item(null, null, "description_" + 2, null, null, new ArrayList<>()));
        items.add(new Item(null, null, "description_" + 3, null, null, new ArrayList<>()));

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

    @Ignore
    @Test
    public void test2Read() {
        System.out.println("=== test2Read ===========================================================================");
        User user = em.find(User.class, 1L);
        em.clear();
        System.out.println(user);
        System.out.println("=========================================================================================");
    }

    @Ignore
    @Test
    public void test3Update() {
        System.out.println("=== test3Update =========================================================================");

        User user = em.find(User.class, 1L);
        user.setEmail("email_999");

        user.getOrders().get(1).getItems().add(
            new Item(null, null, "description_4", user.getOrders().get(1), null, new ArrayList<>())
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

        user = em.find(User.class, 1L);
        System.out.println(user);
        System.out.println("=========================================================================================");
    }

    @Ignore
    @Test
    public void test4Delete() {
        System.out.println("=== test4Delete =========================================================================");
        User user = em.find(User.class, 1L);
        em.remove(user);
        em.remove(user.getOrders().get(0).getSeller());
        em.getTransaction().begin();
        em.flush();
        em.getTransaction().commit();
        em.clear();
        System.out.println("=========================================================================================");
    }

    @Ignore
    @Test
    public void test0() {
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
