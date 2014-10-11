/* Indica de qual pacote o componente pertence */
package br.unb.unbomber.component;

/* Interface para dados de componente */
import br.unb.unbomber.core.Component;

/* Herança única do componente Health */
public class Health extends Component {
	
	/* onwnerId -> Id da entidade associada a este componente */
	private int onwnerId;
	
	/* lifeChar -> Health de uma entidade Character ou Monster  */
	private int lifeEntity;
	/* canTakeDamaged -> Coleta True ou False caso seja permitido retirar 
	 *	dano de uma entidade monster ou character.
	 */
	private boolean canTakeDamaged;

	
	/* Método para coletar o Id deste component */
	public int getOnwnerId() {
		return onwnerId;
	}

	/* Método para atribuir algum Id a este component */
	public void setOnwnerId(int onwnerId) {
		this.onwnerId = onwnerId;
	}

	/* Inicializa vida completa a uma entidade Monster ou Entity */
	/* *
	 * Issues: Quantidade de Full Life vai ser passada por quem?
	 * */
	private void setLifeEntity(int lifeEntity){
		this.lifeEntity = lifeEntity;
	}
	
	/* Coleta a quantidade de vida que uma entidade possui */
	private int isLifeEntity(){
		return lifeEntity;
	}
	
	/* Atribui a possibilidade de causar dano a uma entidade */
	/* Lógica: Após a retirada de algum dano ao health da entity, System 
	 * confere se lifeEntity é diferente de zero, caso ainda seja então é
	 * atribuído ao setTakeDamaged True para que ainda ocorra a oportunidade
	 * de causar dano a esta entidade.
	 * Caso contrário é atribuído False.
	 */
	private void setCanTakeDamaged(boolean canTakeDamaged){
		this.canTakeDamaged = canTakeDamaged;
	}

	/* Coleta a possibilidade de causar dano a uma entidade */
	/* Lógica: System confere a possibilidade de retirar danos, caso seja 
	 * possível ela chama getLifeEntity, retira alguma quantia de vida e atribui
	 * novamente ao setLifeEntity este novo "Health".
	 */
	private boolean isCanTakeDamaged(){
		return canTakeDamaged;
	}
}
	