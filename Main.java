public class Main {
    private static void imprimirLinha() { System.out.println("================================================================"); }
    
    private static void imprimirTitulo(String titulo) {
        imprimirLinha();
        System.out.println(titulo);
        imprimirLinha();
    }
    
    private static long testarBuscas(TabelaHash tabela, ListaDinamica nomes) {
        Cronometro cronometro = new Cronometro();
        cronometro.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i += 150) {
            tabela.buscar(nomes.get(i));
        }
            
        return cronometro.obterTempoDecorrido();
    }
    
    private static long testarRemocoes(TabelaHash tabela, ListaDinamica nomes) {
        Cronometro cronometro = new Cronometro();
        cronometro.iniciar();
        
        int removidos = 0;
        for (int i = 0; i < nomes.getTamanho(); i += 150) {
            if (tabela.remover(nomes.get(i))) {
                removidos++;
            }
        }
        
        long tempo = cronometro.obterTempoDecorrido();
        System.out.println("Total de elementos removidos: " + removidos);
        
        return tempo;
    }
    
    private static void imprimirRelatorioTabela(TabelaHash tabela, long tempoInsercao, long tempoBusca) {
        System.out.println("\n" + tabela.getNomeFuncaoHash());
        System.out.println("---------------------------------------------------");
        System.out.println("Total de elementos inseridos: " + tabela.getTotalElementos());
        System.out.println("Numero total de colisoes: " + tabela.getNumeroColisoes());
        System.out.println("Fator de carga: " + String.format("%.3f", tabela.getLoadFactor()));
        System.out.println("Tempo de insercao: " + (tempoInsercao / 1_000_000.0) + " ms");
        System.out.println("Tempo de busca: " + (tempoBusca / 1_000_000.0) + " ms");
        
        System.out.println("\nDistribuicao das chaves por posicao:");
        int[] distribuicao = tabela.getDistribuicao();
        for (int i = 0; i < distribuicao.length; i++) {
            System.out.println("Posicao [" + i + "]: " + distribuicao[i] + " elementos");
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
        
        System.out.println("\nEstatisticas da Distribuicao:");
        System.out.println("Maior lista: " + maiorLista + " elementos");
        System.out.println("Menor lista: " + menorLista + " elementos");
        System.out.println("Posicoes vazias: " + posicoesVazias);
        System.out.println("Media de elementos por posicao: " + (tabela.getTotalElementos() / (double)tabela.getCapacidade()));
    }
    
    public static void main(String[] args) {
        imprimirTitulo("SISTEMA DE TABELAS HASH - ANALISE COMPARATIVA");
        
        System.out.println("\n[ETAPA 1] Lendo arquivo de nomes...");
        ListaDinamica nomes = LeitorArquivo.lerArquivo("female_names.txt");
        System.out.println("Total de nomes lidos: " + nomes.getTamanho());
        
        System.out.println("\n[ETAPA 2] Criando tabelas hash...");
        TabelaHash tabela1 = new TabelaHash1();
        TabelaHash tabela2 = new TabelaHash2();
        System.out.println("Capacidade de cada tabela: " + tabela1.getCapacidade());
        System.out.println("Metodo de tratamento de colisoes: Encadeamento Exterior");
        
        System.out.println("\n[ETAPA 3] Inserindo nomes na Tabela 1...");
        Cronometro cronometro1 = new Cronometro();
        cronometro1.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabela1.inserir(nomes.get(i));
        }
        
        long tempoInsercao1 = cronometro1.obterTempoDecorrido();
        System.out.println("Insercao concluida!");
        
        System.out.println("\n[ETAPA 4] Inserindo nomes na Tabela 2...");
        Cronometro cronometro2 = new Cronometro();
        cronometro2.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabela2.inserir(nomes.get(i));
        }
        
        long tempoInsercao2 = cronometro2.obterTempoDecorrido();
        System.out.println("Insercao concluida!");
        
        System.out.println("\n[ETAPA 5] Realizando testes de busca...");
        long tempoBusca1 = testarBuscas(tabela1, nomes);
        long tempoBusca2 = testarBuscas(tabela2, nomes);
        System.out.println("Testes de busca concluidos!");
        
        System.out.println("\n[ETAPA 6] Realizando testes de remocao...");
        System.out.println("Tabela 1:");
        long tempoRemocao1 = testarRemocoes(tabela1, nomes);
        System.out.println("Tabela 2:");
        long tempoRemocao2 = testarRemocoes(tabela2, nomes);
        System.out.println("Testes de remocao concluidos!");
        
        imprimirTitulo("RELATORIO FINAL - RESULTADOS COMPARATIVOS");
        
        imprimirRelatorioTabela(tabela1, tempoInsercao1, tempoBusca1);
        System.out.println("\n");
        imprimirRelatorioTabela(tabela2, tempoInsercao2, tempoBusca2);
        
        imprimirTitulo("COMPARACAO DIRETA");
        System.out.println("\nNumero de Colisoes:");
        System.out.println("Tabela 1: " + tabela1.getNumeroColisoes());
        System.out.println("Tabela 2: " + tabela2.getNumeroColisoes());
        System.out.println("Diferenca: " + Math.abs(tabela1.getNumeroColisoes() - tabela2.getNumeroColisoes()));
        
        System.out.println("\nTempo de Insercao:");
        System.out.println("Tabela 1: " + (tempoInsercao1 / 1_000_000.0) + " ms");
        System.out.println("Tabela 2: " + (tempoInsercao2 / 1_000_000.0) + " ms");
        
        System.out.println("\nTempo de Busca:");
        System.out.println("Tabela 1: " + (tempoBusca1 / 1_000_000.0) + " ms");
        System.out.println("Tabela 2: " + (tempoBusca2 / 1_000_000.0) + " ms");
        
        System.out.println("\nTempo de Remocao:");
        System.out.println("Tabela 1: " + (tempoRemocao1 / 1_000_000.0) + " ms");
        System.out.println("Tabela 2: " + (tempoRemocao2 / 1_000_000.0) + " ms");
        
        imprimirLinha();
        System.out.println("FIM DA EXECUCAO");
        imprimirLinha();
    }
}