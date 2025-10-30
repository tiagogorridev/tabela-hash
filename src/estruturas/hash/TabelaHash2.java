package src.estruturas.hash;

public class TabelaHash2 extends TabelaHash {
    
    public TabelaHash2() { super("Funcao Hash 2: Chave % Tamanho"); }
    
    @Override
    protected int funcaoHash(String chave) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash += chave.charAt(i);
        }
        return hash % CAPACIDADE;
    }
}