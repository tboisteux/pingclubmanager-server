package org.pingclubmanager.rest.controller.business;

import java.util.List;

import org.pingclubmanager.services.bo.PlayerBO;
import org.pingclubmanager.services.business.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Rest Controller Allow to provide a default response for base API URL
 * 
 * @author Tony Boisteux
 *
 */
@RestController
@RequestMapping("/players")
public class PlayerRestController {

	@Autowired
	PlayerService playerService;

	/**
	 * Returns all Player
	 * 
	 * @return List<PlayerBO>
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<PlayerBO>> findAllPlayers() {
		List<PlayerBO> playersBo = playerService.findAllPlayers();
		return new ResponseEntity<>(playersBo, HttpStatus.ACCEPTED);
	}

	/**
	 * Returns one Player by his ID
	 * 
	 * @return PlayerBO
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PlayerBO> findPlayerById(@PathVariable Integer id) {
		PlayerBO playerBo = playerService.findPlayer(id);
		return new ResponseEntity<>(playerBo, HttpStatus.ACCEPTED);
	}
}
