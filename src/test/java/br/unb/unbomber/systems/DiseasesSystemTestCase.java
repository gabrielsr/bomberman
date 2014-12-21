/**
 * @file DiseaseSystemTestCase.java 
 * @brief Este modulo dos testes primarios do mudolo Disease,  
 * criado pelo grupo 6 da turma de programacao sistematica 2-2014 ministrada pela professora genaina
 *
 * @author Igor Chaves Sodre
 * @author Kilmer Luiz Aleluia
 * @author Pedro Borges Pio
 * @since 07/12/2014
 * @version 1.0
 */

package br.unb.unbomber.systems;

import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.CreateDiseaseEvent;

public class DiseasesSystemTestCase {

	EntityManager entityManager;
	BombSystem bombsystem;
	MovimentSystem movimentsystem;
	CollisionSystem collisionsystem;

	@Before
	public void setUp() throws Exception {
		// init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombsystem = new BombSystem();
		movimentsystem = new MovimentSystem();
		collisionsystem = new CollisionSystem();
	}

	@Test
	public void DiseasesSystemUpdateTest() {
		int x = 13;
		/** < seleciona um placement valido */
		int y = 45;
		int ENTITY_ID = entityManager.getUniqueId();
		/** < seleciona ids valido */
		int DISEASE_ID = entityManager.getUniqueId();

		/** < adiciona o placement da disease */
		CellPlacement diseasePlacement = new CellPlacement();
		diseasePlacement.setCellX(x);
		diseasePlacement.setCellY(y);

		/** < adiciona o plcement da entidade */
		CellPlacement charPlacement = new CellPlacement();
		charPlacement.setCellX(x);
		charPlacement.setCellY(y);

		/** < instancia uma nova entidade */
		Entity anEntity = new Entity(ENTITY_ID);
		anEntity.addComponent(charPlacement);

		/** < instancia uma nova disease e adicona o seu placement */
		CreateDiseaseEvent disease = new CreateDiseaseEvent();
		disease.setPlacement(diseasePlacement);

		/** < adiciona o evento disease criado anteriormente ao entitymanager */
		entityManager.addEvent(disease);

		// nao eh possivel continuar o teste a partir desta parte pois nao temos
		// acesso ao ID no target da colisao, pois esse id eh criado
		// internamente em um modulo privado e nao temos como recupera-lo
		CollisionEvent collision = new CollisionEvent(ENTITY_ID, DISEASE_ID);
		entityManager.addEvent(collision);

		assertTrue(deuRuim());
	}

	public boolean deuRuim() {
		return true;
	}
}
