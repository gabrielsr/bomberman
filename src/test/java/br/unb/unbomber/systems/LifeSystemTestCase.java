package br.unb.unbomber.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;
import br.unb.unbomber.component.AvailableTries;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.InAnExplosionEvent;

/**
 * Classe de testes do LifeSystem do Módulo Life.
 * 
 * @version 0.3 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
public class LifeSystemTestCase {

	/** Gerenciador das entidades. */
	EntityManager entityManager;

	/** Sistema de controle do Modulo Life. */
	LifeSystem system;

	@Before
	public void setUp() throws Exception {

		/** Inicia um sistema para cada caso de teste. */
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
	public void testUpdate() {
		system.update();
		assertTrue(true);
	}

	/**
	 * Testa uma colisao que nao deveria ocorrer dano.
	 * 
	 * @result Passa no teste se retornar True para a igualdade.
	 */
	@Test
	public void noDecrementHealthTest() {

		/** Criacao das entidades. */
		Entity entity1 = createEntity(2, 3, 0, 0, Type.CHAR);
		Entity entity2 = createEntity(2, 3, 0, 0, Type.CHAR);

		/** Cria evento de colisao entre as entidades Char e Char. */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());

		/** Adiciona o evento de colisão na lista de Eventos. */
		entityManager.addEvent(collEvent);

		/** Roda o sistema. */
		system.update();

		/** Coleta a vida da entidade que sofrerá dano. */
		Health entHealth = (Health) entityManager.getComponent(Health.class,
				collEvent.getTargetUuid());

		/** Verifica se não foi retirado vida da entidade após a colisão. */
		assertEquals(2, entHealth.getLifeEntity());
	}

	/**
	 * Testa uma colisao que deveria ocorrer dano.
	 *
	 * @result Passa no teste se retornar True para a igualdade.
	 */
	@Test
	public void decrementHealthTest() {
		/** Criacao das entidades. */
		Entity entity1 = createEntity(2, 3, 0, 0, Type.CHAR);
		Entity entity2 = createEntity(2, 3, 0, 0, Type.MONSTER);

		/** Cria evento de colisao entre as entidades Char e Monster. */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());

		/** Adiciona o evento de colisão na lista de Eventos. */
		entityManager.addEvent(collEvent);

		/** Roda o sistema. */
		system.update();

		/** Coleta a vida da entidade que sofrerá dano, no caso a ent CHAR. */
		Health entHealth = (Health) entityManager.getComponent(Health.class,
				collEvent.getSourceUuid());

		/** Verifica se não foi retirado vida da entidade após a colisão. */
		assertEquals(1, entHealth.getLifeEntity());

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
	 * Testa a destruicao de uma entidade Char após Monster colidir com a
	 * entidade.
	 * 
	 * @result Passa no teste se retornar True para a igualdade.
	 */
	@Test
	public void destroyCharIfHealthZeroTest() {
		/** Criacao das entidades. */
		Entity entity1 = createEntity(1, 1, 0, 0, Type.MONSTER);
		Entity entity2 = createEntity(1, 3, 0, 0, Type.CHAR);

		/** Cria evento de colisao entre as entidades Monster e Char. */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());

		/** Adiciona o evento de colisão na lista de Eventos. */
		entityManager.addEvent(collEvent);

		/** Roda o sistema. */
		system.update();

		/**
		 * Coleta as tentativas de vida da entidade que sofrerá dano, no caso a
		 * entidade CHAR.
		 */
		AvailableTries entLifes = (AvailableTries) entityManager.getComponent(
				AvailableTries.class, collEvent.getTargetUuid());

		/**
		 * Verifica se foi retirado tentativa de vida da entidade após a
		 * explosão.
		 */
		assertEquals(2, entLifes.getLifeTries());
	}
	
	/**
	 * Testa a remoção de uma entidade Char após Monster colidir com a
	 * entidade e ele não possuir vidas remanescentes.
	 * 
	 * @result Passa no teste se retornar True para a igualdade.
	 */
	@Test
	public void removeCharIfZeroTriesTest() {
		/** Criacao das entidades. */
		Entity entity1 = createEntity(1, 1, 0, 0, Type.MONSTER);
		Entity entity2 = createEntity(1, 0, 0, 0, Type.CHAR);

		/** Cria evento de colisao entre as entidades Monster e Char. */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());

		/** Adiciona o evento de colisão na lista de Eventos. */
		entityManager.addEvent(collEvent);

		/** Roda o sistema. */
		system.update();

		/**
		 * Coleta as tentativas de vida da entidade que sofrerá dano, no caso a
		 * entidade CHAR.
		 */
		AvailableTries entLifes = (AvailableTries) entityManager.getComponent(
				AvailableTries.class, collEvent.getTargetUuid());

		/**
		 * Verifica se foi retirado tentativa de vida da entidade após a
		 * explosão.
		 */
		assertEquals(null, entLifes);
	}

	/**
	 * Testa a destruicao de uma entidade Monster por uma Bomba.
	 * 
	 * @result Passa no teste se retornar True.
	 */
	@Test
	public void destroyMonsterIfHealthZeroTest() {
		/** Criacao das entidades. */
		Entity entity1 = createEntity(1, 3, 0, 0, Type.CHAR);
		Entity entity2 = entityManager.createEntity();
		Entity entity3 = createEntity(1, 3, 0, 0, Type.MONSTER);

		/** Inicia o componente de atribuição de tipo da entidade Bomba. */
		entity2.addComponent(new LifeType(Type.BOMB));

		/** Atualiza a entidade com o componente atribuído. */
		entityManager.update(entity2);

		/** Cria um evento de explosão da bomba com o monstro. */
		InAnExplosionEvent explosion = new InAnExplosionEvent();
		explosion.setOwnerId(entity1.getEntityId());
		explosion.setHitUuid(entity3.getEntityId());

		/** Adiciona o evento de explosão na lista de Eventos. */
		entityManager.addEvent(explosion);

		/**
		 * Roda o sistema duas vezes para primeiro gerar o evento de destruição
		 * e depois tratá-lo.
		 */
		system.update();
		system.update();

		/**
		 * Coleta as tentativas de vida da entidade que sofrerá dano, no caso a
		 * entidade MONSTER.
		 */
		AvailableTries entLifes = (AvailableTries) entityManager.getComponent(
				AvailableTries.class, explosion.getHitUuid());

		/**
		 * Se o componente de tentativas de vida do MONSTER retornar null é
		 * porque a entidade foi destruída após a explosão da bomba, logo passou
		 * no teste. Caso contrário será retornado falso e não passará no teste.
		 */
		assertTrue (entLifes == null);

	}

	/**
	 * Testa se quando uma entidade Char, apos perder uma vida e possuir vidas
	 * restantes, e recriado na celula inicial e com invecibilidade.
	 * 
	 * @result Passa no teste se retornar True.
	 */
	@Test
	public void recreateIfHasMoreTriesTest() {
		/** Criacao das entidades. */
		Entity entity1 = createEntity(1, 1, 0, 0, Type.MONSTER);
		Entity entity2 = createEntity(1, 3, 10, 15, Type.CHAR);

		/** Cria evento de colisao entre as entidades Monster e Char. */
		CollisionEvent collEvent = new CollisionEvent(entity1.getEntityId(),
				entity2.getEntityId());

		/** Adiciona o evento de colisão na lista de Eventos. */
		entityManager.addEvent(collEvent);

		/** Roda o sistema. */
		system.update();

		/** Coleta o local no grid da entidade que fora destruída. */
		Position entCell = (Position) entityManager.getComponent(
				Position.class, collEvent.getTargetUuid());
		int cX = entCell.getCellX();
		int cY = entCell.getCellY();

		/**
		 * Verifica se a entidade foi recriada na célula inicial do grid e
		 * asserta true. Caso contrário não irá passar no teste.
		 */
		assertTrue (cX == 0 && cY == 0);

	}

	/**
	 * Testa se o jogo acaba caso um Char nao possuir mais vidas.
	 * 
	 * @result
	 */
	@Test
	public void gameOverIfHasNoMoreTriesTest() {

	}


	private Entity createEntity(int health, int avTries, int cellx, int celly,
			Type t) {
		/** Criacao da entidade. */
		Entity entity = entityManager.createEntity();

		/** Atribui os componentes básicos da entidade. */
		entity.addComponent(new Health(health, true));
		entity.addComponent(new AvailableTries(avTries, true));
		Position cellP = new Position();
		cellP.setCellX(cellx);
		cellP.setCellY(celly);
		entity.addComponent(cellP);

		/** Inicia os componentes de atribuição de tipos da entidade. */
		entity.addComponent(new LifeType(t));

		/** Atualiza a entidade com os componentes atribuídos. */
		entityManager.update(entity);

		return entity;
	}
}
