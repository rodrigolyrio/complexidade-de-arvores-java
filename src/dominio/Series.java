package br.ifes.tpa.dominio;

public class Series {
    private String nome;
    private int anoLancamento;
    private String pais;

    public Series(String nome, int anoLancamento, String pais) {
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.pais = pais;
    }

    public String getNome() { return nome; }
    public int getAnoLancamento() { return anoLancamento; }
    public String getPais() { return pais; }

    @Override
    public String toString() {
        return String.format("Série: %-20s | Ano: %d | País: %s", nome, anoLancamento, pais);
    }
}