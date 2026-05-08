import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.ArrayList;

public class GeradorArquivosBalanceados {
    private static final int NUM_REGISTROS = 400000;
    private static final String NOME_ARQUIVO = "400k_SeriesBalanceadas.txt";

    // Parte 1: Início do nome
    private static final String[] PARTE1 = {
            "O Mistério", "Crônicas", "Império", "Segredos", "Legado", "A Jornada", "O Código", "Sombras", "Luz", "Ventos",
            "Chamas", "O Trono", "Destino", "Fronteiras", "Aliança", "O Retorno", "A Queda", "Ascensão", "Mundos", "Ecos",
            "Relíquias", "O Portal", "Sentença", "Horizonte", "Origens", "Caminhos", "O Enigma", "Profecia", "Vingança", "Justiça",
            "A Busca", "Labirinto", "Espelhos", "Silêncio", "Gritos", "O Pacto", "Herdeiros", "Ruínas", "Estrelas", "Abismo",
            "O Vale", "A Torre", "Cidade", "Santuário", "O Guardião", "Poeira", "Raízes", "O Círculo", "Memórias", "Visões",
            "O Desafio", "Heróis", "Além", "Refúgio", "Impulso", "Cinzas", "Espectros", "O Alvorecer", "Túnel", "Labaredas",
            "O Despertar", "Fragmentos", "Símbolos", "O Vazio", "Relatos", "Pegadas", "O Brilho", "Som", "Rastro", "O Elo",
            "A Chama", "O Gelo", "O Suspiro", "A Lenda", "O Radar", "Cicatrizes", "O Vulto", "A Arca", "O Plano", "Alvos"
    };

    // Parte 2: Meio do nome (já contém o espaço inicial)
    private static final String[] PARTE2 = {
            " de Prata", " de Ouro", " de Sangue", " de Ferro", " de Gelo", " de Cristal", " da Noite", " do Sol", " da Lua", " do Mar",
            " da Terra", " do Fogo", " do Tempo", " do Espaço", " da Alma", " do Medo", " da Esperança", " do Caos", " da Ordem", " do Reino",
            " de Marte", " de Vênus", " de Titã", " do Norte", " do Sul", " do Leste", " do Oeste", " da Selva", " do Deserto", " da Montanha",
            " de Alexandria", " de Avalon", " de Atlântida", " do Olimpo", " de Asgard", " de Gotham", " de Metrópolis", " de Tóquio", " de Roma", " de Paris",
            " de Londres", " de Berlim", " de Moscou", " de Pequim", " de Sydney", " de Cairo", " de Dubai", " de Madrid", " de Lisboa", " de Viena",
            " do Infinito", " do Além", " do Passado", " do Futuro", " das Sombras", " da Glória", " do Destino", " da Coragem", " do Esquecimento", " da Vitória",
            " de Júpiter", " de Saturno", " do Éter", " do Abismo", " da Alvorada", " do Crepúsculo", " das Estrelas", " do Cosmos", " da Razão", " do Instinto",
            " do Horizonte", " da Mente", " do Poder", " da Verdade", " do Silêncio", " do Trovão", " da Tempestade", " do Desejo", " da Honra", " do Amanhã"
    };

    // Parte 3: Final do nome (já contém o espaço inicial)
    private static final String[] PARTE3 = {
            " Perdido", " Eterno", " Proibido", " Esquecido", " Sombrio", " Iluminado", " Antigo", " Moderno", " Oculto", " Revelado",
            " Divino", " Profano", " Sagrado", " Maldito", " Secreto", " Público", " Final", " Inicial", " Infinito", " Mortal",
            " Invencível", " Frágil", " Poderoso", " Silencioso", " Barulhento", " Calmo", " Furioso", " Veloz", " Lento", " Distante",
            " Próximo", " Escuro", " Brilhante", " Gelado", " Quente", " Árido", " Fértil", " Místico", " Mágico", " Real",
            " Imaginário", " Virtual", " Físico", " Espiritual", " Único", " Raro", " Comum", " Nobre", " Rebelde", " Glorioso",
            " Supremo", " Absoluto", " Lendário", " Mítico", " Destemido", " Incrível", " Notável", " Obscuro", " Radiante", " Tenebroso",
            " Vibrante", " Estático", " Fluido", " Sólido", " Efêmero", " Duradouro", " Sagaz", " Audaz", " Voraz", " Capaz",
            " Letal", " Vital", " Crucial", " Brutal", " Sutil", " Ágil", " Hostil", " Fiel", " Instável", " Intenso"
    };

    private static final String[] PAISES = {
            "Brasil", "EUA", "Alemanha", "Espanha", "Reino Unido", "Canada", "França", "Coreia do Sul", "Japao", "Australia"
    };

    public static void main(String[] args) {
        gerarArquivo();
    }

    private static void gerarArquivo() {
        Random random = new Random();
        ArrayList<Integer> ids = new ArrayList<>();
        gerarIdentificadoresBalanceados(ids, 1, NUM_REGISTROS);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, StandardCharsets.UTF_8))) {
            // O cabeçalho com o número de registros é importante para o leitor
            writer.write(NUM_REGISTROS + "\n");

            for (int i = 0; i < NUM_REGISTROS; i++) {
                int id = ids.get(i);
                String nome = gerarNomeSerie(random);
                int anoLancamento = 1950 + random.nextInt(75); // Intervalo 1950-2024
                String pais = PAISES[random.nextInt(PAISES.length)];

                // Formato: id;nome;ano;pais
                writer.write(id + ";" + nome + ";" + anoLancamento + ";" + pais + "\n");

                if ((i + 1) % 100_000 == 0) {
                    System.out.println((i + 1) + " registros gerados...");
                }
            }
            System.out.println("Arquivo gerado com sucesso: " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    // Mantém a lógica de IDs balanceados para evitar árvores degeneradas
    private static void gerarIdentificadoresBalanceados(ArrayList<Integer> ids, int inicio, int fim) {
        if (inicio > fim) return;
        int meio = (inicio + fim) / 2;
        ids.add(meio);
        gerarIdentificadoresBalanceados(ids, inicio, meio - 1);
        gerarIdentificadoresBalanceados(ids, meio + 1, fim);
    }

    // Novo método que combina as 3 partes conforme o Python
    private static String gerarNomeSerie(Random random) {
        String p1 = PARTE1[random.nextInt(PARTE1.length)];
        String p2 = PARTE2[random.nextInt(PARTE2.length)];
        String p3 = PARTE3[random.nextInt(PARTE3.length)];
        return p1 + p2 + p3;
    }
}