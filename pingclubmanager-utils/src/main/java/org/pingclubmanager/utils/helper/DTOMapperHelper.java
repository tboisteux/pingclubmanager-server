package org.pingclubmanager.utils.helper;

import org.pingclubmanager.utils.exception.PcmTechnicalException;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.assembler.Assembler;
import com.inspiresoftware.lib.dto.geda.assembler.DTOAssembler;

/**
 * Provides helper to copy Bean, DTO or business object Uses GeDA Library
 * 
 * @see <a href=
 *      "http://www.inspire-software.com/confluence/display/GeDA/Getting+started">GeDA
 *      documentation</a>
 * @author jke
 *
 * @param <T>
 *            ToEl
 * @param <E>
 *            From El
 */
public class DTOMapperHelper<T, E> {

	Class<E> entityTypeFromEl;
	Class<T> entityTypetoEl;

	/**
	 * 
	 * @param entityTypeFromEl
	 * @param entityTypeToEl
	 */
	protected DTOMapperHelper(Class<E> entityTypeFromEl, Class<T> entityTypeToEl) {
		this.entityTypeFromEl = entityTypeFromEl;
		this.entityTypetoEl = entityTypeToEl;
	}

	/**
	 * Prepare to copy bean from Type <E> to type <T>
	 * 
	 * @param entityTypeFromEl
	 * @param entityTypeToEl
	 * @return DTOMapperHelper<T, E> New Mapper
	 */
	public static <T, E> DTOMapperHelper<T, E> WithType(Class<E> entityTypeFromEl, Class<T> entityTypeToEl) {
		return new DTOMapperHelper<T, E>(entityTypeFromEl, entityTypeToEl);

	}

	/**
	 * Copy entity from to entity To using mapper
	 * 
	 * @param fromEl
	 * @param toEl
	 * @return T Entity To
	 */
	public T copy(E fromEl, T toEl) {
		if (fromEl == null || toEl == null)
			throw new PcmTechnicalException("Error geda mapping: entity is null");
		// Create or take in cache an assembler
		final Assembler asm = DTOAssembler.newAssembler(this.entityTypetoEl, this.entityTypeFromEl);
		asm.assembleDto(toEl, fromEl, null, null);
		return toEl;
	}

	/**
	 * Copy entity from to entity To using mapper
	 * 
	 * @param fromEl
	 * @param toEl
	 * @param beanFactory
	 * @return T Entity To
	 */
	public T copy(E fromEl, T toEl, BeanFactory beanFactory) {
		// Create or take in cache an assembler
		final Assembler asm = DTOAssembler.newAssembler(this.entityTypetoEl, this.entityTypeFromEl);
		asm.assembleDto(toEl, fromEl, null, beanFactory);
		return toEl;
	}

}
