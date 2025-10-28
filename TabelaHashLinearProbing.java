public class TabelaHashLinearProbing extends TabelaHashEnderecamentoAberto {
    
    public TabelaHashLinearProbing() { 
        super("Linear Probing: Hash + i"); 
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
        return (hashBase + tentativa) % CAPACIDADE;
    }
}
