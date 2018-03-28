package org.pingclubmanager.services.dao.crud;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

/**
 * Interface for CRUD operations performed on the database. (DRY concept)
 * 
 */
public interface CrudServiceDAO {

	/**
	 * Get Columns metadatas for a specific table
	 * 
	 * @param tableName
	 * @return column information
	 */
	public List<HashMap<String, Object>> getColumnsMetadata(String schema, String tableName);

	/**
	 * Active filters on concerned entities
	 */
	public void enableFilter(String filtre, Map<String, Object> parameters);

	public void enableFilter(String filtre, String paramName, Object param);

	/**
	 * Create a new entry in database for a given type. After calling this method ,
	 * the entity will be persisted in the database and refreshed. The current
	 * session will also be flushed. Any changes made ​​to the object will be kept
	 * in the context of persistence The entity is passed by reference , create (
	 * JPA persist ) must not return something in opposition update ( merge JPA)
	 * 
	 * @param <T>
	 *            Type of the object to create
	 * @param t
	 *            Objet a persister
	 */
	<T> void create(T t);

	/**
	 * Update the given object. The returned object is the object that is in the
	 * context of persistence. It can be used to make updates
	 * 
	 * @param <T>
	 *            Type of the object to update
	 * @param t
	 *            Object to update
	 * @return persisting object
	 */
	<T> T update(T t);

	/**
	 * Deletes the object identified by the primary key .
	 * 
	 * @param <T>
	 * @param <PK>
	 * @param type
	 *            , entity class type
	 * @param id
	 */
	<T, PK extends Serializable> void delete(Class<T> type, PK id);

	/**
	 * Return the list of objects
	 * 
	 * @return
	 */
	public <T> List<T> findAll(Class<T> className);

	/**
	 * 
	 * @param className
	 * @param criterion
	 * @return
	 */
	public <T> List<T> findByCriteria(Class<T> className, final Criterion... criterion);

	/**
	 * 
	 * @param className
	 * @param firstResult
	 * @param maxResults
	 * @param criterion
	 * @return
	 */
	public <T> List<T> findByCriteria(Class<T> className, final int firstResult, final int maxResults,
			final Criterion... criterion);

	/**
	 * Find an object by primary key.
	 * 
	 * @param <T>
	 *            Type of the returned object
	 * @param <PK>
	 *            Object ID
	 * @param type
	 *            Type of the object to find
	 * @param id
	 *            primary key
	 * @return the object
	 */
	<T, PK extends Serializable> T find(Class<T> type, PK id);

	/**
	 * Get a list of objects from a query name
	 * 
	 * @param <T>
	 *            Type of the returned objects
	 * @param queryName
	 *            query name
	 * @return returns a list of objects
	 */
	<T> List<T> findWithNamedQuery(String queryName);

	/**
	 * Recover a list of items from a query name and parameters.
	 * 
	 * @param <T>
	 *            Type of the returned objects
	 * @param queryName
	 *            query name
	 * @param params
	 *            Parameters
	 * @return resulting list
	 */
	<T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params);

	int deleteWithNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * Recover a list of items from a query and parameters.
	 * 
	 * @param <T>
	 *            Type of the returned objects
	 * @param query
	 *            query
	 * @param params
	 *            Parameters
	 * @return resulting list
	 */
	<T> List<T> findWithQuery(String queryString, Map<String, Object> params);

	/**
	 * Recover a single result, a from a query name without parameter.
	 * 
	 * @param <T>
	 *            Type of the returned objects
	 * @param queryName
	 *            query name
	 * @return T object
	 */
	<T> T findUniqueWithNamedQuery(String queryName);

	/**
	 * Recover a single result, a from a query name and parameters.
	 * 
	 * @param <T>
	 *            Type of the returned objects
	 * @param queryName
	 *            query name
	 * @param params
	 *            Parameters
	 * @return T object
	 */
	<T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * Recover a maximum number of results , from a query name without parameters.
	 * 
	 * @param <T>
	 *            Type of the returned objects
	 * @param queryName
	 *            query name
	 * @param range
	 *            Number of results to return
	 * @return results list
	 */
	<T> T findMaxResultsWithNamedQuery(String queryName, int range);

	/**
	 * Recover a maximum number of results , from a query name and parameters.
	 * 
	 * @param <T>
	 *            Type of the returned objects
	 * @param queryName
	 *            query name
	 * @param params
	 *            Parameters
	 * @param range
	 *            Number of results to return
	 * @return results list
	 */
	<T> List<T> findMaxResultsWithNamedQuery(String queryName, Map<String, Object> params, int range);

	/**
	 * Retrieves a Range of results, from a named query and parameters
	 * 
	 * @param <T>
	 *            Type of the returned objects
	 * @param queryName
	 *            query name
	 * @param startIndex
	 *            start index of the range
	 * @param endIndex
	 *            end index of the range
	 * @param params
	 *            Parameters
	 * @return results list
	 */
	<T> List<T> findRangeResultsWithNamedQuery(String queryName, int startIndex, int endIndex,
			Map<String, Object> params);

	/**
	 * Get the number of entities of a given type <code>T</code>.
	 * 
	 * @return the number of entities of the given type <code>T</code>
	 */
	<T> long count(Class<T> type);

	/**
	 * Get the number of entities of a given type <code>T</code> which match the
	 * parameters <code>params</code>.
	 * 
	 * @param queryName
	 *            query name
	 * @param params
	 *            Parameters
	 * @return he number of entities of the given type <code>T</code>
	 */
	<T> long count(String queryName, Map<String, Object> params);

	/**
	 * Return a Criterion hibernate (used for managers in order to serve as
	 * repositories for dataGrid)
	 * 
	 * @param classe
	 *            class
	 * @return a Criteria Hibernate for the given type
	 */
	<T> Criteria getCriteria(Class<T> classe);

	<T> Session getSession();

	/**
	 * Get NextVal from sequence
	 * 
	 * @param sequence
	 * @return NextVal value
	 */
	<T> BigInteger getSeqNextVal(String sequence);

	/**
	 * Execute Native Query
	 * 
	 * @param nativeQuery
	 * @return result of native query
	 */
	List<Object> executeNativeQuery(String nativeQuery);

	/**
	 * Execute Native Query with parameters
	 * 
	 * @param nativeQuery
	 * @param parameters
	 * @return result of native query
	 */
	List<Object> executeNativeQuery(String nativeQuery, Map<String, Object> parameters);

	/**
	 * Execute native query using Hibernate session and mapping the_geom as
	 * GeometryType
	 * 
	 * @param nativeQuery
	 * @param parameters
	 * @return
	 */
	public List<Object> executeNativeGeometricQuery(String nativeQuery, Map<String, Object> parameters,
			List<HashMap<String, Object>> metadatas);

	/**
	 * Get all ordered by
	 * 
	 * @param className
	 * @param orderBy
	 * @param order
	 * @return
	 */
	<T> List<T> findAllOrderBy(Class<T> className, String orderBy, String order);

	<T> BigInteger getSeqCurrentVal(String sequence);

	/**
	 * Get all distinct entries by column
	 * 
	 * @param <T>
	 * @param className
	 * @param column
	 * @return List<Object>
	 */
	<T> List<Object> findDistinctByColumn(Class<T> className, String column);

	int executeCUDNativeQuery(String nativeQuery, Map<String, Object> parameters);

}