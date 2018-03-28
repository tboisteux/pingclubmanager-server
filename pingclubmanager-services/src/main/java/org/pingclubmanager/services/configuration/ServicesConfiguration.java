package org.pingclubmanager.services.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:pingclubmanager.properties")
@Import(value = { RepositoryConfiguration.class, DataSourceConfiguration.class })
@EnableTransactionManagement
@ComponentScan(basePackages = { "org.pingclubmanager.services.business", "org.pingclubmanager.services.dao" })
@EnableAspectJAutoProxy
@EnableScheduling
public class ServicesConfiguration {
	protected String hibernateSearchIndexFolder = "C:/index";

	@Autowired
	private JndiObjectFactoryBean dataSource;

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
		return jpaTransactionManager;
	}

	@Bean
	public TransactionTemplate transactionTemplate() {
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		transactionTemplate.setTransactionManager(transactionManager());
		return transactionTemplate;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource((DataSource) dataSource.getObject());
		em.setPersistenceUnitName("javaconfigSamplePersistenceUnit");

		em.setPackagesToScan("org.pingclubmanager.domain.model", "org.pingclubmanager.services.envers");

		HibernateJpaVendorAdapter hjpa = new HibernateJpaVendorAdapter();
		hjpa.setShowSql(true);

		hjpa.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		em.setJpaVendorAdapter(hjpa);

		em.setJpaDialect(new HibernateJpaDialect());
		Properties jpaProps = new Properties();
		jpaProps.put("hibernate.default_schema", "application");
		jpaProps.put("hibernate.show_sql", "false");
		jpaProps.put("hibernate.search.default.directory_provider", "filesystem");
		jpaProps.put("hibernate.search.default.indexBase", this.hibernateSearchIndexFolder);
		jpaProps.put("hibernate.search.lucene_version", "LUCENE_CURRENT");
		// Define level of Hibernate auto creation of database. Used in Development
		// mode.
		// Prefer Patches in production mode with Flyway or get new Hibernate model
		// directly from PSA
		// jpaProps.put("hibernate.hbm2ddl.auto", "create");
		jpaProps.put("org.hibernate.envers.track_entities_changed_in_revision", "true");
		em.setJpaProperties(jpaProps);

		em.setBeanName("pcmEntityManagerFactory");
		em.afterPropertiesSet();
		return em.getObject();
	}

	/**
	 * Provides restTemplate for all services to use Set Jackson as JSON converter
	 * 
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		return restTemplate;
	}

	@Bean
	public Validator getValidator() {
		// Validation before creating
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}
}
