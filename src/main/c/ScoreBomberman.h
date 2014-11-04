/**
* @file ScoreBomberman.h
* @brief Este é o módulo de definição das funções que tratam o score(pontuação) do jogo Bomberman, criado pelo Grupo 10 da turma de Programação Sistemática 2-2014, ministrada pela professora Genaína Nunes Rodrigues, na Universidade de Brasília.
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 07/10/2014
* @version 1.0
*/
#include <stdio.h>
#include <stdlib.h>

#ifndef MOD_SCORE	
#define MOD_SCORE


#ifdef SERVIDOR_SCORE		/*Definição criada para estabelecer módulo cliente e módulo servidor. Funções internas serão visíveis apenas */	
#define EXT_MOD_SCORE		/*para o módulo de implementação (ScoreBomberman.c) e funções externas podem ser chamadas, mas não modificadas*/
#else				/*por outros módulos.*/
#define EXT_MOD_SCORE extern	/**< Definição que estabelece quais funções serão externas e quais funções serão internas ao módulo*/
#endif


typedef struct score STR_Score;	/**< Estrutura principal que lidará com a incrementação e o armazenamento da pontuação (interna ao módulo).*/
typedef struct recebe LST_Event;/**< Estrutura que supostamente será recebida por outros módulos (a cada tique) para que seja possível atualizar a pontuação.*/
typedef struct tabela STR_Tabela;/**< Estrutura auxiliar da função criaTabelaHighScore (interna ao módulo).*/
typedef struct nomes LST_Name; /**< Estrutura que enviará o nome dos jogadores para este módulo.*/



/**
* @brief Esta função recebe uma lista de LST_Events (struct recebe) do turno do jogo e chama outra função (interna ao módulo) para montar e contabilizar a pontuação dos jogadores envolvidos. 
* 
* @param events uma lista do tipo LST_Events que representa os eventos do turno.
* @return retorna 0 se não houve problemas na execução (valores errados enviados, como comandos ou jogadores inexistentes) e 1 se tudo correu bem.
*/
EXT_MOD_SCORE int MS_scoreModuleInterface (LST_Event* events); /*Função que receberá os eventos de outros módulos e contabilizará a pontuação dos jogadores*/
/**
* @brief Função que tem o propósito de armazenar o nome dos jogadores para futura referência (criação da tabela de High Scores). Essa função deve ser chamada logo após a nomeação dos jogadores, antes da inicialização do jogo. Se esta função não for chamada, a função que cria a tabela de High Scores não terá uma referência na hora de relacionar um jogador com sua pontuação.
*
* @param nomes uma lista do tipo LST_Name (struct nomes) que traz consigo em cada célula o número e o nome de um jogador.
* @return void
*/
EXT_MOD_SCORE void MS_sendPlayerNameEvent (LST_Name* nomes); /*Função que deve ser chamada logo após a nomeação dos jogadores. Se esta função não for chamada, a função criaTabelaHighScore não terá uma referência na hora de relacionar um jogador com sua pontuação.*/
static int findEvent (int comando, int jogador); /*Função que pega os eventos enviados modifica/contabiliza a pontuação de jogadores.*/
static void criaTabelaHighScore (STR_Score* scores); /*Função que atualiza a tabela com os melhores scores ao final de uma partida.*/
static void inicializaScore(STR_Score* scores); /* Função que inicializa a estrutura que contabilizará pontos assim que o evento anuncia o início de um jogo.*/
static int addScore (char action, int player, STR_Score* scores); /*Função que modifica a pontuação de um personagem (player 1,2,...) na posição do vetor de acordo com a origem da pontuação (explosão de soft block, morte de um monstro ou coleta de powerup).*/
/**
* @brief Essa função tem como função retornar a pontuação de um jogador para outros módulos.
* 
* @param jogador o jogador da qual deseja-se obter a pontuação.
* @return a função retorna 1 caso dê algum erro (jogador inexistente) e retorna um valor diferente de um caso tudo tenha corrido bem.
*/
EXT_MOD_SCORE int MS_getScore (int jogador); /*Função que retorna a pontuação de um jogador. Referência para outros módulos.*/
static void clearScore (STR_Score* scores); /*zera o score dos personagens no final de uma partida.*/

#endif
