package org.pingclubmanager.rest.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @see http://javaetmoi.com/2014/06/spring-framework-java-configuration/
 *      documentation complète spring
 * @author Tony Boisteux
 *
 */
public class Initializer implements WebApplicationInitializer {

	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(InitConfiguration.class);
		// container.addListener(WebSessionListener.class);
		container.addListener(HttpSessionEventPublisher.class);
		// Set a custom session cookie name to not be mixed up with the default one
		// (problem with chrome)
		container.getSessionCookieConfig().setName("PINGCMGRID");
		ctx.setServletContext(container);

		Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));

		servlet.setLoadOnStartup(1);
		servlet.addMapping("/*");
	}

}
