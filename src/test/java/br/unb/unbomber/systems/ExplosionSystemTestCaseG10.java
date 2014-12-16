package br.unb.unbomber.systems;

/**
* @file ExplosionSystemTestCaseG10.java
* @brief Teste de ExplosionSystem realizado pelo grupo 10
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 11/12/2014
* @version 1.0
*/

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.systems.ExplosionSystem;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;

////////////////////////////////////////////////
/// Retirar comentários dos testes 3, 4 e 5
////////////////////////////////////////////////

public class ExplosionSystemTestCaseG10 {

	EntityManager entityManager;
	ExplosionSystem explosionSystem;
	
	@Before
	public void setUp() throws Exception{

		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		explosionSystem = new ExplosionSystem(entityManager);
	}
	
	/** TESTE 1 */
	/*  Teste do construtor (sem parâmetro) */
	@Test
	public void testeConstrutor() {
		
		ExplosionSystem explosionSystem = new ExplosionSystem();
		explosionSystem.update();
		
		assertNotNull(explosionSystem);
		assertEquals(explosionSystem.getClass(), ExplosionSystem.class);
	}
	
	/** TESTE 2 */
	/*  Teste do construtor (com parâmetro) */
	@Test
	public void testeConstrutor2() {
		
		ExplosionSystem explosionSystem = new ExplosionSystem(entityManager);
		explosionSystem.update();
		
		assertNotNull(explosionSystem);
		assertEquals(explosionSystem.getClass(), ExplosionSystem.class);
	}
	
