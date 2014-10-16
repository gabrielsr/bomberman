#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ScoreBomberman.h"

#define SERVIDOR_SCORE

#define PLAYER_MAX 4
#define SCORE_MONSTER_DEATH 100	/*pontuação referente ao assassinato de um monstro*/
#define SCORE_EXP_SB 10			/*pontuação referente à explosão de um soft block*/
#define SCORE_PICKUP_POWERUP 30	/*pontuação referente à obtenção de um power up*/
#define SCORE_CHARACTER_DEATH 200/*pontuação referente ao assassinato de outro jogador*/

struct score {			/*Estrutura padrão do módulo*/
	int pontos [PLAYER_MAX];
};

struct tabela {		/*Estrutura criada para a função criaTabelaHighScore*/
	char name[20];
	int total;
};

struct recebe {		/*Estrutura que nós imaginamos que nós vamos receber == Event*/
	int comando;
	int player;
	struct recebe* next;
};

struct nomes {	/*Estrutura de nomes criada para que o módulo possa receber o nome dos jogadores*/
	int pl;
	char nombre[20];
	struct nomes* prox;
};

int MS_scoreModuleInterface (LST_Event* events) {	/*Esta função recebe uma lista de eventos do turno e chama outra função para montar e*/
	int i = 0;								/*contabilizar a pontuação*/
	LST_Event* aux = events;
	while (aux->next != NULL) {				/* Percorrendo a lista de eventos....*/
		i += findEvent(aux->comando,aux->player);	
		aux=aux->next;
	}
	return i;	/*retorna 0 se não houve problemas, retorna 1 se houve problemas*/
}

static STR_Tabela MS_paranomes[PLAYER_MAX];

void MS_sendPlayerNameEvent (LST_Name* nomes) {	/*Esta função deve ser chamada pelo módulo de Events no início do jogo*/
	LST_Name* aux = nomes;						/*Ela tem o propósito de armazenar na estrutura 'paranomes' o nome dos jogadores*/
	int i;									/*para futura referência*/
		while (aux->prox != NULL) {
			i = (aux->pl)-1;
			strcpy (aux->nombre, MS_paranomes[i].name);
			aux=aux->prox;
		}		
}

int findEvent (int comando, int jogador) {	/*está se considerando que o argumento dessa função será a struct recebida contendo os eventos por turno que serão contabilizados*/ 
	int i;
	static STR_Score scores;
		if (comando == 0) {	/*considerando que '0' representa a inicialização do jogo*/
			inicializaScore(&scores);
			return 0;
		}
		if (comando == 1) {	/*considerando que '1' representa a morte de um monstro*/
			i=addScore('m',jogador,&scores);
			return i;							/*Para todas os if's e funções, vale o seguinte: a função realizou sua função*/
		}										/*com sucesso -> retorna 0. Ocorreu algum erro, e, consequentemente, a função*/
		if (comando == 2) {						/*não pode realizar sua função -> retorna 1*/
			i=addScore('s',jogador,&scores);
			return i;	/*considerando que '0' tem um análogo que representa a destruição de um soft block*/
		}
		if (comando == 3) {		/*considerando que '3' representa a captura de um power-up*/
			i=addScore('p',jogador,&scores);
			return i;
		}
		if (comando == 4) {	/*considerando que '4' representa a morte de outro jogador*/
			i=addScore('c',jogador,&scores);
			return i;
		}
		if (comando == 5) {	
			criaTabelaHighScore(&scores);	/*Comando de finalização do jogo*/
			clearScore(&scores);
			return 0;
		}
		if (comando == 6) {	/*Comando exclusivo da função getScore*/
			if (jogador>0 && jogador<=PLAYER_MAX) { 
				return scores.pontos[jogador-1];
			}
			else {
				return 1;
			}
		}
return 1;
}


