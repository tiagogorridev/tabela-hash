public class TabelaHashRedimensionavelQuadraticProbing extends TabelaHashRedimensionavelAberto {
    
    public TabelaHashRedimensionavelQuadraticProbing() { 
        super("Quadratic Probing Redimensionavel: Hash + i²"); 
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
        return (hashBase + tentativa * tentativa) % capacidadeAtual;
    }
}
