public abstract class TabelaHash {
    protected static final int CAPACIDADE = 32;
    protected ListaEncadeada[] tabela;
    protected int totalElementos;
    protected int numeroColisoes;
    protected String nomeFuncaoHash;
    protected int[] contadoresAcesso;
    
    public TabelaHash(String nomeFuncaoHash) {
        tabela = new ListaEncadeada[CAPACIDADE];
        contadoresAcesso = new int[CAPACIDADE];
        for (int i = 0; i < CAPACIDADE; i++) {
            tabela[i] = new ListaEncadeada();
            contadoresAcesso[i] = 0;
        }
        totalElementos = 0;
        numeroColisoes = 0;
        this.nomeFuncaoHash = nomeFuncaoHash;
    }
    
    protected abstract int funcaoHash(String chave);
    
    public void inserir(String chave) {
        int indice = funcaoHash(chave);
        
        if (tabela[indice].getTamanho() > 0) {
            numeroColisoes++;
        }
        
        tabela[indice].adicionar(chave);
        totalElementos++;
    }
    
    public boolean buscar(String chave) {
        int indice = funcaoHash(chave);
        contadoresAcesso[indice]++;
        return tabela[indice].contem(chave);
    }
    
    public String buscarComPosicao(String chave) {
        int indice = funcaoHash(chave);
        contadoresAcesso[indice]++;
        if (tabela[indice].contem(chave)) {
            return "Nome '" + chave + "' encontrado na posicao [" + indice + "]";
        } else {
            return "Nome '" + chave + "' nao encontrado";
        }
    }
    
    public ResultadoBusca buscarDetalhado(String chave) {
        int indice = funcaoHash(chave);
        contadoresAcesso[indice]++;
        
        boolean encontrado = tabela[indice].contem(chave);
        int acessos = 1; // Sempre acessa pelo menos uma posição
        
        String detalhes = "";
        if (encontrado) {
            detalhes = "Elemento encontrado na lista encadeada da posicao " + indice;
        } else {
            detalhes = "Elemento nao encontrado na lista encadeada da posicao " + indice;
        }
        
        return new ResultadoBusca(encontrado, indice, acessos, chave, nomeFuncaoHash, detalhes);
    }
    
    public boolean remover(String chave) {
        int indice = funcaoHash(chave);
        boolean removido = tabela[indice].remover(chave);
        
        if (removido) { totalElementos--; }
        return removido;
    }
    
    public int getTotalElementos() { return totalElementos; }
    
    public int getCapacidade() { return CAPACIDADE; }
    
    public ListaEncadeada getLista(int indice) { return tabela[indice]; }
    
    public int getNumeroColisoes() { return numeroColisoes; }
    
    public String getNomeFuncaoHash() { return nomeFuncaoHash; }
    
    public double getLoadFactor() {
        return (double) totalElementos / CAPACIDADE;
    }
    
    public int[] getContadoresAcesso() {
        return contadoresAcesso.clone();
    }
    
    public void resetarContadoresAcesso() {
        for (int i = 0; i < CAPACIDADE; i++) {
            contadoresAcesso[i] = 0;
        }
    }
    
    public int getTotalAcessos() {
        int total = 0;
        for (int i = 0; i < CAPACIDADE; i++) {
            total += contadoresAcesso[i];
        }
        return total;
    }
    
    public double getMediaAcessosPorPosicao() {
        return getTotalAcessos() / (double) CAPACIDADE;
    }
    
    public int[] getDistribuicao() {
        int[] distribuicao = new int[CAPACIDADE];
        for (int i = 0; i < CAPACIDADE; i++) {
            distribuicao[i] = tabela[i].getTamanho();
        }
        return distribuicao;
    }
}