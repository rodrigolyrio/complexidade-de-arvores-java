import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class GeradorArquivosOrdenados{

    private static final int NUM_REGISTROS = 2000000; // Quantidade de registros a serem gerados
    private static final String NOME_ARQUIVO = "SeriesBalanceadas.txt";

    private static final String[] NOMES_SERIE = {
            "O Mistério de", "A Herança de", "Crônicas de", "Segredos em", "Ventos de",
            "O Último", "Além de", "Caminhos de", "Diários de", "Império de",
            "A Lenda de", "Fronteiras de", "Destino em", "Sombras de", "Relatos de"
    };

    private static final String[] SUBSTANTIVOS_SERIE = {
            "Dinastia", "Neve", "Justiça", "Papel", "Sangue", "Poder", "Liberdade",
            "Honra", "Família", "Guerra", "Esperança", "Traição", "Vingança", "Glória",
            "Tempo", "Destino", "Amazonas", "Coroação", "Sertão", "Cidade"
    };

    private static final String[] PAISES = {
            "Brasil", "Portugal", "Angola", "Moçambique", "Cabo Verde", "Timor-Leste",
            "Guiné-Bissau", "Guiné Equatorial", "EUA", "Reino Unido", "Espanha", "França",
            "Japão", "Coréia do Sul", "China"
    };
    
    public static void main(String[] args) {
        gerarArquivo();
    }
    
    private static void gerarArquivo() {
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, StandardCharsets.UTF_8))) {
            writer.write(NUM_REGISTROS + "\n");

            for (int i = 0; i < NUM_REGISTROS; i++) {
                String nome = gerarNomeSerie(random);
                int anoLancamento = 1980 + random.nextInt(47);
                String pais = PAISES[random.nextInt(PAISES.length)];
                writer.write(nome + ";" + anoLancamento + ";" + pais + "\n");

                if ((i + 1) % 1_000_000 == 0) {
                    System.out.println((i + 1) + " registros gerados...");
                }
            }
            
            System.out.println("Arquivo gerado com sucesso: " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    private static String gerarNomeSerie(Random random) {
        String nome_serie = NOMES_SERIE[random.nextInt(NOMES_SERIE.length)];
        String substantivo_serie = SUBSTANTIVOS_SERIE[random.nextInt(SUBSTANTIVOS_SERIE.length)];
        return nome_serie + " " + substantivo_serie;
    }
}
