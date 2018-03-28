package org.pingclubmanager.services.dao.manager.defaultManager.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.pingclubmanager.services.dao.crud.CrudServiceDAO;
import org.pingclubmanager.services.dao.manager.defaultManager.DefaultManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Implémentation générique de l'interface {@link DefaultManager}.
 * 
 * @author lguerin
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
@Repository
public class DefaultManagerImpl<T, PK extends Serializable> implements DefaultManager<T, PK> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DefaultManagerImpl.class);

	@Autowired
	private CrudServiceDAO crudDAO;

	@Autowired
	private Validator validator;

	/**
	 * Le type de l'objet courant.
	 */
	private Class<T> entityType;

	@SuppressWarnings("unchecked")
	public DefaultManagerImpl() {
		try {
			this.entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		} catch (ClassCastException e) {
			logger.info("Bean try to cast a bean that is not well typed, maybe at instanciation by Spring");
		}
	}

	public DefaultManagerImpl(Class<T> type) {
		this.entityType = type;
	}

	public void create(T t) throws Exception {
		Assert.notNull(t, "Object can not be null");
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		if (constraintViolations != null && constraintViolations.isEmpty()) {
			crudDAO.create(t);
		} else {
			logger.error("Error validating bean before insert");
			// TODO: Do a proper message for all fields
			constraintViolations.forEach(constraint -> logger.error("Field " + constraint.getMessage()));
			throw new Exception("Error validating bean before insert of type" + this.entityType);
		}
	}

	public T update(T t) throws Exception {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		if (constraintViolations != null && constraintViolations.isEmpty()) {
			return crudDAO.update(t);
		} else {
			logger.error("Error validating bean before update");
			// TODO: Do a proper message for all fields
			constraintViolations.forEach(constraint -> logger.error("Field " + constraint.getMessage()));
			throw new Exception("Error validating bean before update of type" + this.entityType);
		}
	}

	public void delete(PK id) {
		Assert.notNull(id, "Object id can not be null");
		crudDAO.delete(entityType, id);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return crudDAO.findAll(this.entityType);
	}

	@Override
	public List<T> findAllOrderBy(String orderBy, String order) {
		return crudDAO.findAllOrderBy(this.entityType, orderBy, order);
	}

	/**
	 * 
	 * @param className
	 * @param criterion
	 * @return
	 */
	@Override
	public List<T> findByCriteria(Class<T> className, final Criterion... criterion) {
		return crudDAO.findByCriteria(className, criterion);
	}

	/**
	 * 
	 * @param className
	 * @param firstResult
	 * @param maxResults
	 * @param criterion
	 * @return
	 */
	@Override
	public List<T> findByCriteria(Class<T> className, final int firstResult, final int maxResults,
			final Criterion... criterion) {
		return crudDAO.findByCriteria(className, firstResult, maxResults, criterion);
	}

	@Transactional(readOnly = true)
	public T find(PK id) {
		Assert.notNull(id, "Object id can not be null");
		return crudDAO.find(entityType, id);
	}

	@Transactional(readOnly = true)
	public List<T> findRangeResultsWithNamedQuery(String queryName, int startIndex, int endIndex,
			Map<String, Object> params) {
		return crudDAO.findRangeResultsWithNamedQuery(queryName, startIndex, endIndex, params);
	}

	@Transactional(readOnly = true)
	public T findUniqueWithNamedQuery(String queryName, Map<String, Object> params) {
		return crudDAO.findUniqueWithNamedQuery(queryName, params);
	}

	@Transactional(readOnly = true)
	public T findUniqueWithNamedQuery(String queryName) {
		return crudDAO.findUniqueWithNamedQuery(queryName);
	}

	@Transactional(readOnly = true)
	public List<T> findWithNamedQuery(String queryName) {
		return crudDAO.findWithNamedQuery(queryName);
	}

	@Transactional(readOnly = true)
	public List<T> findWithNamedQuery(String queryName, Map<String, Object> params) {
		return crudDAO.findWithNamedQuery(queryName, params);
	}

	@Transactional(readOnly = true)
	public int deleteWithNamedQuery(String queryName, Map<String, Object> params) {
		return crudDAO.deleteWithNamedQuery(queryName, params);
	}

	public long count() {
		return crudDAO.count(entityType);
	}

	public long count(String queryName, Map<String, Object> params) {
		return crudDAO.count(queryName, params);
	}

	public void enableFilter(String filtre, Map<String, Object> parameters) {
		crudDAO.enableFilter(filtre, parameters);
	}

	public void enableFilter(String filtre, String paramName, Object param) {
		crudDAO.enableFilter(filtre, paramName, param);
	}

	public Criteria getCriteria(Class<T> classe) {
		return crudDAO.getCriteria(classe);
	}

	@SuppressWarnings("unchecked")
	public T merge(T t) {
		return (T) this.crudDAO.getSession().merge(t);
	}

	/**
	 * Get next val on a specifc database sequence
	 */
	public BigInteger getSeqNextVal(String sequence) {
		return this.crudDAO.getSeqNextVal(sequence);
	}

	/**
	 * Get current val for a specific database sequence (current val of sequence but
	 * not for current transaction)
	 * 
	 * @param sequence
	 * @return
	 */
	public BigInteger getSeqCurrentVal(String sequence) {
		return this.crudDAO.getSeqCurrentVal(sequence);
	}

	/**
	 * @return the entityType
	 */
	public Class<T> getEntityType() {
		return entityType;
	}

	/**
	 * @param crudDAO
	 *            the crudDAO to set
	 */
	public void setCrudDAO(final CrudServiceDAO crudDAO) {
		this.crudDAO = crudDAO;
	}

	/**
	 * @param validator
	 *            the validator to set
	 */
	public void setValidator(final Validator validator) {
		this.validator = validator;
	}

	@Override
	public List<Object> executeNativeQuery(String nativeQuery) {
		return this.crudDAO.executeNativeQuery(nativeQuery);
	}

	@Override
	public List<Object> executeNativeQuery(String nativeQuery, Map<String, Object> parameters) {
		return this.crudDAO.executeNativeQuery(nativeQuery, parameters);
	}

	@Override
	public int executeCUDNativeQuery(String nativeQuery, Map<String, Object> parameters) {
		return this.crudDAO.executeCUDNativeQuery(nativeQuery, parameters);
	}

	@Override
	public List<Object> executeNativeGeometricQuery(String nativeQuery, Map<String, Object> parameters,
			List<HashMap<String, Object>> metadatas) {
		return this.crudDAO.executeNativeGeometricQuery(nativeQuery, parameters, metadatas);
	}

	@Override
	public List<HashMap<String, Object>> getColumnsMetadata(String schema, String tableName) {
		return this.crudDAO.getColumnsMetadata(schema, tableName);
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> List<Object> findDistinctByColumn(Class<T> className, String column) {
		return this.crudDAO.findDistinctByColumn(className, column);
	}

}