public class TabelaHashRedimensionavel2 extends TabelaHashRedimensionavel {
    
    public TabelaHashRedimensionavel2() { 
        super("Hash Redimensionavel 2: Chave % Tamanho"); 
    }
    
    @Override
    protected int funcaoHash(String chave, int capacidadeAtual) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash += chave.charAt(i);
        }
        return hash % capacidadeAtual;
    }
}
