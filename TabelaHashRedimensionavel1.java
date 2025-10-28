public class TabelaHashRedimensionavel1 extends TabelaHashRedimensionavel {
    
    public TabelaHashRedimensionavel1() { 
        super("Hash Redimensionavel 1: Quantidade de Letras - 1"); 
    }
    
    @Override
    protected int funcaoHash(String chave, int capacidadeAtual) {
        return (chave.length() - 1) % capacidadeAtual;
    }
}
