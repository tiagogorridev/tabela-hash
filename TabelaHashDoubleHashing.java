public class TabelaHashDoubleHashing extends TabelaHashEnderecamentoAberto {
    
    public TabelaHashDoubleHashing() { 
        super("Double Hashing: Hash1 + i * Hash2"); 
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
        int hash1 = funcaoHash(chave);
        int hash2 = funcaoHash2(chave);
        return (hash1 + tentativa * hash2) % CAPACIDADE;
    }
    
    private int funcaoHash2(String chave) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash = hash * 31 + chave.charAt(i);
        }
        hash = Math.abs(hash);
        return (hash % (CAPACIDADE - 1)) + 1; // Garante que hash2 != 0
    }
}
