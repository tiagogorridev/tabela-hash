public abstract class TabelaHashRedimensionavel {
    protected int capacidade;
    protected ListaEncadeada[] tabela;
    protected int totalElementos;
    protected int numeroColisoes;
    protected String nomeFuncaoHash;
    protected int[] contadoresAcesso;
    protected static final double FATOR_CARGA_MAXIMO = 0.75;
    protected static final int CAPACIDADE_INICIAL = 16;
    
    public TabelaHashRedimensionavel(String nomeFuncaoHash) {
        this.capacidade = CAPACIDADE_INICIAL;
        this.tabela = new ListaEncadeada[capacidade];
        this.contadoresAcesso = new int[capacidade];
        
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ListaEncadeada();
            contadoresAcesso[i] = 0;
        }
        
        totalElementos = 0;
        numeroColisoes = 0;
        this.nomeFuncaoHash = nomeFuncaoHash;
    }
    
    protected abstract int funcaoHash(String chave, int capacidadeAtual);
    
    public void inserir(String chave) {
        // Verificar se precisa redimensionar antes de inserir
        if (getLoadFactor() >= FATOR_CARGA_MAXIMO) {
            redimensionar();
        }
        
        int indice = funcaoHash(chave, capacidade);
        
        if (tabela[indice].getTamanho() > 0) {
            numeroColisoes++;
        }
        
        tabela[indice].adicionar(chave);
        totalElementos++;
    }
    
    private void redimensionar() {
        System.out.println("Redimensionando tabela de " + capacidade + " para " + (capacidade * 2) + " posicoes...");
        
        // Salvar dados atuais
        ListaEncadeada[] tabelaAntiga = tabela;
        int capacidadeAntiga = capacidade;
        
        // Dobrar a capacidade
        capacidade *= 2;
        tabela = new ListaEncadeada[capacidade];
        contadoresAcesso = new int[capacidade];
        
        // Inicializar nova tabela
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ListaEncadeada();
            contadoresAcesso[i] = 0;
        }
        
        // Resetar contadores
        totalElementos = 0;
        numeroColisoes = 0;
        
        // Reinserir todos os elementos
        for (int i = 0; i < capacidadeAntiga; i++) {
            ListaEncadeada lista = tabelaAntiga[i];
            for (int j = 0; j < lista.getTamanho(); j++) {
                String elemento = lista.get(j);
                inserirSemRedimensionamento(elemento);
            }
        }
        
        System.out.println("Redimensionamento concluido! Nova capacidade: " + capacidade);
    }
    
    private void inserirSemRedimensionamento(String chave) {
        int indice = funcaoHash(chave, capacidade);
        
        if (tabela[indice].getTamanho() > 0) {
            numeroColisoes++;
        }
        
        tabela[indice].adicionar(chave);
        totalElementos++;
    }
    
    public boolean buscar(String chave) {
        int indice = funcaoHash(chave, capacidade);
        contadoresAcesso[indice]++;
        return tabela[indice].contem(chave);
    }
    
    public String buscarComPosicao(String chave) {
        int indice = funcaoHash(chave, capacidade);
        contadoresAcesso[indice]++;
        if (tabela[indice].contem(chave)) {
            return "Nome '" + chave + "' encontrado na posicao [" + indice + "]";
        } else {
            return "Nome '" + chave + "' nao encontrado";
        }
    }
    
    public ResultadoBusca buscarDetalhado(String chave) {
        int indice = funcaoHash(chave, capacidade);
        contadoresAcesso[indice]++;
        
        boolean encontrado = tabela[indice].contem(chave);
        int acessos = 1; // Sempre acessa pelo menos uma posição
        
        String detalhes = "";
        if (encontrado) {
            detalhes = "Elemento encontrado na lista encadeada da posicao " + indice + " (capacidade atual: " + capacidade + ")";
        } else {
            detalhes = "Elemento nao encontrado na lista encadeada da posicao " + indice + " (capacidade atual: " + capacidade + ")";
        }
        
        return new ResultadoBusca(encontrado, indice, acessos, chave, nomeFuncaoHash, detalhes);
    }
    
    public boolean remover(String chave) {
        int indice = funcaoHash(chave, capacidade);
        boolean removido = tabela[indice].remover(chave);
        
        if (removido) { 
            totalElementos--; 
        }
        return removido;
    }
    
    public int getTotalElementos() { return totalElementos; }
    
    public int getCapacidade() { return capacidade; }
    
    public int getNumeroColisoes() { return numeroColisoes; }
    
    public String getNomeFuncaoHash() { return nomeFuncaoHash; }
    
    public double getLoadFactor() {
        return (double) totalElementos / capacidade;
    }
    
    public int[] getContadoresAcesso() {
        return contadoresAcesso.clone();
    }
    
    public void resetarContadoresAcesso() {
        for (int i = 0; i < capacidade; i++) {
            contadoresAcesso[i] = 0;
        }
    }
    
    public int getTotalAcessos() {
        int total = 0;
        for (int i = 0; i < capacidade; i++) {
            total += contadoresAcesso[i];
        }
        return total;
    }
    
    public double getMediaAcessosPorPosicao() {
        return getTotalAcessos() / (double) capacidade;
    }
    
    public int[] getDistribuicao() {
        int[] distribuicao = new int[capacidade];
        for (int i = 0; i < capacidade; i++) {
            distribuicao[i] = tabela[i].getTamanho();
        }
        return distribuicao;
    }
    
    public void imprimirEstatisticas() {
        System.out.println("\n" + nomeFuncaoHash);
        System.out.println("---------------------------------------------------");
        System.out.println("Capacidade atual: " + capacidade);
        System.out.println("Total de elementos: " + totalElementos);
        System.out.println("Fator de carga: " + String.format("%.3f", getLoadFactor()));
        System.out.println("Numero de colisoes: " + numeroColisoes);
        System.out.println("Total de acessos: " + getTotalAcessos());
        System.out.println("Media de acessos por posicao: " + String.format("%.2f", getMediaAcessosPorPosicao()));
    }
}
