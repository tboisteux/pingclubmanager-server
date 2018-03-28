package org.pingclubmanager.services.dao.crud;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

/**
 * @author Tony Boisteux
 * @param <T>,
 *            type entity
 * @param <PK>,
 *            primarykey, the primary key
 */
@Repository
public class JpaCrudDAO implements CrudServiceDAO {

	@PersistenceContext
	protected EntityManager entityManager;

	public static final String COLUMN_NAME = "COLUMN_NAME";
	public static final String TYPE_NAME = "TYPE_NAME";
	public static final String COLUMN_SIZE = "COLUMN_SIZE";
	public static final String COLUMN_INSTANCE = "COLUMN_INSTANCE";

	@Override
	public List<HashMap<String, Object>> getColumnsMetadata(String schema, String tableName) {

		HibernateEntityManager hem = this.entityManager.unwrap(HibernateEntityManager.class);
		SessionImplementor session = (SessionImplementor) hem.getDelegate();

		DatabaseMetaData metadata = null;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		try {
			metadata = session.connection().getMetaData();
			ResultSet resultSet = metadata.getColumns(null, schema, tableName, null);
			ResultSetMetaData md = resultSet.getMetaData();
			int columns = md.getColumnCount();
			while (resultSet.next()) {
				HashMap<String, Object> row = new HashMap<>(columns);
				for (int i = 1; i <= columns; ++i) {
					row.put(md.getColumnName(i), resultSet.getObject(i));
				}

				// Add specific column for instanciated type
				switch ((String) row.get(TYPE_NAME)) {
				case "integer":
				case "serial":
				case "int4":
				case "int2":
					row.put(COLUMN_INSTANCE, IntegerType.INSTANCE);
					break;
				case "varchar":
				case "text":
					row.put(COLUMN_INSTANCE, StringType.INSTANCE);
					break;
				case "numeric":
					row.put(COLUMN_INSTANCE, DoubleType.INSTANCE);
					break;
				// case "geometry":
				// row.put(COLUMN_INSTANCE, GeometryType.INSTANCE);
				// break;
				case "timestamp":
					row.put(COLUMN_INSTANCE, DateType.INSTANCE);
					break;
				case "bool":
					row.put(COLUMN_INSTANCE, BooleanType.INSTANCE);
					break;
				}

				list.add(row);
			}
		} catch (SQLException e) {

		}

		return list;

	}

	public void enableFilter(String filtre, Map<String, Object> parameters) {
		// Enable filters on Hibernate Session
		HibernateEntityManager hem = this.entityManager.unwrap(HibernateEntityManager.class);
		Session session = (Session) hem.getDelegate();
		Filter filter = session.enableFilter(filtre);

		if (filter != null) {

			for (String key : parameters.keySet()) {
				filter.setParameter(key, parameters.get(key));
			}
		}

		// If needed : Add parameters to the specific filter getEnabledFilter
		/*
		 * if (filter == null) { //always get into the if, the filter must be enabled
		 * every time. filter = session.enableFilter("FilterDomain");
		 * filter.setParameter("param1", "A"); }
		 */
	}

	public void enableFilter(String filtre, String paramName, Object param) {
		// Enable filters on Hibernate Session
		HibernateEntityManager hem = this.entityManager.unwrap(HibernateEntityManager.class);
		Session session = (Session) hem.getDelegate();
		Filter filter = session.enableFilter(filtre);
		if (filter != null) {

			filter.setParameter(paramName, param);
		}

		// If needed : Add parameters to the specific filter getEnabledFilter
		/*
		 * if (filter == null) { //always get into the if, the filter must be enabled
		 * every time. filter = session.enableFilter("FilterDomain");
		 * filter.setParameter("param1", "A"); }
		 */
	}

	public <T> void create(T t) {
		this.entityManager.persist(t);
	}

	public <T> T update(final T type) {
		entityManager.merge(type);
		return type;
	}

	public <T, PK extends Serializable> T find(Class<T> type, PK id) {
		return (T) entityManager.find(type, id);
	}

	public <T, PK extends Serializable> void delete(Class<T> type, PK id) {
		T ref = (T) entityManager.find(type, id);
		entityManager.remove(ref);
	}

