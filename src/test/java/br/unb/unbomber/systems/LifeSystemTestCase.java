package br.unb.unbomber.systems;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.CollisionEvent;

/**
 * Classe de testes do LifeSystem do M�dulo Life.
 * 
 * @version 0.2 21 Out 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class LifeSystemTestCase {

	/** Gerenciador das entidades. */
	EntityManager entityManager;

	/** Sistema de controle do Modulo Life. */
	LifeSystem system;

	@Before
	public void setUp() throws Exception {

		/** Inicia um sistema para cada caso de teste */
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		system = new LifeSystem(entityManager);
	}

	/**
	 * Testa uma colisao que nao deveria ocorrer dano.
	 * 
	 * @result Passa no teste se o valor retornado e FALSE.
	 */
	@Test
	public void noDecrementHealthTest() {

		/** Criacao das entidades. */
		Entity entity1 = entityManager.createEntity();
		Entity entity2 = entityManager.createEntity();

		/** Inicia os componentes de atribuicao de tipos das entidades. */
		entity1.addComponent(new LifeType(Type.CHAR));
		entityManager.update(entity1);
		entity2.addComponent(new LifeType(Type.CHAR));
		entityManager.update(entity2);

		/** Cria evento de colisao entre as entidades Char e Char */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());

		/**
		 * Adiciona este evento ao LifeSystem e recebe se foi retirado dano da
		 * colisao simulada.
		 */
		boolean isDamage = system.isLifeDamage(collEvent);

		assertFalse(isDamage);
	}

	/**
	 * Testa uma colisao que deveria ocorrer dano.
	 *
	 * @result Passa no teste se o valor retornado e TRUE.
	 */
	@Test
	public void decrementHealthTest() {

		/** Criacao das entidades. */
		Entity entity1 = entityManager.createEntity();
		Entity entity2 = entityManager.createEntity();

		/** Inicia os componentes de atribuicao de tipos das entidades. */
		entity1.addComponent(new LifeType(Type.MONSTER));
		entityManager.update(entity1);
		entity2.addComponent(new LifeType(Type.CHAR));
		entityManager.update(entity2);

		/** Cria evento de colisao entre as entidades Char e Monster */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());

		/**
		 * Adiciona este evento ao LifeSystem e recebe se foi retirado dano da
		 * colisao simulada.
		 */
		boolean isDamage = system.isLifeDamage(collEvent);

		assertTrue(isDamage);
	}

	/**
	 * Testa se quando ocorrer a colisao de Char e Power Up de incremento de
	 * vida entao esta entidade tem sua vida incrementada.
	 * 
	 * @result
	 */
	@Test
	public void increaseHealthTest() {

	}

	/**
	 * Testa a destruicao de uma entidade Char ou Monster quando sua vida chega
	 * a zero.
	 * 
	 * @result
	 */
	@Test
	public void destroyifHealthZeroTest() {

	}

	/**
	 * Testa se quando uma entidade Char, apos perder uma vida e possuir vidas
	 * restantes, e recriado na celula inicial e com invecibilidade.
	 * 
	 * @result
	 */
	@Test
	public void recreateIfHasMoreTriesTest() {

	}

	/**
	 * Testa se o jogo acaba caso um Char nao possuir mais vidas.
	 * 
	 * @result
	 */
	@Test
	public void gameOverIfHasNoMoreTriesTest() {

	}
}
