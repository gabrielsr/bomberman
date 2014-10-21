/********************************************************************************************************************************
 *Grupo 2:
 *Maxwell Moura Fernandes     - 10/0116175
 *Jo�o Paulo Araujo           -
 *Alexandre Magno             -
 *Marcelo Giordano            -
 *********************************************************************************************************************************/
package br.unb.unbomber.systems;

import static junit.framework.Assert.*;
import br.unb.unbomber.core.Event;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.MovedEntityEvent;
import br.unb.unbomber.systems.CollisionSystem;

@Ignore
public class CollisionSystemTestCase {

	EntityManager entityManager;
	CollisionSystem collisionSystem;

	// O Before indica que esse método será executado antes.
	@Before
	public void setUp() throws Exception {

		// init a new system for each test case

		// Cria um objeto de EntityManager.
		EntityManagerImpl.init();
		// Obtém a referência do objeto criado a cima.
		entityManager = EntityManagerImpl.getInstance();
		// Cria o objeto que será testado.
		collisionSystem = new CollisionSystem(entityManager);
	}

	/*
	 * Por enquanto só tem dois testes. Um para testar se um único evento de
	 * colisão é gerando para uma uma colisão. Outro para determinar se a origem
	 * da colisão é a entidade mais rápida, quando as entidades tem a mesma
	 * direção.
	 */

	@Test
	public void SingleCollision() {

		// Cria uma entidade que pode se mover e outra que não pode.
		Entity entityA = createEntity(0, 0, 2);
		Entity entityB = createEntity(0, 0);

		// Cria um evento indicando que a entidade A se moveu no tick anterior.
		// Eu deixei a atribuição, mas ela não é necessária.
		MovedEntityEvent mEntityA = createMovedEntityEvent(
				entityA.getEntityId(), Direction.RIGHT);
		// MovedEntityEvent mEntityB =
		// createMovedEntityEvent(entityB.getEntityId(), Direction.RIGHT);

		// Atualliza o collision system e verifica se uma lista com a quantidade
		// correta de eventos
		// foi gerada.
		// Espera-se que um único evento de colisão tenha sido gerado.
		collisionSystem.update();
		List<Event> collisionEvents = entityManager
				.getEvents(CollisionEvent.class);

		assertEquals(1, collisionEvents.size());
	}

	@Test
	public void CollisionAB() {
		// Cria duas entidades que podem se mover, A e B.
		Entity entityA = createEntity(0, 0, 2);
		Entity entityB = createEntity(0, 0, 0);

		// Cria dois eventos indicando que as entidades A e B se moveram no tick
		// anterior.
		// Deixei as atribuições mas elas não são necessárias.
		MovedEntityEvent mEntityA = createMovedEntityEvent(
				entityA.getEntityId(), Direction.RIGHT);
		MovedEntityEvent mEntityB = createMovedEntityEvent(
				entityB.getEntityId(), Direction.RIGHT);

		// Atualiza o collision system e verifica se para a colisão gerada a
		// fonte é a entidade A.
		collisionSystem.update();
		List<Event> collisionEvents = entityManager
				.getEvents(CollisionEvent.class);

		for (Event event : collisionEvents) {
			CollisionEvent collisionEvent = (CollisionEvent) event;
			assertEquals(entityA.getEntityId(), collisionEvent.getSourceId());
		}
	}
	
	//Se nenhum evento de colisão foi gerado para dois elementos que nao se colidem
	public void noneCollision(){
		Entity entityA = createEntity(1, 1, 2);
		Entity entityB = createEntity(3, 4, 0);
		
		MovedEntityEvent mEntityA = createMovedEntityEvent(
				entityA.getEntityId(), Direction.RIGHT);
		MovedEntityEvent mEntityB = createMovedEntityEvent(
				entityB.getEntityId(), Direction.RIGHT);
		
		collisionSystem.update();
		List<Event> collisionEvents = entityManager
				.getEvents(CollisionEvent.class);
		
		for(Event event : collisionEvents){
			CollisionEvent collisionEvent = (CollisionEvent) event;
			assertFalse(entityA.getEntityId() == collisionEvent.getSourceId());
		}
		
	}

	/* Métodos usados pelos casos de teste acima */

	// Existe uma sobrecarga do método createEntity.
	// Passando apenas dois argumentos como a versão abaixo, cria-se uma
	// entidade que não pode se mover (softblock ou powerup).

	// Cria entidades imóveis.
	private Entity createEntity(int x, int y) {

		// Cria uma entidade, identificada por um id único.
		Entity anEntity = entityManager.createEntity();

		// Cria um component Movable.
		// Seta a velocidade em 2 unidades de velocidade.
		// Movable movable = new Movable();
		// movable.setSpeed(velocidade);

		// Create um componet Placement
		// Seta a posição
		CellPlacement placement = new CellPlacement();
		placement.setCellX(x);
		placement.setCellY(y);

		// Adidiciona os componentes criados a entidade.
		// anEntity.addComponent(movable);
		anEntity.addComponent(placement);

		// Atualiza o entityManager adicionando as novas componentes ao conjunto
		// de componetes
		// do jogo.
		// Lembre que cada componente tem um id que indica a qual entidade ela
		// pertence.
		entityManager.update(anEntity);

		return anEntity;
	}// Fim do criador de entidades móveis

	// Cria entidades móveis.
	private Entity createEntity(int x, int y, int velocidade) {

		// Cria uma entidade, identificada por um id único.
		Entity anEntity = entityManager.createEntity();

		// Cria um component Movable.
		// Seta a velocidade em 2 unidades de velocidade.
		Movable movable = new Movable();
		movable.setSpeed(velocidade);

		// Create um componet Placement
		// Seta a posição
		CellPlacement placement = new CellPlacement();
		placement.setCellX(x);
		placement.setCellY(y);

		// Adidiciona os componentes criados a entidade.
		anEntity.addComponent(movable);
		anEntity.addComponent(placement);

		// Atualiza o entityManager adicionando as novas componentes ao conjunto
		// de componetes
		// do jogo.
		// Lembre que cada componente tem um id que indica a qual entidade ela
		// pertence.
		entityManager.update(anEntity);

		return anEntity;
	}// Fim do criado de entidades móveis.

	// Cria uma envento indicando que determinada entidade se moveu no tick
	// anterior.
	private MovedEntityEvent createMovedEntityEvent(int id, Direction direction) {
		// Cria um movedEntityEvent.
		// Caracterisa o evento com informações da entidade associada, i.e, seu
		// id etc.
		MovedEntityEvent event = new MovedEntityEvent();
		event.setEventId(id);
		event.setDirection(direction);

		// Adiciona o evento criado a lista de eventos de todo o jogo.
		entityManager.addEvent(event);

		return event;

	}
}
