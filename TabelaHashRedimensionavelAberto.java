public abstract class TabelaHashRedimensionavelAberto {
    protected int capacidade;
    protected String[] tabela;
    protected boolean[] ocupado;
    protected int totalElementos;
    protected int numeroColisoes;
    protected String nomeFuncaoHash;
    protected int[] contadoresAcesso;
    protected static final double FATOR_CARGA_MAXIMO = 0.75;
    protected static final int CAPACIDADE_INICIAL = 16;
    
    public TabelaHashRedimensionavelAberto(String nomeFuncaoHash) {
        this.capacidade = CAPACIDADE_INICIAL;
        this.tabela = new String[capacidade];
        this.ocupado = new boolean[capacidade];
        this.contadoresAcesso = new int[capacidade];
        
        for (int i = 0; i < capacidade; i++) {
            contadoresAcesso[i] = 0;
            ocupado[i] = false;
        }
        
        totalElementos = 0;
        numeroColisoes = 0;
        this.nomeFuncaoHash = nomeFuncaoHash;
    }
    
    protected abstract int funcaoHash(String chave, int capacidadeAtual);
    
    protected abstract int funcaoHashSecundaria(String chave, int tentativa, int capacidadeAtual);
    
    public void inserir(String chave) {
        // Verificar se precisa redimensionar antes de inserir
        if (getLoadFactor() >= FATOR_CARGA_MAXIMO) {
            redimensionar();
        }
        
        int tentativa = 0;
        int indice = funcaoHash(chave, capacidade);
        
        while (ocupado[indice] && tentativa < capacidade) {
            if (tentativa > 0) {
                numeroColisoes++;
            }
            indice = funcaoHashSecundaria(chave, tentativa, capacidade);
            tentativa++;
        }
        
        if (tentativa < capacidade) {
            tabela[indice] = chave;
            ocupado[indice] = true;
            totalElementos++;
        }
    }
    
    private void redimensionar() {
        System.out.println("Redimensionando tabela de " + capacidade + " para " + (capacidade * 2) + " posicoes...");
        
        // Salvar dados atuais
        String[] tabelaAntiga = tabela;
        boolean[] ocupadoAntigo = ocupado;
        int capacidadeAntiga = capacidade;
        
        // Dobrar a capacidade
        capacidade *= 2;
        tabela = new String[capacidade];
        ocupado = new boolean[capacidade];
        contadoresAcesso = new int[capacidade];
        
        // Inicializar nova tabela
        for (int i = 0; i < capacidade; i++) {
            contadoresAcesso[i] = 0;
            ocupado[i] = false;
        }
        
        // Resetar contadores
        totalElementos = 0;
        numeroColisoes = 0;
        
        // Reinserir todos os elementos
        for (int i = 0; i < capacidadeAntiga; i++) {
            if (ocupadoAntigo[i]) {
                inserirSemRedimensionamento(tabelaAntiga[i]);
            }
        }
        
        System.out.println("Redimensionamento concluido! Nova capacidade: " + capacidade);
    }
    
    private void inserirSemRedimensionamento(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave, capacidade);
        
        while (ocupado[indice] && tentativa < capacidade) {
            if (tentativa > 0) {
                numeroColisoes++;
            }
            indice = funcaoHashSecundaria(chave, tentativa, capacidade);
            tentativa++;
        }
        
        if (tentativa < capacidade) {
            tabela[indice] = chave;
            ocupado[indice] = true;
            totalElementos++;
        }
    }
    
    public boolean buscar(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave, capacidade);
        
        while (ocupado[indice] && tentativa < capacidade) {
            contadoresAcesso[indice]++;
            if (tabela[indice] != null && tabela[indice].equals(chave)) {
                return true;
            }
            indice = funcaoHashSecundaria(chave, tentativa, capacidade);
            tentativa++;
        }
        
        // Contar o último acesso mesmo se não encontrou
        if (tentativa < capacidade) {
            contadoresAcesso[indice]++;
        }
        
        return false;
    }
    
    public String buscarComPosicao(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave, capacidade);
        int indiceOriginal = indice;
        
        while (ocupado[indice] && tentativa < capacidade) {
            contadoresAcesso[indice]++;
            if (tabela[indice] != null && tabela[indice].equals(chave)) {
                if (tentativa == 0) {
                    return "Nome '" + chave + "' encontrado na posicao [" + indice + "] (sem colisao)";
                } else {
                    return "Nome '" + chave + "' encontrado na posicao [" + indice + "] (posicao original: [" + indiceOriginal + "], " + tentativa + " colisoes)";
                }
            }
            indice = funcaoHashSecundaria(chave, tentativa, capacidade);
            tentativa++;
        }
        
        // Contar o último acesso mesmo se não encontrou
        if (tentativa < capacidade) {
            contadoresAcesso[indice]++;
        }
        
        return "Nome '" + chave + "' nao encontrado";
    }
    
    public ResultadoBusca buscarDetalhado(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave, capacidade);
        int indiceOriginal = indice;
        int acessos = 0;
        
        while (ocupado[indice] && tentativa < capacidade) {
            contadoresAcesso[indice]++;
            acessos++;
            
            if (tabela[indice] != null && tabela[indice].equals(chave)) {
                String detalhes = "";
                if (tentativa == 0) {
                    detalhes = "Elemento encontrado na posicao original sem colisao (capacidade atual: " + capacidade + ")";
                } else {
                    detalhes = "Elemento encontrado apos " + tentativa + " colisoes (posicao original: " + indiceOriginal + ", capacidade atual: " + capacidade + ")";
                }
                return new ResultadoBusca(true, indice, acessos, chave, nomeFuncaoHash, detalhes);
            }
            
            indice = funcaoHashSecundaria(chave, tentativa, capacidade);
            tentativa++;
        }
        
        // Contar o último acesso mesmo se não encontrou
        if (tentativa < capacidade) {
            contadoresAcesso[indice]++;
            acessos++;
        }
        
        String detalhes = "Elemento nao encontrado apos " + tentativa + " tentativas (capacidade atual: " + capacidade + ")";
        return new ResultadoBusca(false, indice, acessos, chave, nomeFuncaoHash, detalhes);
    }
    
    public boolean remover(String chave) {
        int tentativa = 0;
        int indice = funcaoHash(chave, capacidade);
        
        while (ocupado[indice] && tentativa < capacidade) {
            if (tabela[indice] != null && tabela[indice].equals(chave)) {
                tabela[indice] = null;
                ocupado[indice] = false;
                totalElementos--;
                return true;
            }
            indice = funcaoHashSecundaria(chave, tentativa, capacidade);
            tentativa++;
        }
        
        return false;
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
            distribuicao[i] = ocupado[i] ? 1 : 0;
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
