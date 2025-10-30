package src.estruturas.hash;

public class BuscaDetalhada {
    private final String chave;
    private final boolean encontrado;
    private final int bucketIndex;
    private final int posicaoNaLista;
    private final int acessosNaLista;

    public BuscaDetalhada(String chave, boolean encontrado, int bucketIndex, int posicaoNaLista, int acessosNaLista) {
        this.chave = chave;
        this.encontrado = encontrado;
        this.bucketIndex = bucketIndex;
        this.posicaoNaLista = posicaoNaLista;
        this.acessosNaLista = acessosNaLista;
    }

    public String getChave() { return chave; }
    public boolean isEncontrado() { return encontrado; }
    public int getBucketIndex() { return bucketIndex; }
    public int getPosicaoNaLista() { return posicaoNaLista; }
    public int getAcessosNaLista() { return acessosNaLista; }
}


