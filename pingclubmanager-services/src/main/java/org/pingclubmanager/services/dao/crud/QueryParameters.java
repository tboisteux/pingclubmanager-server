package org.pingclubmanager.services.dao.crud;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for the use of parameters associated with JPQL named queries
 * <code>@NamedQuery</code>.
 * 
 * @author Tony Boisteux
 */
public class QueryParameters {
	private Map<String, Object> parameters = null;

	private QueryParameters(String name, Object value) {
		this.parameters = new HashMap<String, Object>();
		this.parameters.put(name, value);
	}

	public static QueryParameters with(String name, Object value) {
		return new QueryParameters(name, value);
	}

	public QueryParameters and(String name, Object value) {
		this.parameters.put(name, value);
		return this;
	}

	public Map<String, Object> parameters() {
		return this.parameters;
	}
}