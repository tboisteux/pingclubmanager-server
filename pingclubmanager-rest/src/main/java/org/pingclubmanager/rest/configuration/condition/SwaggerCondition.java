package org.pingclubmanager.rest.configuration.condition;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SwaggerCondition implements Condition {

	protected final static String SWAGGER_PROPERTIE = "doc.rest.swagger";

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

		boolean swaggerActivated = false;
		Properties prop = new Properties();
		InputStream input = null;

		// load a properties file from class path, inside static method
		try {
			input = SwaggerCondition.class.getClassLoader().getResourceAsStream("pingclubmanager.properties");
			prop.load(input);

			swaggerActivated = Boolean.valueOf(prop.getProperty(SWAGGER_PROPERTIE));

		} catch (IOException e) {
			// TODO : Stop program ?
			new Exception("Unable to read swagger activation, no property file ?", e);
		} finally {
			IOUtils.closeQuietly(input);
		}

		return swaggerActivated;
	}

}
