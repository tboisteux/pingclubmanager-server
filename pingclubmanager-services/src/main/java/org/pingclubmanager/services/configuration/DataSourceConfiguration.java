package org.pingclubmanager.services.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * @see annotation PropertySource to load datasource properties
 * @author Tony Boisteux
 *
 */
@Configuration
public class DataSourceConfiguration {

	@Bean
	public JndiObjectFactoryBean dataSource() {
		JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
		dataSource.setExpectedType(DataSource.class);
		dataSource.setJndiName("java:/comp/env/jdbc/pcm");
		return dataSource;
	}

}
