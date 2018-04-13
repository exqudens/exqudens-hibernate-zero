package com.exqudens.hibernate.util;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityManagerFactoryUtils {

    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(EntityManagerFactoryUtils.class);
        LOG.trace("");
    }

    public static EntityManagerFactory createEntityManagerFactory(Map<String, DataSource> dataSourceMap, Map<String, Object> properties, Class<?>... classes) {
        LOG.trace("");
        try {
            PersistenceUnitInfo info = PersistenceUnitInfoUtils.createPersistenceUnitInfo(
                    "default",
                    PersistenceUnitInfoUtils.HIBERNATE_PERSISTENCE_PROVIDER_CLASS_NAME,
                    properties,
                    classes
            );
            EntityManagerFactory entityManagerFactory = createEntityManagerFactory(info);
            return entityManagerFactory;
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static EntityManagerFactory createEntityManagerFactory(
            PersistenceUnitInfo info
    ) {
        LOG.trace("");
        try {
            ClassLoader cl = EntityManagerFactoryUtils.class.getClassLoader();
            Object o = cl.loadClass(info.getPersistenceProviderClassName()).newInstance();
            PersistenceProvider persistenceProvider = PersistenceProvider.class.cast(o);
            EntityManagerFactory emf = persistenceProvider.createContainerEntityManagerFactory(info, info.getProperties());
            return emf;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EntityManagerFactoryUtils() {
        super();
        LOG.trace("");
    }

}
