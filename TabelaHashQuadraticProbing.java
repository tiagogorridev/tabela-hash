public class TabelaHashQuadraticProbing extends TabelaHashEnderecamentoAberto {
    
    public TabelaHashQuadraticProbing() { 
        super("Quadratic Probing: Hash + i²"); 
    }
    
    @Override
    protected int funcaoHash(String chave) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash += chave.charAt(i);
        }
        return hash % CAPACIDADE;
    }
    
    @Override
    protected int funcaoHashSecundaria(String chave, int tentativa) {
        int hashBase = funcaoHash(chave);
        return (hashBase + tentativa * tentativa) % CAPACIDADE;
    }
}
