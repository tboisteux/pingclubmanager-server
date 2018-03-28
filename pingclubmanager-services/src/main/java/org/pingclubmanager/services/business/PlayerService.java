package org.pingclubmanager.services.business;

import java.util.List;

import org.pingclubmanager.domain.model.application.Player;
import org.pingclubmanager.services.bo.PlayerBO;

public interface PlayerService {

	public List<PlayerBO> findAllPlayers();

	public PlayerBO findPlayer(Integer id);

	public PlayerBO createPlayer(PlayerBO playerBo);

	public PlayerBO updatePlayer(Integer id, PlayerBO playerBo);

	public void deletePlayer(Integer id);

	public PlayerBO mapToBo(Player player);

	public Player mapToEntity(PlayerBO playerBo);

}
