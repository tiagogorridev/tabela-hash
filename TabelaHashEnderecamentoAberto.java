public abstract class TabelaHashEnderecamentoAberto {
    protected static final int CAPACIDADE = 32;
    protected String[] tabela;
    protected boolean[] ocupado;
    protected int totalElementos;
    protected int numeroColisoes;
    protected String nomeFuncaoHash;
    protected int[] contadoresAcesso;
    
    public TabelaHashEnderecamentoAberto(String nomeFuncaoHash) {
        tabela = new String[CAPACIDADE];
        ocupado = new boolean[CAPACIDADE];
        contadoresAcesso = new int[CAPACIDADE];
        totalElementos = 0;
        numeroColisoes = 0;
        this.nomeFuncaoHash = nomeFuncaoHash;
        
        for (int i = 0; i < CAPACIDADE; i++) {
            contadoresAcesso[i] = 0;
        }
    }
    
    protected abstract int funcaoHash(String chave);
    
    protected abstract int funcaoHashSecundaria(String chave, int tentativa);
    
    public void inserir(String chave) {
        if (totalElementos >= CAPACIDADE) {
            System.out.println("Tabela cheia! Não é possível inserir: " + chave);
            return;
        }
        
        int tentativa = 0;
        int indice = funcaoHash(chave);
        
        while (ocupado[indice] && tentativa < CAPACIDADE) {
            if (tentativa > 0) {
                numeroColisoes++;
            }
            indice = funcaoHashSecundaria(chave, tentativa);
            tentativa++;
        }
        
        if (tentativa < CAPACIDADE) {
            tabela[indice] = chave;
            ocupado[indice] = true;
            totalElementos++;
        }
    }
    
    public boolean buscar(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave);
        
        while (ocupado[indice] && tentativa < CAPACIDADE) {
            contadoresAcesso[indice]++;
            if (tabela[indice] != null && tabela[indice].equals(chave)) {
                return true;
            }
            indice = funcaoHashSecundaria(chave, tentativa);
            tentativa++;
        }
        
        // Contar o último acesso mesmo se não encontrou
        if (tentativa < CAPACIDADE) {
            contadoresAcesso[indice]++;
        }
        
        return false;
    }
    
    public String buscarComPosicao(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave);
        int indiceOriginal = indice;
        
        while (ocupado[indice] && tentativa < CAPACIDADE) {
            contadoresAcesso[indice]++;
            if (tabela[indice] != null && tabela[indice].equals(chave)) {
                if (tentativa == 0) {
                    return "Nome '" + chave + "' encontrado na posicao [" + indice + "] (sem colisao)";
                } else {
                    return "Nome '" + chave + "' encontrado na posicao [" + indice + "] (posicao original: [" + indiceOriginal + "], " + tentativa + " colisoes)";
                }
            }
            indice = funcaoHashSecundaria(chave, tentativa);
            tentativa++;
        }
        
        // Contar o último acesso mesmo se não encontrou
        if (tentativa < CAPACIDADE) {
            contadoresAcesso[indice]++;
        }
        
        return "Nome '" + chave + "' nao encontrado";
    }
    
    public ResultadoBusca buscarDetalhado(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave);
        int indiceOriginal = indice;
        int acessos = 0;
        
        while (ocupado[indice] && tentativa < CAPACIDADE) {
            contadoresAcesso[indice]++;
            acessos++;
            
            if (tabela[indice] != null && tabela[indice].equals(chave)) {
                String detalhes = "";
                if (tentativa == 0) {
                    detalhes = "Elemento encontrado na posicao original sem colisao";
                } else {
                    detalhes = "Elemento encontrado apos " + tentativa + " colisoes (posicao original: " + indiceOriginal + ")";
                }
                return new ResultadoBusca(true, indice, acessos, chave, nomeFuncaoHash, detalhes);
            }
            
            indice = funcaoHashSecundaria(chave, tentativa);
            tentativa++;
        }
        
        // Contar o último acesso mesmo se não encontrou
        if (tentativa < CAPACIDADE) {
            contadoresAcesso[indice]++;
            acessos++;
        }
        
        String detalhes = "Elemento nao encontrado apos " + tentativa + " tentativas";
        return new ResultadoBusca(false, indice, acessos, chave, nomeFuncaoHash, detalhes);
    }
    
    public boolean remover(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave);
        
        while (ocupado[indice] && tentativa < CAPACIDADE) {
            if (tabela[indice] != null && tabela[indice].equals(chave)) {
                tabela[indice] = null;
                ocupado[indice] = false;
                totalElementos--;
                return true;
            }
            indice = funcaoHashSecundaria(chave, tentativa);
            tentativa++;
        }
        
        return false;
    }
    
    public int getTotalElementos() { return totalElementos; }
    
    public int getCapacidade() { return CAPACIDADE; }
    
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
            distribuicao[i] = ocupado[i] ? 1 : 0;
        }
        return distribuicao;
    }
    
    public void imprimirTabela() {
        System.out.println("\nEstado da tabela:");
        for (int i = 0; i < CAPACIDADE; i++) {
            if (ocupado[i]) {
                System.out.println("Posicao [" + i + "]: " + tabela[i]);
            } else {
                System.out.println("Posicao [" + i + "]: VAZIA");
            }
        }
    }
}
