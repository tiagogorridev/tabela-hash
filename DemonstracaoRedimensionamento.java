public class DemonstracaoRedimensionamento {
    private static void imprimirLinha() { System.out.println("================================================================"); }
    
    private static void imprimirTitulo(String titulo) {
        imprimirLinha();
        System.out.println(titulo);
        imprimirLinha();
    }
    
    private static void demonstrarRedimensionamentoExterno(TabelaHashRedimensionavel tabela, ListaDinamica nomes, String nomeTeste) {
        System.out.println("\n=== " + nomeTeste + " ===");
        
        Cronometro cronometro = new Cronometro();
        cronometro.iniciar();
        
        int redimensionamentos = 0;
        int capacidadeInicial = tabela.getCapacidade();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            int capacidadeAntes = tabela.getCapacidade();
            tabela.inserir(nomes.get(i));
            int capacidadeDepois = tabela.getCapacidade();
            
            if (capacidadeDepois > capacidadeAntes) {
                redimensionamentos++;
                System.out.println("Redimensionamento #" + redimensionamentos + " - Capacidade: " + 
                    capacidadeAntes + " -> " + capacidadeDepois + " (Elementos: " + tabela.getTotalElementos() + ")");
            }
        }
        
        long tempoInsercao = cronometro.obterTempoDecorrido();
        
        tabela.imprimirEstatisticas();
        System.out.println("Tempo de insercao: " + (tempoInsercao / 1_000_000.0) + " ms");
        System.out.println("Total de redimensionamentos: " + redimensionamentos);
        System.out.println("Capacidade final: " + tabela.getCapacidade() + " (inicial: " + capacidadeInicial + ")");
        System.out.println("Fator de crescimento: " + String.format("%.1f", (double)tabela.getCapacidade() / capacidadeInicial) + "x");
    }
    
    private static void demonstrarRedimensionamentoAberto(TabelaHashRedimensionavelAberto tabela, ListaDinamica nomes, String nomeTeste) {
        System.out.println("\n=== " + nomeTeste + " ===");
        
        Cronometro cronometro = new Cronometro();
        cronometro.iniciar();
        
        int redimensionamentos = 0;
        int capacidadeInicial = tabela.getCapacidade();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            int capacidadeAntes = tabela.getCapacidade();
            tabela.inserir(nomes.get(i));
            int capacidadeDepois = tabela.getCapacidade();
            
            if (capacidadeDepois > capacidadeAntes) {
                redimensionamentos++;
                System.out.println("Redimensionamento #" + redimensionamentos + " - Capacidade: " + 
                    capacidadeAntes + " -> " + capacidadeDepois + " (Elementos: " + tabela.getTotalElementos() + ")");
            }
        }
        
        long tempoInsercao = cronometro.obterTempoDecorrido();
        
        tabela.imprimirEstatisticas();
        System.out.println("Tempo de insercao: " + (tempoInsercao / 1_000_000.0) + " ms");
        System.out.println("Total de redimensionamentos: " + redimensionamentos);
        System.out.println("Capacidade final: " + tabela.getCapacidade() + " (inicial: " + capacidadeInicial + ")");
        System.out.println("Fator de crescimento: " + String.format("%.1f", (double)tabela.getCapacidade() / capacidadeInicial) + "x");
    }
    
    private static void compararComTabelasFixas(TabelaHashRedimensionavel tabelaRedimensionavel, TabelaHash tabelaFixa, ListaDinamica nomes) {
        System.out.println("\n=== COMPARACAO: REDIMENSIONAVEL vs FIXA ===");
        
        // Testar busca nas tabelas redimensionáveis
        Cronometro cronometro1 = new Cronometro();
        cronometro1.iniciar();
        
        int encontrados1 = 0;
        for (int i = 0; i < nomes.getTamanho(); i += 100) {
            if (tabelaRedimensionavel.buscar(nomes.get(i))) {
                encontrados1++;
            }
        }
        
        long tempoBusca1 = cronometro1.obterTempoDecorrido();
        
        // Testar busca nas tabelas fixas
        Cronometro cronometro2 = new Cronometro();
        cronometro2.iniciar();
        
        int encontrados2 = 0;
        for (int i = 0; i < nomes.getTamanho(); i += 100) {
            if (tabelaFixa.buscar(nomes.get(i))) {
                encontrados2++;
            }
        }
        
        long tempoBusca2 = cronometro2.obterTempoDecorrido();
        
        System.out.println("Tabela Redimensionavel:");
        System.out.println("  Capacidade: " + tabelaRedimensionavel.getCapacidade());
        System.out.println("  Fator de carga: " + String.format("%.3f", tabelaRedimensionavel.getLoadFactor()));
        System.out.println("  Tempo de busca: " + (tempoBusca1 / 1_000_000.0) + " ms");
        System.out.println("  Elementos encontrados: " + encontrados1);
        
        System.out.println("\nTabela Fixa:");
        System.out.println("  Capacidade: " + tabelaFixa.getCapacidade());
        System.out.println("  Fator de carga: " + String.format("%.3f", tabelaFixa.getLoadFactor()));
        System.out.println("  Tempo de busca: " + (tempoBusca2 / 1_000_000.0) + " ms");
        System.out.println("  Elementos encontrados: " + encontrados2);
        
        System.out.println("\nComparacao:");
        System.out.println("  Diferenca de capacidade: " + (tabelaRedimensionavel.getCapacidade() - tabelaFixa.getCapacidade()));
        System.out.println("  Diferenca de fator de carga: " + String.format("%.3f", tabelaRedimensionavel.getLoadFactor() - tabelaFixa.getLoadFactor()));
        System.out.println("  Diferenca de tempo de busca: " + String.format("%.3f", (tempoBusca1 - tempoBusca2) / 1_000_000.0) + " ms");
    }
    
    public static void main(String[] args) {
        imprimirTitulo("DEMONSTRACAO DE REDIMENSIONAMENTO DE TABELAS HASH");
        
        System.out.println("\n[ETAPA 1] Lendo arquivo de nomes...");
        ListaDinamica nomes = LeitorArquivo.lerArquivo("female_names.txt");
        System.out.println("Total de nomes lidos: " + nomes.getTamanho());
        
        System.out.println("\n[ETAPA 2] Criando tabelas hash redimensionaveis com encadeamento externo...");
        TabelaHashRedimensionavel tabelaRedimensionavel1 = new TabelaHashRedimensionavel1();
        TabelaHashRedimensionavel tabelaRedimensionavel2 = new TabelaHashRedimensionavel2();
        
        System.out.println("Capacidade inicial: " + tabelaRedimensionavel1.getCapacidade());
        System.out.println("Fator de carga maximo: 0.75");
        
        imprimirTitulo("REDIMENSIONAMENTO - ENCADEAMENTO EXTERNO");
        
        demonstrarRedimensionamentoExterno(tabelaRedimensionavel1, nomes, "Hash Redimensionavel 1: Quantidade de Letras - 1");
        demonstrarRedimensionamentoExterno(tabelaRedimensionavel2, nomes, "Hash Redimensionavel 2: Chave % Tamanho");
        
        System.out.println("\n[ETAPA 3] Criando tabelas hash redimensionaveis com enderecamento aberto...");
        TabelaHashRedimensionavelAberto tabelaRedimensionavelLinear = new TabelaHashRedimensionavelLinearProbing();
        TabelaHashRedimensionavelAberto tabelaRedimensionavelQuadratica = new TabelaHashRedimensionavelQuadraticProbing();
        
        imprimirTitulo("REDIMENSIONAMENTO - ENDERECAMENTO ABERTO");
        
        demonstrarRedimensionamentoAberto(tabelaRedimensionavelLinear, nomes, "Linear Probing Redimensionavel");
        demonstrarRedimensionamentoAberto(tabelaRedimensionavelQuadratica, nomes, "Quadratic Probing Redimensionavel");
        
        System.out.println("\n[ETAPA 4] Criando tabelas fixas para comparacao...");
        TabelaHash tabelaFixa1 = new TabelaHash1();
        TabelaHash tabelaFixa2 = new TabelaHash2();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabelaFixa1.inserir(nomes.get(i));
            tabelaFixa2.inserir(nomes.get(i));
        }
        
        imprimirTitulo("COMPARACAO DE PERFORMANCE");
        
        compararComTabelasFixas(tabelaRedimensionavel1, tabelaFixa1, nomes);
        compararComTabelasFixas(tabelaRedimensionavel2, tabelaFixa2, nomes);
        
        imprimirTitulo("ANALISE DE BENEFICIOS DO REDIMENSIONAMENTO");
        
        System.out.println("\nBeneficios do Redimensionamento:");
        System.out.println("1. Mantem fator de carga baixo (< 0.75)");
        System.out.println("2. Reduz colisoes e melhora performance");
        System.out.println("3. Adapta-se automaticamente ao tamanho dos dados");
        System.out.println("4. Evita degradacao de performance com muitos elementos");
        
        System.out.println("\nCustos do Redimensionamento:");
        System.out.println("1. Tempo adicional para rehashing");
        System.out.println("2. Uso de memoria temporaria durante redimensionamento");
        System.out.println("3. Complexidade adicional na implementacao");
        
        System.out.println("\nQuando usar Redimensionamento:");
        System.out.println("- Quando o tamanho dos dados e desconhecido");
        System.out.println("- Quando performance de busca e critica");
        System.out.println("- Quando memoria nao e limitacao");
        
        imprimirLinha();
        System.out.println("FIM DA DEMONSTRACAO DE REDIMENSIONAMENTO");
        imprimirLinha();
    }
}
