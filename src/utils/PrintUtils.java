package src.utils;

import src.estruturas.hash.TabelaHash;

public abstract class PrintUtils {

    public static void imprimirLinha() { System.out.println("================================================================"); }
    
    public static void imprimirTitulo(String titulo) {
        imprimirLinha();
        System.out.println(titulo);
        imprimirLinha();
    }

    public static void imprimirRelatorioTabela(TabelaHash tabela, long tempoInsercao, long tempoBusca) {

        System.out.printf("""
                \n %s
                ---------------------------------------------------
                Total de elementos inseridos: %s
                Numero total de colisoes: %s
                Tempo de insercao: %s ms
                Tempo de busca: %s ms
                \nDistribuicao das chaves por posicao:
                """, 
            tabela.getNomeFuncaoHash(),
            tabela.getTotalElementos(),
            tabela.getNumeroColisoes(),
            (tempoInsercao / 1_000_000.0),
            (tempoBusca / 1_000_000.0)
        );

        
        int[] distribuicao = tabela.getDistribuicao();
        for (int i = 0; i < distribuicao.length; i++) {
            System.out.printf("Posicao[%s] - %s elementos\n", i, distribuicao[i]);
        }
        
        int maiorLista = 0;
        int menorLista = distribuicao[0];
        int posicoesVazias = 0;
        
        for (int i = 0; i < distribuicao.length; i++) {
            if (distribuicao[i] > maiorLista) {
                maiorLista = distribuicao[i];
            }
            if (distribuicao[i] < menorLista) {
                menorLista = distribuicao[i];
            }
            if (distribuicao[i] == 0) {
                posicoesVazias++;
            }
        }

        System.out.printf("""
                \nEstatisticas da Distribuicao:
                Maior lista: %s elementos
                Menor lista: %s elementos
                Posicoes vazias: %s
                Media de elementos por posicao: %s
                """,
            maiorLista,
            menorLista,
            posicoesVazias, 
            (tabela.getTotalElementos() / (double)tabela.getCapacidade())
        );
        
    }
    
}
