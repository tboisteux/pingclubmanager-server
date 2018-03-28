package org.pingclubmanager.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe de gestion des Exceptions techniques
 * Loguer les exceptions techniques, par exemple erreur de connexion à la base de données
 * @author jke
 *
 */
public class PcmTechnicalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(PcmTechnicalException.class);

	public PcmTechnicalException(String message) {

		super(message);
		logger.error(message);
	}
	
	public PcmTechnicalException(String message, Throwable eSource) {
		super(message, eSource);
		
		logger.error(message, eSource);
		
	}
	
}
