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
                Fator de carga: %s
                Tempo de insercao: %s ms
                Tempo de busca: %s ms
                \nDistribuicao das chaves por posicao:
                """, 
            tabela.getNomeFuncaoHash(),
            tabela.getTotalElementos(),
            tabela.getNumeroColisoes(),
            String.format("%.4f", tabela.getFatorCarga()),
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
    
    public static void imprimirBuscasComPosicao(TabelaHash tabela, String[] chaves) {
        System.out.printf("\n[BUSCA COM POSICAO] %s\n", tabela.getNomeFuncaoHash());
        for (String chave : chaves) {
            int[] pos = tabela.buscarComPosicao(chave);
            if (pos[0] == -1) {
                System.out.printf("- '%s' nao encontrado\n", chave);
            } else {
                System.out.printf("- '%s' encontrado em bucket %s, posicao na lista %s\n", chave, pos[0], pos[1]);
            }
        }
    }
    
    public static void imprimirBuscasComAcessos(TabelaHash tabela, String[] chaves) {
        System.out.printf("\n[BUSCA COM CONTADORES DE ACESSO] %s\n", tabela.getNomeFuncaoHash());
        int totalAcessos = 0;
        int totalEncontrados = 0;
        for (String chave : chaves) {
            int[] resultado = tabela.buscarComAcessos(chave);
            int bucket = resultado[0];
            int acessos = resultado[1];
            boolean encontrado = resultado[2] == 1;
            totalAcessos += acessos;
            if (encontrado) totalEncontrados++;
            if (encontrado) {
                System.out.printf("- '%s' encontrado (bucket %s) com %s acessos\n", chave, bucket, acessos);
            } else {
                System.out.printf("- '%s' nao encontrado (bucket %s) apos %s acessos\n", chave, bucket, acessos);
            }
        }
        double media = chaves.length > 0 ? (totalAcessos / (double) chaves.length) : 0.0;
        System.out.printf("Resumo: %s encontradas de %s, media de acessos: %.2f\n", totalEncontrados, chaves.length, media);
    }
    
    public static void imprimirBuscasDetalhadas(TabelaHash tabela, String[] chaves) {
        System.out.printf("\n[BUSCA DETALHADA] %s\n", tabela.getNomeFuncaoHash());
        for (String chave : chaves) {
            var detalhes = tabela.buscarDetalhado(chave);
            if (detalhes.isEncontrado()) {
                System.out.printf(
                    "- '%s' -> encontrado | bucket=%s | posicao=%s | acessos=%s\n",
                    detalhes.getChave(), detalhes.getBucketIndex(), detalhes.getPosicaoNaLista(), detalhes.getAcessosNaLista()
                );
            } else {
                System.out.printf(
                    "- '%s' -> NAO encontrado | bucket=%s | acessos=%s\n",
                    detalhes.getChave(), detalhes.getBucketIndex(), detalhes.getAcessosNaLista()
                );
            }
        }
    }
    
    public static void printResumo(TabelaHash tabela, long tempoInsercao, long tempoBusca, long tempoRemocao) {
        System.out.printf("""
                %s
                - Capacidade: %s
                - Total elementos: %s
                - Colisoes: %s
                - Fator de carga: %s
                - Tempo Insercao: %s ms
                - Tempo Busca: %s ms
                - Tempo Remocao: %s ms
                """,
            tabela.getNomeFuncaoHash(),
            tabela.getCapacidade(),
            tabela.getTotalElementos(),
            tabela.getNumeroColisoes(),
            String.format("%.4f", tabela.getFatorCarga()),
            (tempoInsercao / 1_000_000.0),
            (tempoBusca / 1_000_000.0),
            (tempoRemocao / 1_000_000.0)
        );
    }
    
}
