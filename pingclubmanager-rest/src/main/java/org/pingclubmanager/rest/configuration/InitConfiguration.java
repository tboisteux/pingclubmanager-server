package org.pingclubmanager.rest.configuration;

import org.pingclubmanager.rest.configuration.interceptors.LoggerInterceptor;
import org.pingclubmanager.services.configuration.ServicesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import(value = { SwaggerConfiguration.class, ServicesConfiguration.class })
@EnableWebMvc
@ComponentScan(basePackages = { "org.pingclubmanager.rest.controller", "org.pingclubmanager.rest.exception" })
public class InitConfiguration implements WebMvcConfigurer {

	private static final int CACHE_PERIOD = 31556926; // one year - See more at:
														// http://javaetmoi.com/2014/06/spring-framework-java-configuration/#comments

	public LoggerInterceptor loggerInterceptor() {
		return new LoggerInterceptor();
	}

	// @Bean
	// public ViewResolver viewResolver() {
	// // Example: the 'info' view logical name is mapped to the file
	// '/WEB-INF/jsp/info.jsp'
	// InternalResourceViewResolver bean = new InternalResourceViewResolver();
	// bean.setViewClass(JstlView.class);
	// bean.setPrefix("/WEB-INF/jsp/");
	// bean.setSuffix(".jsp");
	// return bean;
	// }

	/**
	 * Provides localiszation messages for MVC view JSP
	 * 
	 * @return
	 */
	/*
	 * @Bean(name = "messageSource") public ReloadableResourceBundleMessageSource
	 * reloadableResourceBundleMessageSource() {
	 * ReloadableResourceBundleMessageSource messageSource = new
	 * ReloadableResourceBundleMessageSource();
	 * messageSource.setBasenames("classpath:com/javaetmoi/sample/web/messages");
	 * messageSource.setDefaultEncoding("UTF-8"); return messageSource; }
	 */

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Static ressources from both WEB-INF and webjars
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(CACHE_PERIOD);

		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/")
				.setCachePeriod(CACHE_PERIOD);

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
				.setCachePeriod(CACHE_PERIOD);
	}

	/**
	 * Example of a custom page without controller Add custom login page to have
	 * original login of Drupal user for corporate portal access
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("forward:/login.html");
		; // the about page did not required any controller
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// Serving static files using the Servlet container's default Servlet.
		configurer.enable();
	}

	/**
	 * Create Spring message Handler for internationalization Configure messages as
	 * baseaname followed by language (_fr, _en, _...)
	 * 
	 * @see <a href="http://simplespringtutorial.com/i18n.html">Internationalization
	 *      documentation</a>
	 * @return ResourceBundleMessageSource
	 */
	/*
	 * @Bean public ReloadableResourceBundleMessageSource
	 * resourceBundleMessageSource() {
	 * 
	 * ReloadableResourceBundleMessageSource resourceBundleMessageSource = new
	 * ReloadableResourceBundleMessageSource();
	 * resourceBundleMessageSource.setBasename("classpath:messages");
	 * 
	 * return resourceBundleMessageSource; }
	 */

	/**
	 * Store locale in session
	 * 
	 * @return SessionLocaleResolver
	 */
	/*
	 * @Bean
	 * 
	 * @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) public
	 * SessionLocaleResolver sessionLocaleResolver() { SessionLocaleResolver
	 * sessionLocaleResolver = new SessionLocaleResolver();
	 * sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
	 * 
	 * return sessionLocaleResolver; }
	 */

	/**
	 * Add Spring request interceptors LocaleChangeInterceptor change locale of the
	 * application use : url?locale=en url?locale=fr
	 * 
	 * @see <a href=
	 *      "http://www.concretepage.com/spring/spring-mvc/spring-handlerinterceptor-annotation-example-webmvcconfigureradapter">Interceptors
	 *      documentation</a>
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		// LocaleChangeInterceptor localeChangeInterceptor = new
		// LocaleChangeInterceptor();
		// localeChangeInterceptor.setParamName("locale");

		registry.addInterceptor(this.loggerInterceptor());
	}

	/*
	 * @Override public void addFormatters(FormatterRegistry formatterRegistry) { //
	 * add your custom formatters }
	 */

	/**
	 * Set up and use JSR-303 Validation for the arguments in Spring MVC request
	 * mapping methods for path variable and request parameter arguments
	 * 
	 * @return
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}
