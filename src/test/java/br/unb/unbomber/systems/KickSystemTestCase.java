package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.nio.file.attribute.PosixFilePermission;
import java.util.List;

import junit.framework.Assert;
import net.mostlyoriginal.api.event.common.EventManager;

import org.junit.Before;
import org.junit.Test;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.EntityBuilder;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.Velocity;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.component.PowerUpInventory;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.MovedEntityEvent;
import br.unb.unbomber.event.MovementCommandEvent;

/**
 * Classe de testes do KickSystem do Kick.
 * 
 * 
 * @author Hichemm Khalyd 
 */

public class KickSystemTestCase {

	/*EntityManager entityManager;*/
	
	World world;
	
	BombSystem bombSystem;
	KickSystem kickSystem;
	CollisionSystem collisionSystem;
	
	
	
	/**
	 * Método para setar configurações iniciais de ambiente
	 * 
	 */
	
	@Before
	public void setUp() throws Exception {
		/*EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();*/
		
		kickSystem = new KickSystem();
		
		world = new World();
		world.setSystem(kickSystem);
		
		world.setManager(new EventManager());
		world.setManager(new UuidEntityManager());
	}
	
	/**
	 * Teste do construtor
	 * 
	 * @result Passa no teste se o valor retornado e TRUE.
	 */
	
	@Test
	public void testConstructor() {
		kickSystem.process();
		
		assert(true);
	}
	
	
	/**
	 * Teste que verifica se uma entidade que está em condições esperadas para chutar 
	 * uma bomba, uma vez qque haja uma coilasão, a bomba realmente sofrerá os efeitos
	 * 
	 * @result se a velocidade da bomba for diferente de 0 o teste passa
	 */
	
	@Test
	public void testCheckIfCanKickBombs(){
		
		world.initialize();
		
		PowerUpInventory pup = new PowerUpInventory();
		pup.addType(PowerType.KICKACQUIRED);
		
		Movable mv = new Movable();
		mv.setFaceDirection(Direction.RIGHT);
		
		/** Criacao das entidades. */
        final Entity kicker = new EntityBuilder(world).with(new Position(0, 0),
                new BombDropper(),pup,mv ).build();
        
        final Entity bomb = new EntityBuilder(world).with(new Position(1, 0),
                new Explosive()).build();
        
        world.setSystem(new VoidEntitySystem() {
            
            @Wire
            EventManager em;
             
            @Override
            protected void processSystem() {
                final CollisionEvent actionCommand = new CollisionEvent(kicker.getUuid(), bomb.getUuid());
                 
                em.dispatch(actionCommand);
            }
        });
        
        
       world.process();
        
        Assert.assertNotNull(bomb.getComponent(Velocity.class));
        Assert.assertTrue(bomb.getComponent(Velocity.class).getMovement().getX() != 0 );
        Assert.assertTrue(bomb.getComponent(Velocity.class).getMovement().getY() == 0 );

		
	}
	
	/**
	 * Teste que verifica se uma entidade que não está em condições esperadas para chutar 
	 * uma bomba, uma vez qque haja uma coilasão, a bomba não sofrerá os efeitos
	 * 
	 * @result se a bomba não tiver componente de velocidade o teste passará
	 */
	@Test
	public void testCheckIfCanNotKickBombs(){
		
		world.initialize();
		
		PowerUpInventory pup = new PowerUpInventory();
		pup.addType(PowerType.BOMBUP);
		
		Movable mv = new Movable();
		mv.setFaceDirection(Direction.RIGHT);
		
		/** Criacao das entidades. */
        final Entity kicker = new EntityBuilder(world).with(new Position(0, 0),
                new BombDropper(),pup,mv ).build();
        
        final Entity bomb = new EntityBuilder(world).with(new Position(1, 0),
                new Explosive()).build();
        
        world.setSystem(new VoidEntitySystem() {
            
            @Wire
            EventManager em;
             
            @Override
            protected void processSystem() {
                final CollisionEvent actionCommand = new CollisionEvent(kicker.getUuid(), bomb.getUuid());
                 
                em.dispatch(actionCommand);
            }
        });
        
        
       world.process();
       
        Assert.assertNull(bomb.getComponent(Velocity.class));
 	
	}
	
	
	/**
	 * Teste que verifica quando uma entidade que está em condições esperadas para chutar 
	 * uma bomba, uma vez qque haja uma coilasão, a bomba realmente sofrerá os efeitos 
	 * na direção compativel com a face direction
	 * 
	 * @result se a velocidade da bomba for diferente de 0 no eixo esperado o teste passará
	 */
	@Test
	public void testBombDirection(){
		
		world.initialize();
		
		PowerUpInventory pup = new PowerUpInventory();
		pup.addType(PowerType.KICKACQUIRED);
		
		Movable mv = new Movable();
		mv.setFaceDirection(Direction.UP);
		
		/** Criacao das entidades. */
        final Entity kicker = new EntityBuilder(world).with(new Position(0, 0),
                new BombDropper(),pup,mv ).build();
        
        final Entity bomb = new EntityBuilder(world).with(new Position(1, 0),
                new Explosive()).build();
        
        world.setSystem(new VoidEntitySystem() {
            
            @Wire
            EventManager em;
             
            @Override
            protected void processSystem() {
                final CollisionEvent actionCommand = new CollisionEvent(kicker.getUuid(), bomb.getUuid());
                 
                em.dispatch(actionCommand);
            }
        });
        
        
       world.process();
        
        Assert.assertNotNull(bomb.getComponent(Velocity.class));
        Assert.assertTrue(bomb.getComponent(Velocity.class).getMovement().getY() > 
        	bomb.getComponent(Velocity.class).getMovement().getX());

	}
		
}
