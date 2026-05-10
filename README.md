# Análise de Complexidade de Árvores Binárias
 
Projeto de análise e implementação construído para a disciplina de **Técnicas de Programação Avançada (TPA)**, no curso de Bacharelado em Sistemas de Informação do **Instituto Federal do Espírito Santo (IFES) - Campus Serra**.

## 📌 Sumário
1. [Introdução](#1-introdução)
2. [Estrutura do Projeto](#2-estrutura-do-projeto)
3. [Tecnologias Utilizadas](#3-tecnologias-utilizadas)
4. [Como Executar](#4-como-executar)
5. [Relatório Final](#5-relatório-final)
6. [Autores](#6-autores)

---

## 1. Introdução
 
O objetivo central deste trabalho é implementar e analisar a complexidade das operações fundamentais de estruturas de **Árvore Binária de Busca (BST)** e **Árvore AVL**, aplicando-as em um sistema de gerenciamento de séries. As operações analisadas são: `adicionar`, `pesquisar`, `remover` e `quantidadeNos`.
 
O projeto explora como a **topologia da árvore** (balanceada vs. degenerada) impacta diretamente a ordem de complexidade dos algoritmos, comparando empiricamente os tempos de execução com arquivos de dados ordenados (que geram árvores degeneradas) e balanceados. 

## 2. Estrutura do Projeto
O código está organizado seguindo padrões de modularização e encapsulamento:

```text
├── src
│   └── br.ifes.tpa
│       ├── app              # Classes principais de execução e medição de desempenho
│       │   ├── AppCustomLib.java      # App usando a biblioteca autoral (ArvoreAVL)
│       │   └── AppJavaStandard.java   # App usando a estrutura nativa TreeSet do Java
│       ├── biblioteca       # Implementação das estruturas de árvore autorais
│       │   ├── ArvoreBinariaBase.java # Classe base abstrata (fornecida pelo professor)
│       │   ├── ArvoreBinaria.java     # Implementação da BST genérica
│       │   ├── ArvoreAVL.java         # Implementação da Árvore AVL com auto-balanceamento
│       │   ├── IColecao.java          # Interface de contrato para as coleções
│       │   └── NoArvore.java          # Modelo do nó da árvore
│       ├── dominio          # Modelagem do objeto de domínio
│       │   ├── Series.java                    # Classe de domínio (nome, ano, país)
│       │   └── ComparatorSeriesPorNome.java    # Critério de indexação por nome
│       └── util             # Classes utilitárias para medição de tempo
│           ├── MedidorTempo.java       # Mede tempo de execução em nanossegundos
│           └── ResultadoMedicao.java   # Encapsula o resultado e o tempo medido
├── generator
│   ├── GeradorArquivosBalanceados.java  # Gera arquivos cuja inserção resulta em BST balanceada
│   ├── GeradorArquivosOrdenados.java    # Gera arquivos com chaves crescentes (BST degenerada)
│   └── LeitorArquivos.java              # Utilitário de leitura dos arquivos gerados
├── data
│   ├── balanceados.java     # Script de referência para geração balanceada
│   └── ordenados.java       # Script de referência para geração ordenada
├── docs
│   └── Relatório TPA 02   # Relatório final do trabalho
├── 100k_SeriesBalanceadas.txt
├── 100k_SeriesOrdenadas.txt
├── 200k_SeriesBalanceadas.txt
├── 200k_SeriesOrdenadas.txt
├── 400k_SeriesBalanceadas.txt
└── 400k_SeriesOrdenadas.txt
```


**Classes-chave:**

- **`ArvoreBinaria.java`**: Implementação autoral da BST genérica com suporte a `Generics` e `Comparator`, herdando de `ArvoreBinariaBase`.
- **`ArvoreAVL.java`**: Estende `ArvoreBinaria`, sobrescrevendo `adicionar` e `remover` para aplicar rotações simples e duplas e manter a árvore sempre balanceada.
- **`IColecao.java`**: Interface que garante interoperabilidade entre as implementações de coleção.
- **`GeradorArquivosBalanceados.java`** / **`GeradorArquivosOrdenados.java`**: Geram os arquivos de entrada que produzem, respectivamente, árvores perfeitamente balanceadas e árvores totalmente degeneradas ao serem inseridos na BST.


## 3. Tecnologias Utilizadas

- **Java 17+**: Linguagem utilizada para o desenvolvimento de todas as estruturas, lógica de balanceamento e medição de desempenho.
- **Generics (`<T>`) + `Comparator<T>`**: Permitem que a árvore armazene qualquer tipo de objeto com critérios de indexação intercambiáveis (ex.: indexar `Series` por nome ou por ano).
- **`TreeSet` (Java Standard Library)**: Estrutura nativa do Java baseada em árvore Rubro-Negra, usada para comparação de desempenho e API na etapa 5 do trabalho.
- **`System.nanoTime()`**: Captura o tempo de execução de cada operação com precisão de nanossegundos, via a classe utilitária `MedidorTempo`.
- **Markdown**: Utilizado para a documentação e estruturação deste README e do relatório final.


## 4. Como Executar
O repositório já contém os arquivos de dados necessários para a análise. Siga os passos abaixo:
 
1. Clone o repositório em seu ambiente local.
2. Abra o projeto em sua IDE Java de preferência (IntelliJ IDEA, Eclipse ou VS Code).
3. Certifique-se de que os arquivos de dados (`*_SeriesBalanceadas.txt` e `*_SeriesOrdenadas.txt`) estejam na **pasta raiz do projeto** para que sejam localizados pela aplicação.
4. Para testar com a **biblioteca autoral (BST / AVL)**:
   - Execute a classe `AppCustomLib.java` localizada no pacote `br.ifes.tpa.app`.
   - Altere o nome do arquivo lido no código para variar entre os arquivos balanceados e ordenados de cada tamanho (100k, 200k, 400k).
5. Para testar com a **biblioteca nativa do Java (`TreeSet`)**:
   - Execute a classe `AppJavaStandard.java` localizada no pacote `br.ifes.tpa.app`.
6. Observe as métricas de tempo de inserção, pesquisa, remoção e contagem impressas no console.
> **Dica:** Para gerar novos arquivos de dados, execute `GeradorArquivosBalanceados.java` ou `GeradorArquivosOrdenados.java` presentes na pasta `generator/`, informando o tamanho desejado.
 


## 5. Relatório Final
A documentação completa com as análises matemáticas linha a linha e a interpretação detalhada dos resultados obtidos pode ser acessada aqui:

👉 **LINK**

## 6. Autores
* **Myllena Furtado Toniato** 
* **Rodrigo Lyrio Rodrigues** 
* **Ronnald Willian** 
