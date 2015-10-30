/**
 *
 */
package org.demo.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 30, 2015
 *
 */
@Configuration
public class PersistenceConfig {

	private static final Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);

	@Bean
	public InitializingBean initPersistence(EntityManagerFactory entityManagerFactory, PostLoadEventListener postLoadListener) {
		return () -> {
			logger.info("Setting up post load entity autowire");
			HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) entityManagerFactory;
			SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory();
			EventListenerRegistry registry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
			registry.appendListeners(EventType.POST_LOAD, postLoadListener);
		};
	}

	@Bean
	public PostLoadEventListener postLoadAutowireListener(AutowireCapableBeanFactory beanFactory) {
		return event -> {
			beanFactory.autowireBean(event.getEntity());
		};
	}

}