	/**
	 * @see be.bzbit.framework.domain.repository.GenericRepository#findAll()
	 */
	@Override
	public <T> List<T> findAll(Class<T> className) {
		return findByCriteria(className);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findWithNamedQuery(String queryName) {
		return entityManager.createNamedQuery(queryName).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = entityManager.createNamedQuery(queryName);

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.getResultList();
	}

	public int deleteWithNamedQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = entityManager.createNamedQuery(queryName);

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		int deleted = query.executeUpdate();
		return deleted;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findWithQuery(String queryString, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();

		Query query = entityManager.createQuery(queryString);

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> T findMaxResultsWithNamedQuery(String queryName, int range) {
		Query query = entityManager.createNamedQuery(queryName);
		query.setMaxResults(range);
		return (T) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueWithNamedQuery(String queryName) {
		Query query = entityManager.createNamedQuery(queryName);
		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = entityManager.createNamedQuery(queryName);

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findMaxResultsWithNamedQuery(String queryName, Map<String, Object> params, int range) {
		Query query = entityManager.createNamedQuery(queryName);
		Set<Entry<String, Object>> rawParameters = params.entrySet();

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		query.setMaxResults(range);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findRangeResultsWithNamedQuery(String queryName, int startIndex, int endIndex,
			Map<String, Object> params) {
		Query query = entityManager.createNamedQuery(queryName);
		if (params != null) {
			Set<Entry<String, Object>> rawParameters = params.entrySet();

			for (Entry<String, Object> entry : rawParameters) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.setFirstResult(startIndex);
		query.setMaxResults(1 + endIndex - startIndex);
		return query.getResultList();
	}

	@Override
	public <T> long count(Class<T> type) {
		Query query = this.entityManager.createQuery("SELECT count(*) FROM " + type.getName());
		Long count = (Long) query.getSingleResult();
		return count;
	}

	@Override
	public <T> long count(String queryName, Map<String, Object> params) {
		return (Long) this.findUniqueWithNamedQuery(queryName, params);
	}

	@Override
	public <T> Criteria getCriteria(Class<T> classe) {
		HibernateEntityManager hem = this.entityManager.unwrap(HibernateEntityManager.class);
		Session session = (Session) hem.getDelegate();

		Criteria critere = session.createCriteria(classe);
		return critere;
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	public <T> List<T> findByCriteria(Class<T> className, final Criterion... criterion) {
		return findByCriteria(className, -1, -1, criterion);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByCriteria(Class<T> className, final int firstResult, final int maxResults,
			final Criterion... criterion) {
		HibernateEntityManager hem = this.entityManager.unwrap(HibernateEntityManager.class);
		Session session = (Session) hem.getDelegate();

		Criteria crit = session.createCriteria(className);

		for (final Criterion c : criterion) {
			crit.add(c);
		}

		if (firstResult > 0) {
			crit.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			crit.setMaxResults(maxResults);
		}

		final List<T> result = crit.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Object> findDistinctByColumn(Class<T> className, final String column) {

		Criteria crit = this.getCriteria(className);
		crit.setProjection(Projections.distinct(Projections.property(column)));
		List<Object> result = crit.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAllOrderBy(Class<T> className, final String orderBy, final String order) {

		switch (order.toLowerCase()) {
		case "asc":
			return this.getCriteria(className).addOrder(Property.forName(orderBy).asc()).list();
		case "desc":
			return this.getCriteria(className).addOrder(Property.forName(orderBy).desc()).list();
		default:
			return this.getCriteria(className).addOrder(Property.forName(orderBy).asc()).list();
		}
	}

	@Override
	public <T> Session getSession() {
		HibernateEntityManager hem = this.entityManager.unwrap(HibernateEntityManager.class);
		return (Session) hem.getDelegate();
	}

	@Override
	public <T> BigInteger getSeqNextVal(String sequence) {
		Query q = this.entityManager.createNativeQuery("SELECT NEXTVAL(:sequence);");
		q.setParameter("sequence", sequence);
		BigInteger result = (BigInteger) q.getSingleResult();
		return result;
	}

	@Override
	public <T> BigInteger getSeqCurrentVal(String sequence) {
		Query q = this.entityManager.createNativeQuery("select last_value from :sequence;");
		q.setParameter("sequence", sequence);
		BigInteger result = (BigInteger) q.getSingleResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executeNativeQuery(String nativeQuery) {
		Query q = this.entityManager.createNativeQuery(nativeQuery);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executeNativeQuery(String nativeQuery, Map<String, Object> parameters) {

		Query query = this.entityManager.createNativeQuery(nativeQuery);

		Set<Entry<String, Object>> rawParameters = parameters.entrySet();

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.getResultList();
	}

	@Override
	public int executeCUDNativeQuery(String nativeQuery, Map<String, Object> parameters) {

		Query query = this.entityManager.createNativeQuery(nativeQuery);

		Set<Entry<String, Object>> rawParameters = parameters.entrySet();

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.executeUpdate();
	}

	/**
	 * Execute native query using Hibernate session and mapping the_geom as
	 * GeometryType
	 * 
	 * @param nativeQuery
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executeNativeGeometricQuery(String nativeQuery, Map<String, Object> parameters,
			List<HashMap<String, Object>> metadatas) {
		HibernateEntityManager hem = this.entityManager.unwrap(HibernateEntityManager.class);
		Session session = (Session) hem.getDelegate();
		SQLQuery query = session.createSQLQuery(nativeQuery);
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		for (HashMap<String, Object> metadata : metadatas) {
			query.addScalar((String) metadata.get(JpaCrudDAO.COLUMN_NAME),
					(Type) metadata.get(JpaCrudDAO.COLUMN_INSTANCE));
		}

		return query.list();
	}

}