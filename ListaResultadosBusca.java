public class ListaResultadosBusca {
    private ResultadoBusca[] resultados;
    private int tamanho;
    private int capacidade;
    
    public ListaResultadosBusca() {
        this.capacidade = 10;
        this.resultados = new ResultadoBusca[capacidade];
        this.tamanho = 0;
    }
    
    public void adicionar(ResultadoBusca resultado) {
        if (tamanho == capacidade) {
            expandir();
        }
        resultados[tamanho] = resultado;
        tamanho++;
    }
    
    private void expandir() {
        capacidade *= 2;
        ResultadoBusca[] novoArray = new ResultadoBusca[capacidade];
        for (int i = 0; i < tamanho; i++) {
            novoArray[i] = resultados[i];
        }
        resultados = novoArray;
    }
    
    public ResultadoBusca get(int indice) {
        if (indice >= 0 && indice < tamanho) {
            return resultados[indice];
        }
        return null;
    }
    
    public int getTamanho() {
        return tamanho;
    }
    
    public boolean isEmpty() {
        return tamanho == 0;
    }
    
    public void limpar() {
        tamanho = 0;
    }
}
