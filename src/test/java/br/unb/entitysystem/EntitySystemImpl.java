package br.unb.entitysystem;

import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;

/**
 * EntitySystemImpl is a simple EntityManager implementation.
 * 
 * WARN: EntitySystemImpl was removed and deprecated in favor of 
 * EntityManagerImpl, please, update your code. This class 
 * will be removed in a future update.
 * 
 * @author Gabriel Rodrigues <gabrielsr@gmail.com>
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
