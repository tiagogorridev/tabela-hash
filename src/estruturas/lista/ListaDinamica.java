package src.estruturas.lista;

public class ListaDinamica {
    private String[] elementos;
    private int tamanho;
    private int capacidade;
    
    public ListaDinamica() {
        this.capacidade = 10;
        this.elementos = new String[capacidade];
        this.tamanho = 0;
    }
    
    public void adicionar(String elemento) {
        if (tamanho == capacidade) {
            expandir();
        }
        elementos[tamanho] = elemento;
        tamanho++;
    }
    
    private void expandir() {
        capacidade *= 2;
        String[] novoArray = new String[capacidade];
        for (int i = 0; i < tamanho; i++) {
            novoArray[i] = elementos[i];
        }
        elementos = novoArray;
    }
    
    public String get(int indice) {
        if (indice >= 0 && indice < tamanho) {
            return elementos[indice];
        }
        return null;
    }
    
    public int getTamanho() {
        return tamanho;
    }
}