public class TabelaHashRedimensionavelLinearProbing extends TabelaHashRedimensionavelAberto {
    
    public TabelaHashRedimensionavelLinearProbing() { 
        super("Linear Probing Redimensionavel: Hash + i"); 
    }
    
    @Override
    protected int funcaoHash(String chave, int capacidadeAtual) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash += chave.charAt(i);
        }
        return hash % capacidadeAtual;
    }
    
    @Override
    protected int funcaoHashSecundaria(String chave, int tentativa, int capacidadeAtual) {
        int hashBase = funcaoHash(chave, capacidadeAtual);
        return (hashBase + tentativa) % capacidadeAtual;
    }
}
