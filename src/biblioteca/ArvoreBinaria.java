package br.ifes.tpa.biblioteca;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementação de uma Árvore Binária de Busca (BST) genérica.
 **/
public class ArvoreBinaria<T> extends ArvoreBinariaBase<T> {

    /** Nó raiz da árvore. **/
    protected NoArvore<T> raiz; // alterado de 'private' para 'protected' para as subclasses enxergarem (como ArvoreAVL)

    /** Contador de nós **/
    protected int totalNos; // alterado de 'private' para 'protected' para as subclasses enxergarem (como ArvoreAVL)

    /**
     * Cria uma Árvore Binária de Busca vazia com o critério de indexação
     **/
    public ArvoreBinaria(Comparator<T> comparador) {
        super(comparador);
        this.raiz = null;
        this.totalNos = 0;
    }

    /**
     * Adiciona um novo valor à árvore respeitando a ordem do Comparator.
     * Valores duplicados (comparador == 0) são ignorados.
     **/
    @Override
    public void adicionar(T novoValor) {
        int[] inserido = {0};
        raiz = adicionarRecursivo(raiz, novoValor, inserido);
        totalNos += inserido[0];
    }

    private NoArvore<T> adicionarRecursivo(NoArvore<T> no, T valor, int[] inserido) {
        if (no == null) {
            inserido[0] = 1;
            return new NoArvore<>(valor);
        }
        int cmp = comparador.compare(valor, no.valor);
        if (cmp < 0) {
            no.esquerda = adicionarRecursivo(no.esquerda, valor, inserido);
        } else if (cmp > 0) {
            no.direita = adicionarRecursivo(no.direita, valor, inserido);
        }

        return no;
    }

    /**
     * Pesquisa um elemento na árvore pelo critério do Comparator.
     **/
    @Override
    public T pesquisar(T valor) {
        return pesquisarRecursivo(raiz, valor);
    }

    private T pesquisarRecursivo(NoArvore<T> no, T valor) {
        if (no == null) return null;
        int cmp = comparador.compare(valor, no.valor);
        if (cmp == 0) return no.valor;
        if (cmp < 0) return pesquisarRecursivo(no.esquerda, valor);
        return pesquisarRecursivo(no.direita, valor);
    }

    /**
     * Remove um elemento da árvore, se existir.
     * Ao remover um nó com dois filhos, substitui pelo sucessor em-ordem
     * (menor elemento da subárvore direita).
     **/

    @Override
    public boolean remover(T valor) {
        int[] removido = {0};
        raiz = removerRecursivo(raiz, valor, removido);
        if (removido[0] == 1) {
            totalNos--;
            return true;
        }
        return false;
    }

    private NoArvore<T> removerRecursivo(NoArvore<T> no, T valor, int[] removido) {
        if (no == null) return null;

        int cmp = comparador.compare(valor, no.valor);

        if (cmp < 0) {
            no.esquerda = removerRecursivo(no.esquerda, valor, removido);
        } else if (cmp > 0) {
            no.direita = removerRecursivo(no.direita, valor, removido);
        } else {
            // Nó encontrado
            removido[0] = 1;

            // Caso 1 e 2: zero ou um filho
            if (no.esquerda == null) return no.direita;
            if (no.direita == null) return no.esquerda;

            // Caso 3: dois filhos. Substitui pelo sucessor em-ordem
            NoArvore<T> sucessor = menorNo(no.direita);
            no.valor = sucessor.valor;
            // Remove o sucessor da subárvore direita (não marca como removido novamente)
            no.direita = removerRecursivo(no.direita, sucessor.valor, new int[]{0});
        }
        return no;
    }

    /** Retorna o nó com o menor valor de uma subárvore (o mais à esquerda). */
    private NoArvore<T> menorNo(NoArvore<T> no) {
        while (no.esquerda != null) {
            no = no.esquerda;
        }
        return no;
    }

    /**
     * Retorna a quantidade de nós armazenados na árvore.
     **/
    @Override
    public int quantidadeNos() {
        return totalNos;
    }

    /**
     * Retorna a altura da árvore.
     * Uma árvore com apenas a raiz tem altura 0. Raiz nula retorna -1.
     **/
    @Override
    public int altura() {
        return alturaRecursiva(raiz);
    }

    private int alturaRecursiva(NoArvore<T> no) {
        if (no == null) return -1;
        int altEsq = alturaRecursiva(no.esquerda);
        int altDir = alturaRecursiva(no.direita);
        return 1 + Math.max(altEsq, altDir);
    }

    /**
     * Retorna o caminhamento em nível (BFS — Busca em Largura).
     * Cada nível da árvore ocupa uma linha dentro dos colchetes.
     **/
    @Override
    public String caminharEmNivel() {
        if (raiz == null) return "[]";

        StringBuilder sb = new StringBuilder("[");
        Queue<NoArvore<T>> fila = new LinkedList<>();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();

            for (int i = 0; i < tamanhoNivel; i++) {
                NoArvore<T> atual = fila.poll();
                sb.append(atual.valor);
                if (i < tamanhoNivel - 1) sb.append(", ");

                if (atual.esquerda != null) fila.add(atual.esquerda);
                if (atual.direita != null)  fila.add(atual.direita);
            }

            // Separa os níveis por quebra de linha, exceto após o último
            if (!fila.isEmpty()) sb.append("\n");
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * Retorna o caminhamento em ordem (in-order: esquerda -> raiz -> direita).
     * Produz os elementos em ordem crescente segundo o Comparator.
     **/

    @Override
    public String caminharEmOrdem() {
        StringBuilder sb = new StringBuilder("[");
        caminharEmOrdemRecursivo(raiz, sb);
        // Remove a vírgula e espaço extras do último elemento, se houver
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    private void caminharEmOrdemRecursivo(NoArvore<T> no, StringBuilder sb) {
        if (no == null) return;
        caminharEmOrdemRecursivo(no.esquerda, sb);
        sb.append(no.valor).append(", ");
        caminharEmOrdemRecursivo(no.direita, sb);
    }

    public String toString() {
        return caminharEmOrdem();
    }
}