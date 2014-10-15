package br.unb.unbomber.core;

import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;

/**
 * EntitySystemImpl is a simple EntityManager implementation.
 * 
 * WARN: EntitySystemImpl was removed and deprecated in favor of 
 * EntityManagerImpl, please, update your code. This class 
 * will be removed in a future update.
 * 
 * @author grodrigues
 *
 */
@Deprecated
public class EntitySystemImpl {

	@Deprecated
	public static EntityManager getInstance() {
		return EntityManagerImpl.getInstance();
	}

	public static void init() {
		EntityManagerImpl.init();	
	}

}
