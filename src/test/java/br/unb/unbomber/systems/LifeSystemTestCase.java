package br.unb.unbomber.systems;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.AvailableTries;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.core.Component;
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
	 * Testa update do system de modo geral.
	 * 
	 * @result Passa no teste se o valor retornado e TRUE.
	 */
	@Test
	public void testUpdate(){
		system.update();
		assertTrue(true);
	}

	/**
	 * Testa uma colisao que nao deveria ocorrer dano.
	 * 
	 * @result Passa no teste se o valor retornado e TRUE.
	 */
	@Test
	public void noDecrementHealthTest() {

		/** Criacao das entidades. */
		Entity entity1 = entityManager.createEntity();
		Entity entity2 = entityManager.createEntity();
		
		/** Atribui os componentes básicos de vida das entidades. */
		entity1.addComponent(new Health(1,true));
		entity2.addComponent(new Health(1,true));
		entity1.addComponent(new AvailableTries(3,true));
		entity2.addComponent(new AvailableTries(3,true));

		/** Inicia os componentes de atribuicao de tipos das entidades. */
		entity1.addComponent(new LifeType(Type.CHAR));
		entity2.addComponent(new LifeType(Type.CHAR));
		
		/** Atualiza as entidades com os componentes atribuídos. */
		entityManager.update(entity1);
		entityManager.update(entity2);

		/** Cria evento de colisao entre as entidades Char e Char */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());
		
		/** Adiciona o evento de colisão na lista de Eventos. */
		entityManager.addEvent(collEvent);
		
		/** Roda o sistema. */
		system.update();
		
		/** Coleta a vida da entidade que sofreu a colisão. */
		Health entHealth = (Health) entityManager
				.getComponent(Health.class, collEvent.getTargetId());
		
		// AvailableTries entLifes = (AvailableTries) entityManager
				// .getComponent(AvailableTries.class, collEvent.getTargetId());
		
		/** Verifica se não foi retirado vida da entidade após a colisão. */
		assertEquals(1, entHealth.getLifeEntity());
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
		
		/** Atribui os componentes básicos de vida das entidades. */
		entity1.addComponent(new Health(1,true));
		entity2.addComponent(new Health(1,true));
		entity1.addComponent(new AvailableTries(3,true));
		entity2.addComponent(new AvailableTries(3,true));

		/** Inicia os componentes de atribuicao de tipos das entidades. */
		entity1.addComponent(new LifeType(Type.CHAR));
		entity2.addComponent(new LifeType(Type.MONSTER));
		
		/** Atualiza as entidades com os componentes atribuídos. */
		entityManager.update(entity1);
		entityManager.update(entity2);

		/** Cria evento de colisao entre as entidades Char e Char */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());
		
		/** Adiciona o evento de colisão na lista de Eventos. */
		entityManager.addEvent(collEvent);
		
		/** Roda o sistema. */
		system.update();
		
		/** Coleta a vida da entidade que sofreu a colisão. */
		Health entHealth = (Health) entityManager
				.getComponent(Health.class, collEvent.getTargetId());
		
		AvailableTries entLifes = (AvailableTries) entityManager
				.getComponent(AvailableTries.class, collEvent.getTargetId());
		
		assertEquals(2, entLifes.getLifeTries());
		/** Verifica se foi retirado vida da entidade após a colisão. */
		assertEquals(0, entHealth.getLifeEntity());
	
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
