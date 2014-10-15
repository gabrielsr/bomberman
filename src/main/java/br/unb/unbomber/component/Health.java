/* Indica de qual pacote o arquivo pertence */
/* No caso ele pertence ao Components */
package br.unb.unbomber.component;

/* Interface para dados de componente */
import br.unb.unbomber.core.Component;

/* Heranca unica do componente Health */
public class Health extends Component {
		
	/* Health de uma entidade Character ou Monster  */
	private int lifeEntity;
	/* Coleta True ou False caso seja permitido retirar dano de uma entidade
	 * monster ou character.
	 */
	private boolean canTakeDamaged;


	/* Inicializa vida completa a uma entidade Monster ou Entity */
	/* *
	 * Issues: Quantidade de Full Life vai ser passada por quem?
	 * */
	private void setLifeEntity(int lifeEntity){
		this.lifeEntity = lifeEntity;
	}
	
	/* Coleta a quantidade de vida que uma entidade possui */
	private int getLifeEntity(){
		return lifeEntity;
	}
	
	/* Atribui a possibilidade de causar dano a uma entidade */
	/* Logica: Apos a retirada de algum dano ao health da entity, System 
	 * confere se lifeEntity e diferente de zero, caso ainda seja entao e
	 * atribuido ao setTakeDamaged True para que ainda ocorra a oportunidade
	 * de causar dano a esta entidade.
	 * Caso contrario e atribuido False.
	 */
	private void setCanTakeDamaged(boolean canTakeDamaged){
		this.canTakeDamaged = canTakeDamaged;
	}

	/* Coleta a possibilidade de causar dano a uma entidade */
	/* Logica: System confere a possibilidade de retirar danos, caso seja 
	 * possivel ela chama getLifeEntity, retira alguma quantia de vida e atribui
	 * novamente ao setLifeEntity este novo "Health".
	 */
	private boolean isCanTakeDamaged(){
		return canTakeDamaged;
	}
}
