package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

/**
 * Classe para verificar se houve dano a uma entidade.
 * 
 * @version 0.1 14 Out 2014
 * @author Grupo 5
 */


public class Health extends Component {
		
	/** Health de uma entidade Character ou Monster. */
	private int lifeEntity;
	/**
	 * Coleta True ou False caso seja permitido retirar dano de uma entidade
	 * monster ou character.
	 */
	private boolean canTakeDamaged;
	/**
	 * Armazena o número de tentativas que o character tem de reviver.
	 */
	private int availableTries;

	/** 
	 * Inicializa vida completa a uma entidade Monster ou Entity 
	 * 
	 * @param lifeEntity 
	 * */
	private void setLifeEntity(int lifeEntity){
		this.lifeEntity = lifeEntity;
	}
	

	/** 
	 * Coleta a quantidade de vida que uma entidade possui 
	 * 
	 * @return lifeEntity
	 * */
	public int getLifeEntity(){
		return lifeEntity;
	}
	
	/** 
	 * Diminui o valor da vida da entidade pelo método takeDamage() da classe LifeSystem
	 * pelo método takeDamage() da classe LifeSystem
	 * 
	* */
	public void lifeDrecrement(){
		lifeEntity --;
	}
	
	/**
	 * Atribui a possibilidade de causar dano a uma entidade.
	 * Logica: Apos a retirada de algum dano ao health da entity, System 
	 * confere se lifeEntity e diferente de zero, caso ainda seja entao e
	 * atribuido ao setTakeDamaged True para que ainda ocorra a oportunidade
	 * de causar dano a esta entidade.
	 * Caso contrario e atribuido False.
	 * 
	 * @param canTakeDamaged
	 */
	private void setCanTakeDamaged(boolean canTakeDamaged){
		this.canTakeDamaged = canTakeDamaged;
	}


	/**
	 * Coleta a possibilidade de causar dano a uma entidade.
	 * Logica: System confere a possibilidade de retirar danos, caso seja 
	 * possivel ela chama getLifeEntity, retira alguma quantia de vida e atribui
	 * novamente ao setLifeEntity este novo "Health".
	 * 
	 * @return canTakeDamaged 
	 */
	public boolean isCanTakeDamaged(){
		return canTakeDamaged;
	}
	/**
	 * Seta o número de tentativas iniciais
	 * 
	 */
	public void setAvailableTries(int tries){
		availableTries = tries;
	}
	/**
	 * Retorna o valor de tentativas disponíveis
	 */
	public int getAvailableTries(){
		return availableTries;
	}
	/**
	 * Decrementa o número de tentativas disponíveis ao morrer
	 */
	public void availableTriesDecrement(){
		availableTries --;
	}
	
}
