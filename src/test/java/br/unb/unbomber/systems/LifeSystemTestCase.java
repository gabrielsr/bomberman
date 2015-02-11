package br.unb.unbomber.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;
import net.mostlyoriginal.api.event.common.EventManager;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.AvailableTries;
import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.event.CollisionEvent;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.EntityBuilder;



/**
 * Classe de testes do LifeSystem do Módulo Life.
 * 
 * 
 * @author Breno Xavier - brenoxp2008
 */
public class LifeSystemTestCase {
	
	LifeSystem lifeSystem;
	
	World world;
	
	@Before
	public void setUp() throws Exception {
		lifeSystem = new LifeSystem();
		
		world = new World();
		world.setSystem(lifeSystem);
		
		world.setManager(new EventManager());
		world.setManager(new UuidEntityManager());
		
	}
	
	/**
	 * Testa update do system de modo geral.
	 * 
	 * @result Passa no teste se o valor retornado e TRUE.
	 */
	@Test
	public void testUpdate() {
		lifeSystem.process();
		assertTrue(true);
	}
	
	
	/**
	 * Testa uma colisao que nao deveria ocorrer dano.
	 * 
	 * @result Passa no teste se retornar True para a igualdade.
	 */
	@Test
	public void noDecrementHealthTest() {
		world.initialize();
		
		Health health = new Health();
		health.setCanTakeDamaged(true);
		health.setLifeEntity(1);
		
		/** Criacao das entidades. */
		Entity entity1 = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), health, new LifeType(Type.CHAR)).build();
		
		Entity entity2 = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), new LifeType(Type.CHAR)).build();
		
		//Cria evento de colisão
		world.setSystem(new VoidEntitySystem() {
			
			@Wire
			EventManager em;
			
			@Override
			protected void processSystem() {
				final CollisionEvent actionCommand = new CollisionEvent(entity1.getUuid(), entity2.getUuid());
				
				em.dispatch(actionCommand);
			}
		});
		
		world.process();
		
		//Compara os lifes, antes e depois da colisão
		assertEquals(1, entity1.getComponent(Health.class).getLifeEntity());
	}
	
	/**
	 * Testa uma colisao que deveria ocorrer dano.
	 *
	 * @result Passa no teste se retornar True para a igualdade.
	 */
	@Test
	public void decrementHealthTest() {
		world.initialize();
		
		//Cria lifes para serem colocados nas entidades
		Health health1 = new Health();
		health1.setCanTakeDamaged(true);
		health1.setLifeEntity(2);
		
		/** Criacao das entidades. */
		Entity entity1 = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), health1, new LifeType(Type.CHAR)).build();
		
		Entity entity2 = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), new LifeType(Type.MONSTER)).build();
		
		//Cria evento de colisão
		world.setSystem(new VoidEntitySystem() {
			
			@Wire
			EventManager em;
			
			@Override
			protected void processSystem() {
				final CollisionEvent actionCommand = new CollisionEvent(entity1.getUuid(), entity2.getUuid());
				
				em.dispatch(actionCommand);
			}
		});
		
		world.process();
		
		//Verifica se life foi decrementado depois da colisão.
		assertEquals(1, health1.getLifeEntity());
	}
	
	/**
	 * Testa se quando ocorrer a colisao de Char e Power Up de incremento de
	 * vida entao esta entidade tem sua vida incrementada.
	 * 
	 * @result
	 */
	@Test
	public void increaseHealthTest() {
		world.initialize();
		
		//Cria lifes para serem colocados nas entidades
		Health health = new Health();
		health.setCanTakeDamaged(true);
		health.setLifeEntity(2);
		
		/** Criacao da entidade. */
		Entity entity = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), health, new LifeType(Type.CHAR)).build();
		
		/** Criacao do PowerUp HEALTHUP. */
		Entity life = new EntityBuilder(world).with(new Position(0, 0),
				new PowerUp(PowerType.HEALTHUP)).build();
		
		//Cria evento de colisão
		world.setSystem(new VoidEntitySystem() {
			
			@Wire
			EventManager em;
			
			@Override
			protected void processSystem() {
				final CollisionEvent actionCommand = new CollisionEvent(entity.getUuid(), life.getUuid());
				
				em.dispatch(actionCommand);
			}
		});
		
		world.process();
		
		//Verifica se life foi incrementado depois da colisão.
		assertEquals(3, health.getLifeEntity());
	}
	
	/**
	 * Testa a destruicao de uma entidade Char após Monster colidir com a
	 * entidade.
	 * 
	 * @result Passa no teste se retornar True para a igualdade.
	 */
	@Test
	public void destroyCharIfHealthZeroTest() {
		
		world.initialize();
		
		//Cria lifes para serem colocados nas entidades
		Health health1 = new Health();
		health1.setCanTakeDamaged(true);
		health1.setLifeEntity(1);

		/** Criacao das entidades. */
		Entity entity1 = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), health1, new LifeType(Type.CHAR), new AvailableTries(0,true)).build();
		
		Entity entity2 = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), new LifeType(Type.MONSTER)).build();

		/** Cria evento de colisao entre as entidades Monster e Char. */
		world.setSystem(new VoidEntitySystem() {
			
			@Wire
			EventManager em;
			
			@Override
			protected void processSystem() {
				final CollisionEvent actionCommand = new CollisionEvent(entity1.getUuid(), entity2.getUuid());
				
				em.dispatch(actionCommand);
			}
		});

		/** Roda o sistema. */
		world.process();
		
		Assert.assertEquals(0, health1.getLifeEntity());
		
	}
	
	/**
	 * Testa se quando uma entidade Char, apos perder uma vida e possuir vidas
	 * restantes continua ativa
	 * 
	 * @result Passa no teste se retornar True.
	 */
	@Test
	public void recreateIfHasMoreTriesTest() {
		world.initialize();
		
		Health health = new Health();
		health.setLifeEntity(1);
		health.setCanTakeDamaged(true);
		
		/** Criacao das entidades. */
		Entity entity1 = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), health, new LifeType(Type.CHAR), new AvailableTries(1,true)).build();
		
		Entity entity2 = new EntityBuilder(world).with(new Position(0, 0),
				new BombDropper(), new LifeType(Type.MONSTER)).build();

		/** Cria evento de colisao entre as entidades Monster e Char. */
		world.setSystem(new VoidEntitySystem() {
			
			@Wire
			EventManager em;
			
			@Override
			protected void processSystem() {
				final CollisionEvent actionCommand = new CollisionEvent(entity1.getUuid(), entity2.getUuid());
				
				em.dispatch(actionCommand);
			}
		});

		/** Roda o sistema. */
		world.process();
		
		/** Verifica se a entidade ainda está ativa já que inicialmente tinha uma tentativa. */
		Assert.assertTrue(entity1.isActive());
		
		
		/** Cria evento de colisao entre as entidades Monster e Char. */
		world.setSystem(new VoidEntitySystem() {
			
			@Wire
			EventManager em;
			
			@Override
			protected void processSystem() {
				final CollisionEvent actionCommand = new CollisionEvent(entity1.getUuid(), entity2.getUuid());
				
				em.dispatch(actionCommand);
			}
		});

		/** Roda o sistema. */
		world.process();
		
		/** Verifica se a entidade está desativada já que a sua tentativa já foi ativada. */
		Assert.assertFalse(entity1.isActive());
	}

}
