package src.estruturas.hash;

public class TabelaHash1 extends TabelaHash {
    
    public TabelaHash1() { super("Funcao Hash 1: Quantidade de Letras - 1"); }
    
    @Override
    protected int funcaoHash(String chave) {
        return (chave.length() - 1) % CAPACIDADE;
    }
}