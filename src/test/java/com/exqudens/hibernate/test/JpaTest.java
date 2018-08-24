package com.exqudens.hibernate.test;

import java.util.Map;
import java.util.function.Function;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import com.exqudens.hibernate.test.model.a.Item;
import com.exqudens.hibernate.test.model.a.Order;
import com.exqudens.hibernate.test.model.a.Seller;
import com.exqudens.hibernate.test.model.a.User;
import com.exqudens.hibernate.test.util.ClassPathUtils;
import com.exqudens.hibernate.test.util.ConfigGroovy;
import com.exqudens.hibernate.test.util.DataSourceUtils;
import com.exqudens.hibernate.util.PersistenceUnitInfoUtils;

interface JpaTest {

    static final String DS_DEFAULTS_PREFIX = "dataSources.mysqlDefaults.";
    static final String DS_PREFIX          = "dataSources.masterTestDataSource.";
    static final String JPA_PREFIX         = "jpaProviders.hibernateJpaProvider.properties.";

    static EntityManagerFactory createEntityManagerFactory() {
        return createEntityManagerFactory(DS_DEFAULTS_PREFIX, DS_PREFIX, JPA_PREFIX);
    }

    static EntityManagerFactory createEntityManagerFactory(
        String dataSourceDefaultsPrefix,
        String dataSourcePrefix,
        String jpaPrefix
    ) {
        try {
            ConfigGroovy configGroovy = new ConfigGroovy(null, ClassPathUtils.toString("config-test.groovy"));

            DataSource dataSource = DataSourceUtils.createDataSource(
                configGroovy.createDataSourceProperties(dataSourceDefaultsPrefix, dataSourcePrefix, Function.identity())
            );

            Map<String, Object> properties = configGroovy.retrieveProperties(jpaPrefix);

            PersistenceUnitInfo info = PersistenceUnitInfoUtils.createHibernatePersistenceUnitInfo(
                "default",
                dataSource,
                null,
                properties,
                User.class,
                Seller.class,
                Order.class,
                Item.class
            );

            ClassLoader cl = PersistenceUnitInfoUtils.class.getClassLoader();
            Object o = cl.loadClass(info.getPersistenceProviderClassName()).newInstance();
            PersistenceProvider persistenceProvider = PersistenceProvider.class.cast(o);
            EntityManagerFactory emf = persistenceProvider.createContainerEntityManagerFactory(
                info,
                info.getProperties()
            );
            return emf;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
