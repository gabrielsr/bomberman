package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.availableTries;

import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;

import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.DamageEntityEvent;
import br.unb.unbomber.event.DestroyedEvent;
import br.unb.unbomber.event.GameOverEvent;
import br.unb.unbomber.event.InvencibleEvent;

/**
 * Classe repons�vel pelas regras e l�gicas do M�dulo Life.
 * 
 * @version 0.1 14 Out 2014
 * @author Grupo 5
 */

public class LifeSystem extends BaseSystem {

	/** Instancia o EntityManager a partir da classe pai BaseSystem */
	public LifeSystem() {
		super();
	}
	
	/** 
	 * Inicia uma Entidade modelo.
	 * 
	 * @param	model	Entidade modelo.
	 */
	public LifeSystem(EntityManager model) {
		super(model);
		
	}
	
	@Override
	public void update() {
		
		/** Coleta Eventos de Colis�o */
		List<Event> collisionEvents = getEntityManager().getEvents(
												CollisionEvent.class);
		
		for(Event event:collisionEvents){
			CollisionEvent collisions = (CollisionEvent) event;

			//TODO Criar Components capaz de diferenciar Entities.
			
			/** 
			 * @if Confere se h� colis�o.
			 * 
			 * Monster - Character
			 * 	Gera dano no Character caso colidam.
			 * 
			 * Bomb - Monster
			 * 	Gera dano no Monster caso colidam.
			 * 
			 * Bomb - Character
			 * 	Gera dano no Character caso colidam.
			 * 
			 * Monster - Explosion
			 * 	Gera dano no Monster.
			 * 
			 * Character - Explosion
			 *	Gera dano no Character.
			 * 
			 */

			Health life = (Health) getEntityManager().getComponent(
							Health.class, collisions.getOwnerId());

			takeDamaged(life);

		}
				
	}	
	
	public void takeDamaged(Health life){
		
		//TODO Implementar a retirada de Health a uma entidade.
		//ISSUE Component life j� est� contido com a TargetId da colis�o?
		
	}
	
}
