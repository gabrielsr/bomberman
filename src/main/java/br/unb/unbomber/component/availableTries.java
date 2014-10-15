/* Indica de qual pacote o arquivo pertence */
/* No caso ele pertence ao Components */
package br.unb.unbomber.component;

/* Interface para dados de componente */
import br.unb.unbomber.core.Component;

/* Heranca unica do componente availableTries */
public class availableTries extends Component {
		
	/* Life tries de um entidade Monter ou Character  */
	private int lifeTries;
	/* Coleta True ou False caso seja permitido retirar dano de uma entidade
	 * monster ou character.
	 */
	private boolean canTakeLife;


	/* Inicializa numero de tentativas a uma entidade Monster ou Entity */
	/* *
	 * Issues: Quantidade de numero de tentativas vai ser passada por quem?
	 * */
	private void setLifeTries(int lifeTries){
		this.lifeTries = lifeTries;
	}
	
	/* Coleta as vidas restantes que uma entidade possui */
	private int getLifeTries(){
		return lifeTries;
	}
	
	/* Atribui a possibilidade de retirar a vida de uma entidade */
	/* Logica: Apos a retirada de algum dano ao health da entity, System 
	 * confere se lifeEntity e igual zero, caso seja entao e
	 * questionado a possibilidade de retirar vidas por isCanTakeLife ou,
	 * verifica a quantidade de vidas restantes por getLifeTries.
	 * Se for possivel entao e atribuido uma vida a menos no setLifeTries,
	 * e logo apos e conferido quantas vidas restantes a entidade possui para
	 * que seja atualizado o setCanTakeLife de acordo com a possibilidade.
	 */
	private void setCanTakeLife(boolean canTakeLife){
		this.canTakeLife = canTakeLife;
	}

	/* Coleta a possibilidade de retirar a vida de uma entidade */
	/* Logica descrita acima no metodo setCanTakeLife */
	private boolean isCanTakeLife(){
		return canTakeLife;
	}
}
	