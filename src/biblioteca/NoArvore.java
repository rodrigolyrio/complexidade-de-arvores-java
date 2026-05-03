package br.ifes.tpa.biblioteca;

/**
 * Representa um nó de uma Árvore Binária genérica.
 * Armazena um valor e referências para os filhos esquerdo e direito.
 */
public class NoArvore<T> {
    public T valor;
    public NoArvore<T> esquerda;
    public NoArvore<T> direita;

    /** Altura do nó: Utilizada somente pela ArvoreAVL para balanceamento. */
    public int altura;

    public NoArvore(T valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
        this.altura = 0;
    }
}