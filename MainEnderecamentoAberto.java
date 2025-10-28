public class MainEnderecamentoAberto {
    private static void imprimirLinha() { System.out.println("================================================================"); }
    
    private static void imprimirTitulo(String titulo) {
        imprimirLinha();
        System.out.println(titulo);
        imprimirLinha();
    }
    
    private static long testarBuscas(TabelaHashEnderecamentoAberto tabela, ListaDinamica nomes) {
        Cronometro cronometro = new Cronometro();
        cronometro.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i += 150) {
            tabela.buscar(nomes.get(i));
        }
            
        return cronometro.obterTempoDecorrido();
    }
    
    private static long testarRemocoes(TabelaHashEnderecamentoAberto tabela, ListaDinamica nomes) {
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
    
    private static void imprimirRelatorioTabela(TabelaHashEnderecamentoAberto tabela, long tempoInsercao, long tempoBusca) {
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
            System.out.println("Posicao [" + i + "]: " + (distribuicao[i] == 1 ? "OCUPADA" : "VAZIA"));
        }
        
        int posicoesOcupadas = 0;
        int posicoesVazias = 0;
        
        for (int i = 0; i < distribuicao.length; i++) {
            if (distribuicao[i] == 1) {
                posicoesOcupadas++;
            } else {
                posicoesVazias++;
            }
        }
        
        System.out.println("\nEstatisticas da Distribuicao:");
        System.out.println("Posicoes ocupadas: " + posicoesOcupadas);
        System.out.println("Posicoes vazias: " + posicoesVazias);
        System.out.println("Taxa de ocupacao: " + String.format("%.1f", (posicoesOcupadas * 100.0 / 32)) + "%");
    }
    
    public static void main(String[] args) {
        imprimirTitulo("SISTEMA DE TABELAS HASH - ENDERECAMENTO ABERTO");
        
        System.out.println("\n[ETAPA 1] Lendo arquivo de nomes...");
        ListaDinamica nomes = LeitorArquivo.lerArquivo("female_names.txt");
        System.out.println("Total de nomes lidos: " + nomes.getTamanho());
        
        System.out.println("\n[ETAPA 2] Criando tabelas hash com enderecamento aberto...");
        TabelaHashEnderecamentoAberto tabelaLinear = new TabelaHashLinearProbing();
        TabelaHashEnderecamentoAberto tabelaQuadratica = new TabelaHashQuadraticProbing();
        TabelaHashEnderecamentoAberto tabelaDoubleHashing = new TabelaHashDoubleHashing();
        System.out.println("Capacidade de cada tabela: " + tabelaLinear.getCapacidade());
        System.out.println("Metodo de tratamento de colisoes: Enderecamento Aberto");
        
        System.out.println("\n[ETAPA 3] Inserindo nomes na Tabela Linear Probing...");
        Cronometro cronometro1 = new Cronometro();
        cronometro1.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabelaLinear.inserir(nomes.get(i));
        }
        
        long tempoInsercao1 = cronometro1.obterTempoDecorrido();
        System.out.println("Insercao concluida!");
        
        System.out.println("\n[ETAPA 4] Inserindo nomes na Tabela Quadratic Probing...");
        Cronometro cronometro2 = new Cronometro();
        cronometro2.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabelaQuadratica.inserir(nomes.get(i));
        }
        
        long tempoInsercao2 = cronometro2.obterTempoDecorrido();
        System.out.println("Insercao concluida!");
        
        System.out.println("\n[ETAPA 5] Inserindo nomes na Tabela Double Hashing...");
        Cronometro cronometro3 = new Cronometro();
        cronometro3.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabelaDoubleHashing.inserir(nomes.get(i));
        }
        
        long tempoInsercao3 = cronometro3.obterTempoDecorrido();
        System.out.println("Insercao concluida!");
        
        System.out.println("\n[ETAPA 6] Realizando testes de busca...");
        long tempoBusca1 = testarBuscas(tabelaLinear, nomes);
        long tempoBusca2 = testarBuscas(tabelaQuadratica, nomes);
        long tempoBusca3 = testarBuscas(tabelaDoubleHashing, nomes);
        System.out.println("Testes de busca concluidos!");
        
        System.out.println("\n[ETAPA 7] Realizando testes de remocao...");
        System.out.println("Tabela Linear Probing:");
        long tempoRemocao1 = testarRemocoes(tabelaLinear, nomes);
        System.out.println("Tabela Quadratic Probing:");
        long tempoRemocao2 = testarRemocoes(tabelaQuadratica, nomes);
        System.out.println("Tabela Double Hashing:");
        long tempoRemocao3 = testarRemocoes(tabelaDoubleHashing, nomes);
        System.out.println("Testes de remocao concluidos!");
        
        imprimirTitulo("RELATORIO FINAL - RESULTADOS COMPARATIVOS");
        
        imprimirRelatorioTabela(tabelaLinear, tempoInsercao1, tempoBusca1);
        System.out.println("\n");
        imprimirRelatorioTabela(tabelaQuadratica, tempoInsercao2, tempoBusca2);
        System.out.println("\n");
        imprimirRelatorioTabela(tabelaDoubleHashing, tempoInsercao3, tempoBusca3);
        
        imprimirTitulo("COMPARACAO DIRETA");
        System.out.println("\nNumero de Colisoes:");
        System.out.println("Linear Probing: " + tabelaLinear.getNumeroColisoes());
        System.out.println("Quadratic Probing: " + tabelaQuadratica.getNumeroColisoes());
        System.out.println("Double Hashing: " + tabelaDoubleHashing.getNumeroColisoes());
        
        System.out.println("\nTempo de Insercao:");
        System.out.println("Linear Probing: " + (tempoInsercao1 / 1_000_000.0) + " ms");
        System.out.println("Quadratic Probing: " + (tempoInsercao2 / 1_000_000.0) + " ms");
        System.out.println("Double Hashing: " + (tempoInsercao3 / 1_000_000.0) + " ms");
        
        System.out.println("\nTempo de Busca:");
        System.out.println("Linear Probing: " + (tempoBusca1 / 1_000_000.0) + " ms");
        System.out.println("Quadratic Probing: " + (tempoBusca2 / 1_000_000.0) + " ms");
        System.out.println("Double Hashing: " + (tempoBusca3 / 1_000_000.0) + " ms");
        
        System.out.println("\nTempo de Remocao:");
        System.out.println("Linear Probing: " + (tempoRemocao1 / 1_000_000.0) + " ms");
        System.out.println("Quadratic Probing: " + (tempoRemocao2 / 1_000_000.0) + " ms");
        System.out.println("Double Hashing: " + (tempoRemocao3 / 1_000_000.0) + " ms");
        
        imprimirLinha();
        System.out.println("FIM DA EXECUCAO");
        imprimirLinha();
    }
}
