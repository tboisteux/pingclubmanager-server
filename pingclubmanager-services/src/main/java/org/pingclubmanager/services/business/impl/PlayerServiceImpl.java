package org.pingclubmanager.services.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.pingclubmanager.domain.model.application.Player;
import org.pingclubmanager.services.bo.PlayerBO;
import org.pingclubmanager.services.business.PlayerService;
import org.pingclubmanager.services.dao.manager.business.PlayerManager;
import org.pingclubmanager.utils.helper.DTOMapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	PlayerManager playerManager;

	@Override
	public List<PlayerBO> findAllPlayers() {
		List<PlayerBO> playersBO = new ArrayList<>();
		List<Player> players = playerManager.findAll();
		players.forEach(player -> playersBO.add(this.mapToBo(player)));
		return playersBO;
	}

	@Override
	public PlayerBO findPlayer(Integer id) {
		Player player = playerManager.find(id);
		PlayerBO playerBO = this.mapToBo(player);
		return playerBO;
	}

	@Override
	public PlayerBO createPlayer(PlayerBO playerBo) {
		Player player = this.mapToEntity(playerBo);
		PlayerBO newPlayerBo = new PlayerBO();

		try {
			playerManager.create(player);
			newPlayerBo = this.mapToBo(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newPlayerBo;
	}

	@Override
	public PlayerBO updatePlayer(Integer id, PlayerBO playerBo) {
		Player player = this.mapToEntity(playerBo);
		PlayerBO updatedPlayerBo = new PlayerBO();

		try {
			playerManager.update(player);
			updatedPlayerBo = this.mapToBo(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedPlayerBo;
	}

	@Override
	public void deletePlayer(Integer id) {
		Player player = playerManager.find(id);

		try {
			playerManager.delete(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PlayerBO mapToBo(Player player) {
		PlayerBO playerBo = new PlayerBO();
		DTOMapperHelper.WithType(Player.class, PlayerBO.class).copy(player, playerBo);
		return playerBo;
	}

	@Override
	public Player mapToEntity(PlayerBO playerBo) {
		Player player = new Player();
		DTOMapperHelper.WithType(PlayerBO.class, Player.class).copy(playerBo, player);
		return player;
	}

}
