package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.availableTries;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntitySystemImpl;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Event;

import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.DamageEntityEvent;
import br.unb.unbomber.event.DestroyedEvent;
import br.unb.unbomber.event.GameOverEvent;
import br.unb.unbomber.event.InvencibleEvent;

/**
 * Classe de testes do LifeSystem do Módulo Life.
 * 
 * @version 0.1 14 Out 2014
 * @author Grupo 5
 */

public class LifeSystemTestCase {
	
	/** Gerenciador das entidades */
	EntityManager entityManager;
	/** Sistema de controle do Módulo Life */
	LifeSystem system;
	
	@Before
	public void setUp() throws Exception {
		
		/** Inicia um sistema para cada caso de teste */
		EntitySystemImpl.init();
		entityManager = EntitySystemImpl.getInstance();
		system = new LifeSystem(entityManager);
	}


	/**
	 * Testa contagem da vida, decremento.
	 * Verifica se foi retirado vida quando ocorre dano a uma entidade
	 * Char ou Monster.
	 */
	public void decrementHealthTest() {
		 
	}

	/**
	 * Testa contagem da vida, incremento.
	 * Verifica se houve aumento de vida quando e coletado o Power Up
	 * para aumentar a vida de uma Entidade Char ou Monster. 
	 */
	public void increaseHealthTest() {
		
	}

	/**
	 * Testa destruição de uma Entidade quando sua vida chega a zero.
	 * Entidade Char ou Monster.
	 */
	public void destroyifHealthZeroTest() {
		
	}

	/**
	 * Testa se uma Entidade Character possui mais tentativas de Vida.
	 * Confere também se é recriado o personagem na casa inicial 
	 * e com invencibilidade. 
	 */
	public void recreateIfHasMoreTriesTest() {
		
	}

	/**
	 * Testa se ocorre GameOverEvent para uma Entidade Character que não possua
	 * mais vida (Health) e tentativas de vida (availableTries). 
	 */
	public void gameOverIfHasNoMoreTriesTest() {
		
	}
}


	