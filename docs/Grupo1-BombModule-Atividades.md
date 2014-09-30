* Bomb Module

* Equipe 1:

		Breno (8220-4619)
		Denis (9118-5543)
		Diego (8217-6094)
		Jefferson (8201-2251)

* Forma de Comunicação

1. Grupo Whatsapp

* Divisão de Tarefas

a) Todos

Criar casos de testes para os médotos que tratar
Documentar código

b) Breno

Tratar as seguintes responsabilidades:

	Limita o número de bombs ativas (não disparadas) que podem ser deixadas por um mesmo Character no Grid, segundo o limite do Character
	Registra um timer para a Bomb quando esta for inserida no Grid.

c) Denis

Tratar as seguintes responsabilidades:

	Responsável pela criação das bombs no Grid.
	Trata eventos TimeOutEvent de bombs, dispando-as.
		Bombs são disparadas após 90 turnos.

d) Diego

Tratar as seguintes responsabilidades:

	Trata a criação de bombs:
	são criadas com o mesmo bombRange do seu criador (BombDropper).
	ExplosionStartedEvents são criados criados com bombRange da Bomb que originou o evento.

e) Jefferson

Tratar as seguintes responsabilidades:

	Dispara bombs que estiverem no range de uma explosão (tratando o InAnExplosionEvent)
		Disparar Bomb
		Cria um evento ExplosionStartedEvent e remove a Bomb do Grid.
	
