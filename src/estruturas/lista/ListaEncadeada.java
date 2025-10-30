package src.estruturas.lista;

public class ListaEncadeada {
    No inicio;
    int tamanho;
    
    public ListaEncadeada() {
        this.inicio = null;
        this.tamanho = 0;
    }
    
    public void adicionar(String dado) {
        No novoNo = new No(dado);
        
        if (inicio == null) {
            inicio = novoNo;
        } else {
            No atual = inicio;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }
        tamanho++;
    }

    public boolean contem(String dado) {
        No atual = inicio;
        while (atual != null) {
            if (atual.dado.equals(dado)) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }
    
    public boolean remover(String dado) {
        if (inicio == null) return false;
        
        if (inicio.dado.equals(dado)) {
            inicio = inicio.proximo;
            tamanho--;
            return true;
        }
        
        No atual = inicio;
        while (atual.proximo != null) {
            if (atual.proximo.dado.equals(dado)) {
                atual.proximo = atual.proximo.proximo;
                tamanho--;
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }
    
    public int getTamanho() { return tamanho; }
    
    public String get(int indice) {
        if (indice < 0 || indice >= tamanho) {
            return null;
        }
        
        No atual = inicio;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.dado;
    }
}