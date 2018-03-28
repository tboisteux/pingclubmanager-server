package org.pingclubmanager.services.dao.manager.defaultManager;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.pingclubmanager.services.dao.crud.CrudServiceDAO;

/**
 * Service générique implémentant les opérations redondantes (CRUD) communes à
 * tous les <code>Manager</code>.
 * 
 * @author lguerin
 */
public interface DefaultManager<T, PK extends Serializable> {
	/**
	 * Créé une nouvelle instance.
	 * 
	 * @param <T>
	 *            Type de l'objet a creer
	 * @param newInstance
	 *            Instance a créer
	 * @throws Exception
	 */
	void create(T t) throws Exception;

	/**
	 * Met a jour l'objet transmis.
	 * 
	 * @param <T>
	 *            Type de l'objet a mettre a jour
	 * @param t
	 *            Objet a mettre a jour
	 * @return l'objet persiste
	 * @throws Exception
	 */
	T update(T t) throws Exception;

	/**
	 * Supprime l'objet identifie par la cle primaire.
	 * 
	 * @param <T>
	 * @param <PK>
	 * @param type
	 *            , entity class type
	 * @param id
	 */
	void delete(PK id);

	/**
	 * Retrouve un objet par cle primaire.
	 * 
	 * @param <T>
	 *            Type de l'objet retourné
	 * @param <PK>
	 *            Identifiant de l'objet
	 * @param type
	 *            Type de l'objet a rechercher
	 * @param id
	 *            Clé primaire
	 * @return the object null if not found
	 */
	T find(PK id);

	/**
	 * Recupere un nombre maximum de resultats, a partir d'une requete nommee et
	 * parametres.
	 * 
	 * @param <T>
	 *            Type des objets a retourner
	 * @param queryName
	 *            Identifiant de la requere nommee
	 * @param startIndex
	 *            Index de départ
	 * @param endIndex
	 *            Index de fin
	 * @param params
	 *            Parametres de la requete
	 * @return
	 */
	List<T> findRangeResultsWithNamedQuery(String queryName, int startIndex, int endIndex, Map<String, Object> params);

	/**
	 * Récupére un resultat pour <code>T</code> unique à l'aide d'une query nommée
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	T findUniqueWithNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * Retourne le resultat d'une namequery sans paramètre pour l'entité
	 * <code>T</code>
	 * 
	 * @param queryName
	 * @return
	 */
	List<T> findWithNamedQuery(String queryName);

	/**
	 * Retourne le resultat d'une namequery avec paramètre pour l'entité
	 * <code>T</code>
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	List<T> findWithNamedQuery(String queryName, Map<String, Object> params);

	int deleteWithNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * Dénombre le nombre d'entités de type <code>T</code>.
	 * 
	 * @return le nombre d'entités de type <code>T</code>
	 */
	long count();

	/**
	 * Dénombre le nombre d'entités de type <code>T</code> répondant aux critères
	 * <code>params</code>.
	 * 
	 * @param queryName
	 *            Requête nommée
	 * @param params
	 *            Paramètres de la requête
	 * @return le nombre d'entités de type <code>T</code>
	 */
	long count(String queryName, Map<String, Object> params);

	/**
	 * Permet d'activer un filtre de récupération présent sur une entité
	 * 
	 * @param filtre
	 * @param parameters
	 */
	void enableFilter(String filtre, Map<String, Object> parameters);

	/**
	 * Permet d'activer un filtre de récupération présent sur une entité
	 * 
	 * @param filtre
	 * @param paramName
	 * @param param
	 */
	void enableFilter(String filtre, String paramName, Object param);

	/**
	 * Ratache un object a la session
	 * 
	 * @param t
	 *            l'objet a ratacher
	 * @return l'objet rataché
	 */
	T merge(T t);

	/**
	 * Get NextVal from sequence
	 * 
	 * @param sequence
	 * @return NextVal value
	 */
	BigInteger getSeqNextVal(String sequence);

	/**
	 * Get Native Query from nativeQuery
	 * 
	 * @param nativeQuery
	 * @return Native Query value
	 */
	Object executeNativeQuery(String nativeQuery);

	/**
	 * Execute Native Query from nativeQuery with parameters
	 * 
	 * @param nativeQuery
	 * @return Native Query value
	 */
	List<Object> executeNativeQuery(String nativeQuery, Map<String, Object> parameters);

	/**
	 * Execute Insert Native Query from nativeQuery with parameters
	 * 
	 * @param nativeQuery
	 * @return Query result
	 */
	int executeCUDNativeQuery(String nativeQuery, Map<String, Object> parameters);

	/**
	 * Execute native query using Hibernate session and mapping the_geom as
	 * GeometryType
	 * 
	 * @param nativeQuery
	 * @param parameters
	 * @return
	 */
	List<Object> executeNativeGeometricQuery(String nativeQuery, Map<String, Object> parameters,
			List<HashMap<String, Object>> metadatas);

	/**
	 * Get all results for <code>T</code> entity
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * 
	 * @param className
	 * @param criterion
	 * @return
	 */
	List<T> findByCriteria(Class<T> className, final Criterion... criterion);

	/**
	 * 
	 * @param className
	 * @param firstResult
	 * @param maxResults
	 * @param criterion
	 * @return
	 */
	List<T> findByCriteria(Class<T> className, final int firstResult, final int maxResults,
			final Criterion... criterion);

	/**
	 * 
	 * @return
	 */
	Class<T> getEntityType();

	Criteria getCriteria(Class<T> classe);

	/**
	 * Get all results for <code>T</code> entity ordered by <code>orderBy</code>
	 * 
	 * @return
	 */
	List<T> findAllOrderBy(String orderBy, String order);

	/**
	 * @param crudDAO
	 *            the crudDAO to set
	 */
	void setCrudDAO(CrudServiceDAO crudDAO);

	/**
	 * @param validator
	 *            the validator to set
	 */
	void setValidator(Validator validator);

	/**
	 * * Get Columns metadatas for a specific table
	 * 
	 * @param tableName
	 * @return column information
	 */
	List<HashMap<String, Object>> getColumnsMetadata(String schema, String tableName);

	/**
	 * Get all distinct entries from entity by columName
	 * 
	 * @param className
	 * @param columnName
	 * @return
	 */
	@SuppressWarnings("hiding")
	<T> List<Object> findDistinctByColumn(Class<T> className, String column);

}