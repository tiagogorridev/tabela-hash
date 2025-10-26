public abstract class TabelaHash {
    protected static final int CAPACIDADE = 32;
    protected ListaEncadeada[] tabela;
    protected int totalElementos;
    protected int numeroColisoes;
    protected String nomeFuncaoHash;
    
    public TabelaHash(String nomeFuncaoHash) {
        tabela = new ListaEncadeada[CAPACIDADE];
        for (int i = 0; i < CAPACIDADE; i++) {
            tabela[i] = new ListaEncadeada();
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
        return tabela[indice].contem(chave);
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
    
    public int[] getDistribuicao() {
        int[] distribuicao = new int[CAPACIDADE];
        for (int i = 0; i < CAPACIDADE; i++) {
            distribuicao[i] = tabela[i].getTamanho();
        }
        return distribuicao;
    }
}