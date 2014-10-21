/*Universidade de Brasília
Departamento de Ciência da Computação
Programação Sistemática - Professora Genaina Nunes Rodrigues
Testes Trabalho Parte1 - CUnit
Camila Imbuzeiro Camargo - 13/0104868
Lucas Araújo Pena - 13/0056162
Miguel Angelo Montagner Filho 13/0127302
Nicolas Machado Schumacher 13/0047660 */

#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "CUnit/CUnit.h"
#include "CUnit/Basic.h" 
#include "ScoreBomberman.c"

/*
 * Função que adiciona ao registro do CUnit os testes paras as funções 
 * presentes no arquivo testes.c
 */

void adicionar_testes_smo_datas(void);

/*Abaixo estão as funções que efetuam os testes para as funções de 'scorebomberman.h'*/
void testa_findEvent_MenorQueZero (void);
void testa_findEvent_MaiorQueSeis (void);
void testa_findEvent_Comando1Certo (void);
void testa_findEvent_Comando2Errado (void);
void testa_inicializaScore_ParaP1 (void);
void testa_inicializaScore_ParaP2 (void);
void testa_inicializaScore_ParaP3 (void);
void testa_inicializaScore_ParaP4 (void);
void testa_addScore_ParaMorteMonstroP1 (void);
void testa_addScore_ParaDestruicaoSoftBlockP2 (void);
void testa_addScore_ParaPegarPowerUpP3 (void);
void testa_addScore_ParaMortedeOutroCharacterP4 (void);
void testa_addScore_ParaDuplaPontuacaoP1 (void);
void testa_addScore_ParaLetraInexistenteP1(void);
void testa_getScore_P1 (void);
void testa_getScore_P2 (void);
void testa_getScore_P3 (void);
void testa_getScore_P4 (void);
void testa_getScore_JogadorInexistente (void);
void testa_criaTabelaHighScore_TesteValor (void);
void testa_criaTabelaHighScore_TesteNome (void);

/*****************************************************************************************************************************************/
/********************************************Testes para a função findEvent***************************************************************/

void testa_findEvent_MenorQueZero (void) {
	int resultado;
	resultado = findEvent (-1,1);
	CU_ASSERT_TRUE(1==resultado);
}

void testa_findEvent_MaiorQueSeis (void) {
	int resultado;
	resultado = findEvent (7,1);
	CU_ASSERT_TRUE(1==resultado);
}

void testa_findEvent_Comando1Certo (void) {
	int resultado;
	resultado = findEvent (1,2);
	CU_ASSERT_TRUE(0==resultado);
}

void testa_findEvent_Comando2Errado (void) {
	int resultado;
	resultado = findEvent (2,-1);
	CU_ASSERT_TRUE(1==resultado);
}

/*****************************************************************************************************************************************/
/****************************************Testes para a função inicializaScore*************************************************************/

void testa_inicializaScore_ParaP1 (void) {
	STR_Score scores;
	inicializaScore(&scores);
	CU_ASSERT_TRUE(!scores.pontos[0]);
}

void testa_inicializaScore_ParaP2 (void) {
	STR_Score scores;
	inicializaScore(&scores);
	CU_ASSERT_TRUE(!scores.pontos[1]);
}

void testa_inicializaScore_ParaP3 (void) {
	STR_Score scores;
	inicializaScore(&scores);
	CU_ASSERT_TRUE(!scores.pontos[2]);
}

void testa_inicializaScore_ParaP4 (void) {
	STR_Score scores;
	inicializaScore(&scores);
	CU_ASSERT_TRUE(!scores.pontos[3]);
}

/*****************************************************************************************************************************************/
/******************************************Testes para a função addScore *****************************************************************/

void testa_addScore_ParaMorteMonstroP1(void) {
	STR_Score scores;
	int i;
	int resultado;
	inicializaScore(&scores);
	addScore('m',1,&scores);
	resultado = scores.pontos[0];
	CU_ASSERT_TRUE(100==resultado);
}

void testa_addScore_ParaDestruicaoSoftBlockP2(void) {
	STR_Score scores;
	int i;
	int resultado;
	inicializaScore(&scores);
	addScore('s',2,&scores);
	resultado = scores.pontos[1];
	CU_ASSERT_TRUE(10==resultado);
}

