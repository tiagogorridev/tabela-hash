package src.estruturas.hash;

import src.estruturas.lista.ListaEncadeada;

public abstract class TabelaHash {
    protected static final int CAPACIDADE = 32;
    protected ListaEncadeada[] tabela;
    protected int totalElementos;
    protected int numeroColisoes;
    protected String nomeFuncaoHash;
    
    public TabelaHash(String nomeFuncHash) {

        tabela = new ListaEncadeada[CAPACIDADE];

        for (int i = 0; i < CAPACIDADE; i++) {
            tabela[i] = new ListaEncadeada();
        }
        
        totalElementos = 0;
        numeroColisoes = 0;
        this.nomeFuncaoHash = nomeFuncHash;

    }
    
    protected abstract int funcaoHash(String chave);
    
    public void inserir(String chave) {

        int indice = funcaoHash(chave);
        
        if (tabela[indice].getTamanho() > 0) numeroColisoes++;
        
        tabela[indice].adicionar(chave);
        totalElementos++;

    }
    
    public boolean buscar(String chave) {
        return tabela[funcaoHash(chave)].contem(chave);
    }
    
    public boolean remover(String chave) {

        boolean removido = tabela[funcaoHash(chave)].remover(chave);
        
        if (removido) totalElementos--;

        return removido;
        
    }
    
    public int getTotalElementos() { return totalElementos; }
    
    public int getCapacidade() { return CAPACIDADE; }
    
    public ListaEncadeada getLista(int indice) { return tabela[indice]; }
    
    public int getNumeroColisoes() { return numeroColisoes; }
    
    public String getNomeFuncaoHash() { return nomeFuncaoHash; }
    
    public double getFatorCarga() {
        return totalElementos / (double) CAPACIDADE;
    }

    public int[] buscarComPosicao(String chave) {
        int indice = funcaoHash(chave);
        int posLista = tabela[indice].indiceDe(chave);
        if (posLista == -1) return new int[] { -1, -1 };
        return new int[] { indice, posLista };
    }

    public int[] buscarComAcessos(String chave) {
        int indice = funcaoHash(chave);
        int[] acessosEEncontrado = tabela[indice].contarAcessosEEncontrado(chave);
        // Retorna: [bucketIndex, acessosNaLista, encontrado(1|0)]
        return new int[] { indice, acessosEEncontrado[0], acessosEEncontrado[1] };
    }

    public BuscaDetalhada buscarDetalhado(String chave) {
        int indice = funcaoHash(chave);
        int[] acessosEEncontrado = tabela[indice].contarAcessosEEncontrado(chave);
        int posLista = tabela[indice].indiceDe(chave);
        boolean encontrado = acessosEEncontrado[1] == 1;
        return new BuscaDetalhada(
            chave,
            encontrado,
            indice,
            encontrado ? posLista : -1,
            acessosEEncontrado[0]
        );
    }
    
    public int[] getDistribuicao() {
    
        int[] distribuicao = new int[CAPACIDADE];

        for (int i = 0; i < CAPACIDADE; i++) {
        
            distribuicao[i] = tabela[i].getTamanho();
        
        }
        
        return distribuicao;
    
    }

}