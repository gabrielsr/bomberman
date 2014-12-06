package br.unb.unbomber.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import br.unb.unbomber.component.Bounty;
import br.unb.unbomber.component.Score;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.DestroyedEvent;

@Ignore
public class ScoreSystemTest {

	
	EntityManager entityManager;
	ScoreSystem scsystem;
	
	@Before
	public void setUp() throws Exception{

		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		scsystem = new ScoreSystem(entityManager);
	}
	
	@Test
	public void testeInicializa() {
		scsystem = new ScoreSystem();
		
		scsystem.update();
		
		assertTrue(true);
	}
	
	@Test 
	public void testUpdateDestroyed() {
	
		scsystem = new ScoreSystem();
		
		/*Cria a entidade destruída*/
		Entity entity1 = createDestroyedEntity();
		/*Cria a entidade que a destruiu*/
		Entity entity2 = createDestroyerEntity();
		/*Cria o evento de destruição e o adiciona na lista de eventos*/
		DestroyedEvent destroyed = new DestroyedEvent(entity2.getEntityId(), entity1.getEntityId());
		entityManager.addEvent(destroyed);
		/*Testa a função*/
		scsystem.update();
		
		Bounty a = (Bounty) entityManager.getComponent(Bounty.class, destroyed.getTargetId());
		Score b = (Score) entityManager.getComponent(Score.class, destroyed.getSourceId()); 
		/*Compara os valores da recompensa de quem foi destruído com o da pontuação de quem recebeu*/
		/*Quem destrói recebe um número de pontos igual ao da recompensa de quem foi destruído*/
		assertTrue(a.getBounty() == b.getScore());
	}

private Entity createDestroyedEntity(){
	/*Criando uma entidade para destruir...*/
	Entity destroyed = entityManager.createEntity();
	/*Como ela será destruída, ela precisa ter uma recompensa atribuída a ela*/
	Bounty bounty = new Bounty();
	bounty.setBounty(30);	/*Escolhemos um valor arbitrário, estabelecemos essa recompensa*/
	
	destroyed.addComponent(bounty);	/*Adicionando o novo componente 'bounty' à entidade*/
	entityManager.update(destroyed);	/*Atualizando a entidade*/
	
	return destroyed;
}

private Entity createDestroyerEntity(){
	/*Aqui o objetivo é criar a entidade que vai destruir a entidade criada acima*/
	Entity destroyer = entityManager.createEntity();
	/*Essa entidade precisa ter o componente Score para que possa ser incrementada a sua pontuação após a destruição*/
	Score score = new Score();	/*Criando o componente (que não precisa ser inicializado, pois ele já é inicializado na classe do próprio componente)*/
	
	destroyer.addComponent(score); /*Adicionando o componente à entidade*/
	entityManager.update(destroyer);	/*Atualizando a entidade*/
	
	return destroyer;
}
	
}

