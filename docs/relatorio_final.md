Primeira seção do relatório: relato sobre o desenvolvimento da biblioteca e do programa de teste

Ronnald Willian:

- Atuação desenvolvendo a parte 01:
  - Criado o arquivo NoArvore.java: Nele foi desenvolvido o campo *altura* no *NoArvore.java*, pois na *ArvoreAVL* será necessário saber a altura de cada nó individualmente. Para calcular o fator de balanceamento e decidir quando e como rotacionar. Se esse campo não existir no nó, a AVL teria que calcular a altura recursivamente toda vez, o que degradaria o desempenho ou forçaria uma refatoração da classe.
  - O construtor de ArvoreBinaria, "Comparator": O comparador acessível em todos os métodos de ArvoreBinaria sem precisar ser declarado novamente. É ele que determina como dois objetos são comparados em toda operação da árvore.
    - Ainda dentro de ArvoreBinaria:
      - Método adicionar
      - Método pesquisar
      - Método remover
      - Método altura
      - Método caminharEmNível
      - Método caminharEmOrdem

5) Terceira seção do relatório: Análise Empírica de complexidade de Algoritmos da sua biblioteca de árvores
  - Ler um arquivo,inserindo em uma árvore os elementos representados no arquivo, e ao final da criação da árvore com todos os elementos do arquivo deve exibir o tempo total que levou para ler todos os registros e inserir todos na árvore. Ou seja, deve fazer exatamente o mesmo que já fazia, porém usando uma árvore ao invés de lista.

  - Deve fazer uma busca pela folha mais distante da raiz(utilizando o método pesquisar) e exibir o tempo gasto para a execução do método pesquisar. (Isto seria o equivalente a buscar o último elemento da lista do trabalho anterior, ou seja, o pior caso da estrutura)

  - Deve fazer uma busca por um elemento que não exista na árvore e que, se existisse, seria filho de um dos nós mais distantes da raiz(utilizando o método pesquisar) e exibir o tempo gasto para a execução do método pesquisar. (Seria equivalente à busca por um elemento que não existe na lista)

  - Deve excluir a folha mais distante da raiz (utilizando o método remover) e exibir o tempo gasto para a execução do método remover. (equivalente a remover o último elemento da lista no trabalho anterior)

  - Deve imprimir a quantidade de elementos da árvore (utilizando o método quantidadeNos) e exibir o tempo gasto para a execução do método quantidadeNos.