#include <stdio.h>
#include <stdlib.h>


#ifndef MOD_SCORE
#define MOD_SCORE


#ifdef SERVIDOR_SCORE	
#define EXT_MOD_SCORE	
#else
#define EXT_MOD_SCORE extern	
#endif


typedef struct score STR_Score;	/*Estrutura principal que lidará com a incrementação e o armazenamento da pontuação*/
typedef struct recebe LST_Event;/*Struct que supostamente será recebida por outros módulos (a cada turno) para que seja possível atualizar o score*/
typedef struct tabela STR_Tabela;/*Estrutura auxiliar da função criaTabelaHighScore, abaixo*/
typedef struct nomes LST_Name; /*Estrutura que enviará o nome dos jogadores para este módulo*/

EXT_MOD_SCORE int MS_scoreModuleInterface (LST_Event* events); /*Função que recebe uma lista de eventos para poder contabilizar pontos*/
EXT_MOD_SCORE void MS_sendPlayerNameEvent (LST_Name* nomes); /*Esta função deve ser chamada pelo módulo de Events no início do jogo*/
static int findEvent (int comando, int jogador); /*pega os eventos do módulo de eventos para poder contabilizar o score*/
static void criaTabelaHighScore (STR_Score* scores); /*função que atualiza referência para os melhores scores no final de uma partida*/
static void inicializaScore(STR_Score* scores); /*inicializa o vetor que contabilizará pontos assim que o evento anuncia o início de um jogo*/
static int addScore (char action, int player, STR_Score* scores); /*adiciona o score de um personagem (player 1,2,...) na posição do vetor de acordo com a origem da pontuação (explosão de soft block, morte de um monstro ou coleta de powerup)*/
EXT_MOD_SCORE int MS_getScore (int jogador); /*retorna o score de um personagem, disponível para outros módulos obterem o score*/
static void clearScore (STR_Score* scores); /*zera o score dos personagens no final de uma partida*/

#endif
