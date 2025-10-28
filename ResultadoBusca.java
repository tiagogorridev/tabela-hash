public class ResultadoBusca {
    private boolean encontrado;
    private int posicao;
    private int acessos;
    private String chave;
    private String metodoHash;
    private String detalhesAdicionais;
    
    public ResultadoBusca(boolean encontrado, int posicao, int acessos, String chave, String metodoHash) {
        this.encontrado = encontrado;
        this.posicao = posicao;
        this.acessos = acessos;
        this.chave = chave;
        this.metodoHash = metodoHash;
        this.detalhesAdicionais = "";
    }
    
    public ResultadoBusca(boolean encontrado, int posicao, int acessos, String chave, String metodoHash, String detalhesAdicionais) {
        this.encontrado = encontrado;
        this.posicao = posicao;
        this.acessos = acessos;
        this.chave = chave;
        this.metodoHash = metodoHash;
        this.detalhesAdicionais = detalhesAdicionais;
    }
    
    // Getters
    public boolean isEncontrado() { return encontrado; }
    public int getPosicao() { return posicao; }
    public int getAcessos() { return acessos; }
    public String getChave() { return chave; }
    public String getMetodoHash() { return metodoHash; }
    public String getDetalhesAdicionais() { return detalhesAdicionais; }
    
    // Setters
    public void setEncontrado(boolean encontrado) { this.encontrado = encontrado; }
    public void setPosicao(int posicao) { this.posicao = posicao; }
    public void setAcessos(int acessos) { this.acessos = acessos; }
    public void setChave(String chave) { this.chave = chave; }
    public void setMetodoHash(String metodoHash) { this.metodoHash = metodoHash; }
    public void setDetalhesAdicionais(String detalhesAdicionais) { this.detalhesAdicionais = detalhesAdicionais; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultado da busca para '").append(chave).append("':\n");
        sb.append("  Metodo: ").append(metodoHash).append("\n");
        sb.append("  Encontrado: ").append(encontrado ? "SIM" : "NAO").append("\n");
        sb.append("  Posicao: ").append(posicao).append("\n");
        sb.append("  Acessos realizados: ").append(acessos).append("\n");
        if (!detalhesAdicionais.isEmpty()) {
            sb.append("  Detalhes: ").append(detalhesAdicionais).append("\n");
        }
        return sb.toString();
    }
    
    public String toStringResumido() {
        return String.format("'%s': %s na posicao [%d] com %d acessos", 
            chave, encontrado ? "ENCONTRADO" : "NAO ENCONTRADO", posicao, acessos);
    }
}
