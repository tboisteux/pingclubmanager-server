package org.pingclubmanager.rest.configuration;

import org.pingclubmanager.rest.configuration.condition.SwaggerCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.models.Swagger;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Activate Swagger documentation. Swagger documentation accessible on URL :
 * /swagger-ui.html Swagger base doc is /V2/api-docs
 * 
 * @see SpringFox http://springfox.github.io/springfox/
 * @see Swagger Annotations use
 *      https://github.com/swagger-api/swagger-core/wiki/Annotations
 * @author Tony Boisteux
 *
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
@Conditional(SwaggerCondition.class)
public class SwaggerConfiguration {

	/**
	 * Configure use of swagger
	 * Public API
	 * @return
	 */
	@Bean
	public Docket swaggerPublicPortalAPI() {
		// Swagger version
		return new Docket(DocumentationType.SWAGGER_2)
				// API Name
				.groupName("api")
				.apiInfo(
						new ApiInfoBuilder().description("Ping club manager Java RESTFUL API")
						.version("1.0.0")
						.title("Ping club manager").build())
				.select()
				.apis(RequestHandlerSelectors.any())
				// All Controllers
				.paths(PathSelectors.regex("/.*")) // and by paths
				.build();
	}
}
