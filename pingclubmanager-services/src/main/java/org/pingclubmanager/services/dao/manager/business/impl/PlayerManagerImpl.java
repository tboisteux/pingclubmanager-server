package org.pingclubmanager.services.dao.manager.business.impl;

import java.io.Serializable;

import org.pingclubmanager.domain.model.application.Player;
import org.pingclubmanager.services.dao.manager.business.PlayerManager;
import org.pingclubmanager.services.dao.manager.defaultManager.impl.DefaultManagerImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Service
public class PlayerManagerImpl extends DefaultManagerImpl<Player, Serializable> implements PlayerManager {

}
