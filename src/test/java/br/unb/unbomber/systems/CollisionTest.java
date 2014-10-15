/********************************************************************************************************************************
*Grupo 2:
*Maxwell Moura Fernandes     - 10/0116175
*João Paulo Araujo           -
*Alexandre Magno             -
*Marcelo Giordano            -
*********************************************************************************************************************************/
package br.unb.unbomber.event;

import static org.junit.Assert.*;
import Event;

import java.util.List;

import org.junit.Test;

import br.unb.unbomber.systems.CollisionSystem;

public class CollisionTest {

	@Test
	public void CollisionTest() {
		
		CollisionSystem colisao = new CollisionSystem();
		
		//A colide com B 
		MovedEventEntity a = new MovedEventEntity(1,ESQUERDA,2);
		MovedEventEntity b = new MovedEventEntity(2,null,0);
		getEntityManager().addEvent(a);
		getEntityManager().addEvent(b);
		
		//pega a lista de colisoes que ocorreram
		colisao.update();
		List<Event> collisionEvents = getEntityManager().getEvents(CollisionEvent.class);
		
		assert(ACollisionBTest(collisionEvents, a.getEntityId(), b.getEntityId()));
		
		//B colide com A
		b.setDirection(DIREITA);
		b.setSpeed(2);
		getEntityManager().addEvent(b);
		a.setDirection(null);
		a.setSpeed(0);
		getEntityManager().addEvent(a);
		
		assert(ACollisionBTest(collisionEvents, b.getEntityId(), a.getEntityId()));
		
		//A e B na mesma direção com B mais rápido - responsável pela colisão
		a.setDirection(DIREITA);
		a.setSpeed(1);
		getEntityManager().addEvent(a);
		
		assert(ACollisionBTest(collisionEvents, b.getEntityId(), a.getEntityId()));
		
		
	}
	
		private boolean ACollisionBTest(List<Event> collisionList, int idA, int idB){
			CollisionEvent ce;
			MovedEntityEvent mee;
			for(Event evt1 : collisionList){
				ce = (CollisionEvent) evt1;
				if(ce.getSourceId() == idA){
					return (ce.getTargetId() == idB);
				}
			}

}
