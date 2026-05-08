// App que usará a árvore nativa do java.

package br.ifes.tpa.app;

import br.ifes.tpa.dominio.ComparatorSeriesPorNome;
import br.ifes.tpa.dominio.Series;
//import br.ifes.tpa.biblioteca.IColecao;
//import br.ifes.tpa.listaencadeada.ListaEncadeada;
import br.ifes.tpa.util.MedidorTempo;
import br.ifes.tpa.util.ResultadoMedicao;
//import br.ifes.tpa.listaencadeada.ListaEncadeadaArrayList;
//import br.ifes.tpa.listaencadeada.ListaEncadeadaLinkedList;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeSet;

public class AppJavaStandard {

    public static void carregarDados(TreeSet<Series> l) { //Carrega o arquivo de texto
        try {
            ResultadoMedicao<Integer> resultado = MedidorTempo.medirComRetorno(() -> { //Ao identificar o arquivo, já é iniciado o cálculo do tempo
                int quantidade = 0;

                try (BufferedReader buff = new BufferedReader(new FileReader("series_100k.txt"))) { //Localização do arquivo
                    System.out.println("Carregando dados do arquivo...");
                    String linha;

                    while ((linha = buff.readLine()) != null) { //Enquanto a linha não estiver vazia
                        if (linha.trim().isEmpty()) { //remove os espaços vazios da lista
                            continue;
                        }

                        String[] partes = linha.split(";"); //Divide a linha a cada ";" encontrada

                        if (partes.length == 3) { //se forem encontradas 3 partes separadas por ";" na linha
                            String nome = partes[0];
                            int ano = Integer.parseInt(partes[1]);
                            String pais = partes[2];

                            Series s = new Series(nome, ano, pais);
                            l.add(s);
                            quantidade++;
                        }
                    }

                } catch (IOException | NumberFormatException e) {
                    throw new RuntimeException(e);
                }

                return quantidade;
            });

            System.out.println("Dados carregados com sucesso.");
            System.out.println("Quantidade de séries carregadas: " + resultado.getResultado());
            MedidorTempo.imprimirTempo("Tempo de leitura do arquivo e inserção na árvore", resultado.getTempoNano());

        } catch (RuntimeException e) {
            if (e.getCause() instanceof FileNotFoundException) {
                System.out.println("Arquivo 'dados.txt' não encontrado. Iniciando com lista vazia.\n");
            } else {
                System.out.println("Erro ao ler o arquivo: " + e.getCause().getMessage());
            }
        }
    }

    //Início do Menu

    public static void main(String[] args) {
        TreeSet<Series> l = new TreeSet<>(new ComparatorSeriesPorNome());
        carregarDados(l);

        int ano, resp;
        String nome, pais;
        Scanner scanner = new Scanner(System.in);

        try {
            do {
                String msg = "*** MENU JAVA TREESET ***\n" +
                        "Escolha uma opção:\n" +
                        "1) Adicionar uma Série\n" +
                        "2) Remover uma Série\n" +
                        "3) Pesquisar uma Série\n" +
                        "4) Listar Séries\n" +
                        "5) Quantidade de nós\n" +
                        "0) Sair\n";

                System.out.println(msg);
                System.out.print("Sua opção: ");
                resp = scanner.nextInt();
                scanner.nextLine();

                if (resp < 0 || resp > 5) {
                    System.out.println("\nOpção inválida. Tente novamente.\n");
                    continue;
                }

                switch (resp) {
                    case 1: //Adicionando a Série
                        System.out.println("Digite o nome da Série:");
                        nome = scanner.nextLine();

                        System.out.println("Digite o ano da Série:");
                        ano = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Digite o país da Série:");
                        pais = scanner.nextLine();

                        Series novaSerie = new Series(nome, ano, pais);

                        long tempoAdicionar = MedidorTempo.medir(() -> l.add(novaSerie)); //Tempo de adição (Apesar de sempre funcionar, seu foco é durante o uso do arquivo)

                        System.out.println("Série adicionada com sucesso.");
                        MedidorTempo.imprimirTempo("Tempo de inserção", tempoAdicionar);
                        break;

                    case 2: //Removendo uma série
                        System.out.println("Digite o nome da Série a ser removida:");
                        nome = scanner.nextLine();

                        Series chaveRemocao = new Series(nome, 0, "");

                        ResultadoMedicao<Boolean> resultadoRemocao = MedidorTempo.medirComRetorno(() -> l.remove(chaveRemocao)); //Tempo de remoção

                        if (resultadoRemocao.getResultado()) {
                            System.out.println("Série removida com sucesso.");
                        } else {
                            System.out.println("Série não existe.");
                        }

                        MedidorTempo.imprimirTempo("Tempo de remoção", resultadoRemocao.getTempoNano());
                        break;

                    case 3: //Buscando uma Série
                        System.out.println("Digite o nome da Série a ser procurada:");
                        nome = scanner.nextLine();

                        Series chavePesquisa = new Series(nome, 0, "");

                        ResultadoMedicao<Series> resultadoPesquisa = MedidorTempo.medirComRetorno(() -> l.floor(chavePesquisa)); //floor retorna o elemento menor ou igual ao elemento pesquisado

                        Series serieEncontrada = l.floor(chavePesquisa);

                        if (serieEncontrada == null) {
                            System.out.println("Série não existe.");
                        } else {
                            System.out.println("Série encontrada: " + serieEncontrada);
                        }

                        MedidorTempo.imprimirTempo("Tempo de pesquisa", resultadoPesquisa.getTempoNano());
                        break;

                    case 4:
                        System.out.println("Listagem das Séries:");
                        l.forEach(System.out::println);
                        break;

                    case 5:
                        ResultadoMedicao<Integer> resultadoQuantidade = MedidorTempo.medirComRetorno(() -> l.size()); //Executa o código para imprimir a quantidade de nós e o tempo corrido

                        System.out.println("Quantidade de nós: " + resultadoQuantidade.getResultado());
                        MedidorTempo.imprimirTempo("Tempo do .size", resultadoQuantidade.getTempoNano());
                        break;

                    case 0:
                        System.out.println("Saindo do programa..."); //
                        break;
                }

            } while (resp != 0);

        } catch (Exception e) {
            System.out.println("ERRO! " + e.getMessage());
        } finally {
            scanner.close();
        }

        System.out.println("Programa encerrado.");
    }
}