void testa_addScore_ParaPegarPowerUpP3(void) {
	STR_Score scores;
	int i;
	int resultado;
	inicializaScore(&scores);
	addScore('p',3,&scores);
	resultado = scores.pontos[2];
	CU_ASSERT_TRUE(30==resultado);
}

void testa_addScore_ParaMortedeOutroCharacterP4(void) {
	STR_Score scores;
	int i;
	int resultado;
	inicializaScore(&scores);
	addScore('c',4,&scores);
	resultado = scores.pontos[3];
	CU_ASSERT_TRUE(200==resultado);
}

void testa_addScore_ParaDuplaPontuacaoP1(void) {
	STR_Score scores;
	int i;
	int resultado;
	inicializaScore(&scores);
	addScore('m',1,&scores);
	addScore('s',1,&scores);
	resultado = scores.pontos[0];
	CU_ASSERT_TRUE(110==resultado);
}

void testa_addScore_ParaLetraInexistenteP1(void) {
	STR_Score scores;
	int resultado;
	inicializaScore(&scores);
	resultado = addScore('t',1,&scores);
	CU_ASSERT_TRUE(1==resultado);
}

/*****************************************************************************************************************************************/
/****************************************Testes para a funçã MS_getScore (e findEvent implicitamente)***********************************/

void testa_getScore_P1 (void) {
	int resultado;
	findEvent(0,1);
	findEvent(1,1);
	resultado = MS_getScore(1);
	CU_ASSERT_TRUE(100==resultado);
}

void testa_getScore_P2 (void) {
	int resultado;
	findEvent(0,1);
	findEvent(2,2);
	resultado = MS_getScore(2);
	CU_ASSERT_TRUE(10==resultado);
}

void testa_getScore_P3 (void) {
	int resultado;
	findEvent(0,1);
	findEvent(3,3);
	resultado = MS_getScore(3);
	CU_ASSERT_TRUE(30==resultado);
}

void testa_getScore_P4 (void) {
	int resultado;
	findEvent(0,1);
	findEvent(4,4);
	resultado = MS_getScore(4);
	CU_ASSERT_TRUE(200==resultado);
}

void testa_getScore_JogadorInexistente (void) {
	int resultado;
	findEvent(0,1);
	findEvent(4,4);
	resultado = MS_getScore(5);
	CU_ASSERT_TRUE(1==resultado);
}

/*****************************************************************************************************************************************/
/**********************************************Testes para a função criaTabelaHighScore***************************************************/

void testa_criaTabelaHighScore_TesteValor (void) {
	STR_Score scores;
	LST_Name* names = malloc (sizeof (LST_Name));
	FILE* f;
	char* teste;
	int resultado;
	
	scores.pontos[0]=100;
	scores.pontos[1]=100;
	scores.pontos[2]=100;
	scores.pontos[3]=100;
	
	names->pl=1;
	strcpy(names->nombre,"Camila");
	names->prox = malloc (sizeof (LST_Name));
	
	names->prox->pl=2;
	strcpy(names->prox->nombre,"Lucas");
	names->prox->prox = malloc (sizeof (LST_Name));
	
	names->prox->prox->pl=3;
	strcpy(names->prox->prox->nombre,"Miguel");
	names->prox->prox->prox = malloc (sizeof (LST_Name));
	
	names->prox->prox->prox->pl=4;
	strcpy(names->prox->prox->prox->nombre,"Nicolas");
	names->prox->prox->prox->prox = NULL;
	
	MS_sendPlayerNameEvent(names);
	criaTabelaHighScore(&scores);
	f = fopen ("scores.bin","rb");
	fread(teste,sizeof(char),20,f);
	fread(&resultado,sizeof(int),1,f);
	
	CU_ASSERT_TRUE (resultado==100);
}

