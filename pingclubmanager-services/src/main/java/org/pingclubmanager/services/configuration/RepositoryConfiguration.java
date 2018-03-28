package org.pingclubmanager.services.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.pingclubmanager.services.dao")
public class RepositoryConfiguration {

}