void criaTabelaHighScore (STR_Score* scores) {	/*Essa função tem como função criar um arquivo contendo as pontuações*/
	STR_Tabela ths[PLAYER_MAX];	/*vetor auxiliar contendo a pontuação dos jogadores da partida em questão*/
	int i,j,k;		/*inteiros auxiliares*/
	char nada[20];	/*vetor de caractere auxiliar*/
	STR_Tabela pa[10];	/*vetor tabela de leitura do arquivo binário*/
	FILE* fp;		
	j=0;	
	nada[0]='\0';
		for (i=0;i<PLAYER_MAX;i++) {		/*Laço destinado a obter o nome (string) dos jogadores envolvidos na partida*/
			strcpy(ths[i].name, MS_paranomes[i].name);
			ths[i].total=scores->pontos[i];
		}
	fp = fopen ("scores.bin","rb");
	if (fp==NULL) {	/*Conferindo se o arquivo de High Scores existe*/
		fp = fopen ("scores.bin","w+b");		/*Se não, cria um*/
			for (i=0;i<10;i++) {				/*com nomes e pontuações zeradas*/
				fwrite (nada,sizeof(char),20,fp);
				fwrite (&j,sizeof(int),1,fp);
			}	
		fclose(fp);
		fp = fopen ("scores.bin","rb");
	}
		for(i=0;i<10;i++) {						/*Com o arquivo existente, faz-se a leitura dos dados, obtendo o nome e a pontuação do*/
			fread(pa[i].name,sizeof(char),20,fp);	/*jogador e armazena-se no vetor tabela pa[]*/
			fread(&pa[i].total,sizeof(int),1,fp);
		}
	fclose(fp);
		for(i=0;i<PLAYER_MAX;i++) {		/*Para todos os jogadores*/
			for (j=0;j<10;j++) {		/*Faz-se a comparação com todos os valores que pertenciam à antiga tabela de High Score*/
				if (pa[j].total <= ths[i].total) {	/*Se um dos valores da pontuação do jogador for maior ou igual a um dos números*/
						for(k=9;k>j;k--) {			/*da tabela, temos que reorganizar a ordem para poder inserir o novo score*/
							strcpy(pa[k].name,pa[k-1].name);	/*Os elementos mais próximos do final, recebem a informação do*/
							pa[k].total=pa[k-1].total;			/*elemento anterior, com o último elemento sendo eliminado*/
						}										
					strcpy(pa[j].name,ths[i].name);				/*e o novo elemento sendo inserido em sua posição de direito*/
					pa[j].total=ths[i].total;
					break;		/*se o elemento já teve a sua posição estabelecida, a função para pois se não, haverá a substituição*/
				}				/*para todos os outros valores (que evidentemente serão menores que a pontuação recentemente inserida*/
			}					/*e isso seria um erro*/
		}
	fp = fopen ("scores.bin","w+b");	/*Feita a organização no vetor auxiliar tabela pa[], podemos agora inserir a nova configuração*/
		for (i=0;i<10;i++) {			/*no arquivo de armazenamento*/
				fwrite (pa[i].name,sizeof(char),20,fp);
				fwrite (&pa[i].total,sizeof(int),1,fp);
			}	
	fclose(fp);
}

void inicializaScore (STR_Score* scores) {	/*Função que inicializa o score de todos os jogadores como zero (0)*/
	int i;
		for(i=0;i<PLAYER_MAX;i++) {
			scores->pontos[i]= 0;
		}
}

int addScore (char action, int player, STR_Score* scores) {	/*Função que incrementa a pontuação de um jogador 'player' de acordo com a ação realizada*/
	int i = player-1;	/*Não é necessária a preocupação com o índice do vetor. Se o cliente enviar '1' para representar o jogador 1, o ajuste é feito aqui, pois, a posição do jogador 1 no vetor da struct é no índice 0 (player-1)*/
	
	if ( action!='m' && action != 's' && action != 'p' && action != 'c') {
		return 1;
	}
	
	if (i>=0 && i<PLAYER_MAX) {	/*Verificação: não podemos ter um jogador definido como menor que zero ou um jogador*/
		switch (action) {		/*definido como maior que o número máximo de jogadores*/
		case 'm':
		scores->pontos[i] += SCORE_MONSTER_DEATH;		/*os casos definidos de pontuação (#defines acima)*/
		break;
		case 's':
		scores->pontos[i] += SCORE_EXP_SB;
		break;
		case 'p':
		scores->pontos[i] += SCORE_PICKUP_POWERUP;
		break;
		case 'c':
		scores->pontos[i] += SCORE_CHARACTER_DEATH;
		break;
		}
	}
	else {
		return 1;	/*retorna 1 avisando que houve erro*/
	}
	return 0;
}

int MS_getScore (int jogador) {		/*Função que retorna o score de um jogador 'player' escolhido*/
	int comando = 6;
	int player = jogador;
	int i = findEvent(comando, player);
		if (i==1) {
			return 1;	/*Esse valor avisa para quem chamou a função que houve um problema na chamada*/
		}
	return i;
}

void clearScore (STR_Score* scores) {	/*Zera a pontuação*/
	inicializaScore(scores);
}