void testa_criaTabelaHighScore_TesteNome (void) {
	STR_Score scores;
	LST_Name* names = malloc (sizeof (LST_Name));
	FILE* f;
	char teste[20]; 
	int resultado,j;
	
	scores.pontos[0]=100;
	scores.pontos[1]=100;
	scores.pontos[2]=100;
	scores.pontos[3]=100;
	
	names->pl=1;
	strcpy(names->nombre,"Camila");
	names->prox = malloc (sizeof (LST_Name));
	
	names->prox->pl=2;
	strcpy(names->prox->nombre,"Lucas");
	names->prox->prox = malloc (sizeof (LST_Name));
	
	names->prox->prox->pl=3;
	strcpy(names->prox->prox->nombre,"Miguel");
	names->prox->prox->prox = malloc (sizeof (LST_Name));
	
	names->prox->prox->prox->pl=4;
	strcpy(names->prox->prox->prox->nombre,"Nicolas");
	names->prox->prox->prox->prox = NULL;
	
	MS_sendPlayerNameEvent(names);
	criaTabelaHighScore(&scores);
	f = fopen ("scores.bin","rb");
	fread(teste,sizeof(char),20,f);
	resultado = strcmp(teste,names->prox->prox->prox->nombre);
	fclose(f);
	CU_ASSERT_TRUE (resultado);
}

/*****************************************************************************************************************************************/

void adicionar_testes_smo_datas(void) {
	CU_pSuite suite;
	
	/*Cria uma suite que conterá todos os testes*/
	suite = CU_add_suite("Testes da smo_datas",NULL,NULL);
		
	/*Adiciona os testes para a função findEvent*/
	CU_ADD_TEST(suite,testa_findEvent_MenorQueZero);
	CU_ADD_TEST(suite,testa_findEvent_MaiorQueSeis);
	CU_ADD_TEST(suite,testa_findEvent_Comando1Certo);
	CU_ADD_TEST(suite,testa_findEvent_Comando2Errado);
	
	/*Adiciona os testes para a função inicializaScore*/
	CU_ADD_TEST(suite,testa_inicializaScore_ParaP1);
	CU_ADD_TEST(suite,testa_inicializaScore_ParaP2);
	CU_ADD_TEST(suite,testa_inicializaScore_ParaP3);
	CU_ADD_TEST(suite,testa_inicializaScore_ParaP4);
	
	/*Adiciona os testes para a função addScore*/
	CU_ADD_TEST(suite,testa_addScore_ParaMorteMonstroP1);
	CU_ADD_TEST(suite,testa_addScore_ParaDestruicaoSoftBlockP2);
	CU_ADD_TEST(suite,testa_addScore_ParaPegarPowerUpP3);
	CU_ADD_TEST(suite,testa_addScore_ParaMortedeOutroCharacterP4);
	CU_ADD_TEST(suite,testa_addScore_ParaDuplaPontuacaoP1);
	CU_ADD_TEST(suite,testa_addScore_ParaLetraInexistenteP1);
	
	/*Adiciona os testes para a função getScore e implicitamente, da função findEvent*/
	CU_ADD_TEST(suite,testa_getScore_P1);
	CU_ADD_TEST(suite,testa_getScore_P2);
	CU_ADD_TEST(suite,testa_getScore_P3);
	CU_ADD_TEST(suite,testa_getScore_P4);
	CU_ADD_TEST(suite,testa_getScore_JogadorInexistente);
	
	/*Adiciona os testes para a função criaTabelaHighScore e, implicitamente, da função MS_sendPlayerNameEvent*/
	CU_ADD_TEST(suite,testa_criaTabelaHighScore_TesteValor);
	CU_ADD_TEST(suite,testa_criaTabelaHighScore_TesteNome);
	
}

int main (void){
	/*Inicializa o registro de suítes e testes do CUnit*/	
	if (CUE_SUCCESS != CU_initialize_registry())
    	return CU_get_error();
    
    /*Adiciona os testes ao registro*/ 	
   	adicionar_testes_smo_datas();
	/*Muda o modo do CUnit para o modo VERBOSE
	 O modo VERBOSE mostra algumas informacoes a
	 mais na hora da execucao*/
	CU_basic_set_mode(CU_BRM_VERBOSE);
	/*Roda os testes e mostra na tela os resultados*/
	CU_basic_run_tests();
	/*Limpa o registro*/
	CU_cleanup_registry();
	return CU_get_error();
}
