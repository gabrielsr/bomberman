Resumo: este arquivo comprimido contém o módulo de definição e o módulo de implementação do módulo Score. Este módulo e suas funções tem como objetivo contabilizar a pontuação dos characters do jogo, (quando eventos relevantes acontecem (destruição de um soft block, aquisição de um power up, destruição de um monstro ou destruição de outro personagem) a pontuação do personagem é aumentada), fornecer os dados da pontuação para outros módulos se necessário e criar e organizar (sempre que preciso) uma tabela de high scores.

Compilação e execução: o programa, em ambiente Linux, pode ser compilado por GCC e seus casos de teste podem ser executados através dos comandos digitados no terminal:
	gcc -c scorebomberman.c
	make (do arquivo makefile que foi enviado no arquivo)
	
Descrição: existem 3 funções externadas pelo módulo: scoreModuleInterface, sendPlayerNameEvent, getScore. Como este programa não fará chamadas, espera-se que, ao chamar scoreModuleInterface os outros módulos enviem uma lista de structs constituída de dois inteiros e um ponteiro para a próxima célula da lista. Os dois inteiros representarão, respectivamente, um comando (0 a 5) e um jogador (1 ao número máximo de jogadores). Cada comando representará um tipo de ocorrência. Ex: o comando '0' representa a inicialização do jogo, e, quando recebido este comando, a função irá inicializar o contador para contar os pontos dos jogadores. Para este caso, o número do jogador enviado não faz diferença. Segue abaixo o que representa cada valor de comando:
0 - inicialização do jogo;
1 - deve ser enviado com o número válido de um jogador 'x'. Diz que esse jogador 'x' destruiu um monstro.
2 -	deve ser enviado com o número válido de um jogador 'x'. Diz que esse jogador 'x' destruiu um soft block.
3 -	deve ser enviado com o número válido de um jogador 'x'. Diz que esse jogador 'x' pegou um power up.
4 -	deve ser enviado com o número válido de um jogador 'x'. Diz que esse jogador 'x' destruiu um outro jogador.
5 - o jogo acabou, a tabela de high scores pode ser criada/atualizada e a pontuação reinicializada.
Além dessa função, a função sendPlayerNameEvent precisa ser chamada pelo módulo responsável toda vez que se obter o nome dos jogadores. Essa função recebe como argumento uma lista que irá conter o nome dos jogadores com o seu número associado (montados em uma struct: inteiro com o número do jogador, string com o nome desse jogador e ponteiro para a próxima célula). Sem essa informação é impossível montar a tabela de high scores. Quando enviado corretamente, essa função irá servir como função auxiliar para a função que cria o arquivo binário contendo os dados da tabela.
Finalmente, a última função externada do módulo é a função getScore, que retorna a pontuação de um jogador desejado caso necessário. É necessário apenas enviar como argumento o número do jogador da qual se deseja saber a pontuação. 
Para todas as funções acima, há um padrão: caso ocorra algum erro (jogador/comando inexistente enviado como argumento, por exemplo), a função retorna '1'. Caso contrário, a função retorna '0', ou no caso de getScore, retorna um valor maior que '0' e diferente de '1'. 
É importante mencionar que o tipo abstrato de dados criado para esse módulo pode variar de acordo com o número de jogadores estabelecido. É necessário apenas mudar a constante PLAYER_MAX.
Existe uma função no módulo que cria uma tabela de high scores a partir dos dados já obtidos e os dados atuais da partida. A tabela é armazenada em um arquivo binário, chamado de 'scores.bin'. Ele depois pode ser acessado por qualquer módulo que precise das informações nele presente.
As outras funções, internas ao módulo, tem suas descrições disponíveis em comentários no arquivo '.c'.

Autores:

Camila Imbuzeiro Camargo - 13/0104868
Lucas Araújo Pena - 13/0056162
Miguel Angelo Montagner Filho 13/0127302
Nicolas Machado Schumacher 13/0047660

Programação Sistemática - Professora Genaina Nunes Rodrigues