	/** TESTE 3 */
	/*  Teste do método createExplosion passando
	 *  parâmetros válidos */
//	@Test
	public void testeCreateExplosionValidParam() {
		
		final int EXP_RANGE = 1;
		final int CELL_X = 1;
		final int CELL_Y = 1;
		
		Entity expEntity = entityManager.createEntity();
		
		CellPlacement expPlacement = new CellPlacement();
		expPlacement.setCellX(CELL_X);
		expPlacement.setCellY(CELL_Y);
		entityManager.addComponent(expPlacement);
		expEntity.addComponent(expPlacement);
		
		try {
			explosionSystem.createExplosion(expPlacement, EXP_RANGE, expEntity.getEntityId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Component> placements = (List<Component>) entityManager.getComponents(CellPlacement.class);
		List<Component> explosions = (List<Component>) entityManager.getComponents(Explosion.class);
		
		/** Assert (verifica a quantidade de explosões criadas) */
		/*  Ocorre 1 explosão em cada sentido (cima, baixo, direita e esquerda)
		 *  e uma no centro. Assim, no total são 5 explosões */
		assertEquals( 5, explosions.size() );
		
		/** Assert (verifica a quantidade de células) */
		/* 1 célula contém expPlacement, as outras
		 * 5 células são criadas nas explosões */
		assertEquals( 6, placements.size() );
		
		/// Abaixo as 5 células criadas pelo método createExplosion
		/// são atribuidas a variáveis do tipo CellPlacement para
		/// acessar cada posição da célula e avaliar se elas foram
		/// criadas corretamente
		CellPlacement cp0 = (CellPlacement) placements.get(0);	//< Célua que armazena expPlacement 
		CellPlacement cp1 = (CellPlacement) placements.get(1);	//< As demais células foram criadas por
		CellPlacement cp2 = (CellPlacement) placements.get(2);	//  createExplosion().
		CellPlacement cp3 = (CellPlacement) placements.get(3);
		CellPlacement cp4 = (CellPlacement) placements.get(4);
		CellPlacement cp5 = (CellPlacement) placements.get(5);
		
		/* O método createExplosion() tem uma sequência de verificação, sendo
		 * que a primeira célula de explosão criada neste método é posicionada 
		 * no mesmo local que expPlacement. Depois é verificado se a explosão
		 * pode se propagar para cima, depois para baixo, depois à esquerda e
		 * por fim à direita, todos nessa sequência. Como o update() é feito ao 
		 * final, esta sequência será invertida na lista de components. Então a
		 * célula que deveria estar no index(1) da lista, na verdade está no
		 * index(5) e assim por diante. */
		/// Para verificar isso, pegamos a última célua index(5) e comparamos com
		/// a primeira index(0).
		/** Asserts (célula (5) criada em createExplosion() em relação ao argumento expPlacement) */
		assertEquals( expPlacement.getCellX(), cp5.getCellX());
		assertEquals( expPlacement.getCellY(), cp5.getCellY());
		assertEquals( cp0.getCellX(), cp5.getCellX());	//< cp0 == expPlacement
		assertEquals( cp0.getCellY(), cp5.getCellY());
		
		/// Passando os asserts acima, aferimos que ocorre exatamente como foi descrito,
		/// então as demais posições também são invertidas na lista:
		/** Asserts (verifica as posições das células criadas e a sequência delas) */
		assertEquals( (expPlacement.getCellX() +1), cp4.getCellX()); //< cp4 deve ser a célula à direita de expPlacement,
		assertEquals( expPlacement.getCellY(), cp4.getCellY());		 //  então ela está na posição X+1 de expPlacement.
		
		assertEquals( (expPlacement.getCellX() -1), cp3.getCellX()); //< cp3 deve ser a célula à esquerda de expPlacement,
		assertEquals( expPlacement.getCellY(), cp3.getCellY());		 //  então ela está na posição X-1 de expPlacement.
		
		assertEquals( expPlacement.getCellX(), cp2.getCellX());		 //< cp2 deve estar abaixo de expPlacement, então sua
		assertEquals( (expPlacement.getCellY() +1), cp2.getCellY()); //  posição é Y+1 de expPlacement.
		
		assertEquals( expPlacement.getCellX(), cp1.getCellX()); 	 //< cp1 deve estar acima de expPlacement, então sua
		assertEquals( (expPlacement.getCellY() -1), cp1.getCellY()); //  posição é Y-1 de expPlacement.
		
	}

	/** TESTE 4 */
	/*  Teste do método createExplosion passando
	 *  parâmetros válidos quando a explosão
	 *  ocorre em uma quina do grid. Nesse caso
	 *  na posição X = 0 e Y = 0 */
//	@Test
	public void testeCreateExplosionValidParam2() {
		
		final int EXP_RANGE = 2;
		final int CELL_X = 0;
		final int CELL_Y = 0;
		
		Entity expEntity = entityManager.createEntity();

		CellPlacement expPlacement = new CellPlacement();
		expPlacement.setCellX(CELL_X);
		expPlacement.setCellY(CELL_Y);
		entityManager.addComponent(expPlacement);
		expEntity.addComponent(expPlacement);
		
		try {
			explosionSystem.createExplosion(expPlacement, EXP_RANGE, expEntity.getEntityId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Component> explosions = (List<Component>) entityManager.getComponents(Explosion.class);
		List<Component> placements = (List<Component>) entityManager.getComponents(CellPlacement.class);
		
		/** Assert (verifica a quantidade de explosões criadas) */
		/*  Ocorrem 2 explosões para a direita, 2 para baixo
		 *  e 1 no centro, totalizando 5 explosões. Nos sentidos
		 *  esquerda e cima não devem haver células, pois essas
		 *  posições são fora do grid */
		assertEquals( 5, explosions.size() );
		assertEquals( 6, placements.size() ); //< 1 célula contém expPlacement, as outras
											  //  5 células são criadas nas explosões
	}

	
	/** TESTE 5 */
	/*  Teste do método createExplosion passando
	 *  um parâmetro inválido de explosion range */
//	@Test
	public void testeCreateExplosionInvalidParam() {
		
		final int EXP_RANGE_NEGATIVE = -1;  //< Explosion Range negativo
		final int CELL_X = 2;
		final int CELL_Y = 2;
		
		Entity expEntity = entityManager.createEntity();

		CellPlacement expPlacement = new CellPlacement();
		expPlacement.setCellX(CELL_X);
		expPlacement.setCellY(CELL_Y);
		entityManager.addComponent(expPlacement);
		expEntity.addComponent(expPlacement);
		
		try {
			explosionSystem.createExplosion(expPlacement, EXP_RANGE_NEGATIVE, expEntity.getEntityId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Component> explosions = (List<Component>) entityManager.getComponents(Explosion.class);
		
		/** Assert (verifica a quantidade de explosões criadas) */
		/*  Não devem haver explosões nesse caso */
		assertEquals( 0, explosions.size() );
		
	}
	
}
