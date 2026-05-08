// App que utiliza a biblioteca de árvores personalizada (Custom Library).
package br.ifes.tpa.app;

import br.ifes.tpa.biblioteca.ArvoreAVL;
import br.ifes.tpa.biblioteca.IColecao;
import br.ifes.tpa.dominio.ComparatorSeriesPorNome;
import br.ifes.tpa.dominio.Series;
import br.ifes.tpa.util.MedidorTempo;
import br.ifes.tpa.util.ResultadoMedicao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Aplicativo que gerencia a coleção de Séries usando a ArvoreAVL.
 * Criado para comparar o desempenho com a árvore nativa do Java.
 **/
public class AppCustomLib {

    // Método para carregar os dados do arquivo .txt
    public static void carregarDados(IColecao<Series> colecao) {
        try {
            // Mede o tempo total de leitura e inserção de todos os registros
            ResultadoMedicao<Integer> resultado = MedidorTempo.medirComRetorno(() -> {
                int quantidade = 0;
                // Dica: Verifique se o nome do arquivo gerado pelo seu Gerador coincide com este
                try (BufferedReader buff = new BufferedReader(new FileReader("series_100k.txt"))) {
                    System.out.println("Carregando dados do arquivo...");
                    String linha;

                    while ((linha = buff.readLine()) != null) {
                        if (linha.trim().isEmpty()) continue;

                        String[] partes = linha.split(";");

                        if (partes.length == 3) {
                            String nome = partes[0];
                            int ano = Integer.parseInt(partes[1]);
                            String pais = partes[2];

                            colecao.adicionar(new Series(nome, ano, pais));
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
            MedidorTempo.imprimirTempo("Tempo total de carregamento (Leitura + Inserção)", resultado.getTempoNano());

        } catch (RuntimeException e) {
            if (e.getCause() instanceof FileNotFoundException) {
                System.out.println("Arquivo 'series_100k.txt' não encontrado. Iniciando com árvore vazia.\n");
            } else {
                System.out.println("Erro ao ler o arquivo: " + e.getCause().getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Instanciação da ArvoreAVL com o Comparator por Nome
        ArvoreAVL<Series> colecao = new ArvoreAVL<>(new ComparatorSeriesPorNome());

        // Carregamento inicial dos dados [Etapa 5 e 7 da especificação]
        carregarDados(colecao);

        int ano, resp;
        String nome, pais;
        Scanner scanner = new Scanner(System.in);

        try {
            do {
                System.out.println("*** MENU BIBLIOTECA PERSONALIZADA (AVL) ***");
                System.out.println("1) Adicionar uma Série");
                System.out.println("2) Remover uma Série");
                System.out.println("3) Pesquisar uma Série (Pior Caso)");
                System.out.println("4) Listar Séries (Em Ordem)");
                System.out.println("5) Quantidade de nós");
                System.out.println("6) Altura da árvore");
                System.out.println("7) Caminhamento em Nível");
                System.out.println("0) Sair");
                System.out.print("Sua opção: ");

                resp = scanner.nextInt();
                scanner.nextLine(); // Consome a quebra de linha

                switch (resp) {
                    case 1: // Adicionar
                        System.out.print("Nome: ");
                        nome = scanner.nextLine();
                        System.out.print("Ano: ");
                        ano = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("País: ");
                        pais = scanner.nextLine();

                        Series nova = new Series(nome, ano, pais);
                        long tAdd = MedidorTempo.medir(() -> colecao.adicionar(nova));

                        System.out.println("Série adicionada.");
                        MedidorTempo.imprimirTempo("Tempo de inserção", tAdd);
                        break;

                    case 2: // Remover
                        System.out.print("Nome para remover: ");
                        nome = scanner.nextLine();
                        Series chaveRem = new Series(nome, 0, "");

                        ResultadoMedicao<Boolean> resRem = MedidorTempo.medirComRetorno(() -> colecao.remover(chaveRem));

                        System.out.println(resRem.getResultado() ? "Removida!" : "Não encontrada.");
                        MedidorTempo.imprimirTempo("Tempo de remoção", resRem.getTempoNano());
                        break;

                    case 3: // Pesquisar
                        System.out.print("Nome para pesquisar: ");
                        nome = scanner.nextLine();
                        Series chavePesq = new Series(nome, 0, "");

                        // Medição obrigatória para o relatório [Etapa 5]
                        ResultadoMedicao<Series> resPesq = MedidorTempo.medirComRetorno(() -> colecao.pesquisar(chavePesq));

                        System.out.println(resPesq.getResultado() != null ? "Achou: " + resPesq.getResultado() : "Não existe.");
                        MedidorTempo.imprimirTempo("Tempo de pesquisa", resPesq.getTempoNano());
                        break;

                    case 4: // Listar
                        System.out.println("Séries em ordem:");
                        System.out.println(colecao); // Chama toString/caminharEmOrdem
                        break;

                    case 5: // Quantidade de nós
                        ResultadoMedicao<Integer> resQtd = MedidorTempo.medirComRetorno(colecao::quantidadeNos);
                        System.out.println("Total de nós: " + resQtd.getResultado());
                        MedidorTempo.imprimirTempo("Tempo de quantidadeNos", resQtd.getTempoNano());
                        break;

                    case 6: // Altura
                        ResultadoMedicao<Integer> resAlt = MedidorTempo.medirComRetorno(colecao::altura);
                        System.out.println("Altura atual: " + resAlt.getResultado());
                        MedidorTempo.imprimirTempo("Tempo de altura", resAlt.getTempoNano());
                        break;

                    case 7: // Nível
                        ResultadoMedicao<String> resNiv = MedidorTempo.medirComRetorno(colecao::caminharEmNivel);
                        System.out.println("Caminhamento em nível:");
                        System.out.println(resNiv.getResultado());
                        MedidorTempo.imprimirTempo("Tempo de caminhamento em nível", resNiv.getTempoNano());
                        break;

                    case 0:
                        System.out.println("Encerrando...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } while (resp != 0);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}