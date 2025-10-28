public class BuscaComContadoresAcesso {
    private static void imprimirLinha() { System.out.println("================================================================"); }
    
    private static void imprimirTitulo(String titulo) {
        imprimirLinha();
        System.out.println(titulo);
        imprimirLinha();
    }
    
    private static void imprimirContadoresAcesso(TabelaHash tabela) {
        System.out.println("\n" + tabela.getNomeFuncaoHash());
        System.out.println("---------------------------------------------------");
        
        int[] contadores = tabela.getContadoresAcesso();
        int[] distribuicao = tabela.getDistribuicao();
        
        System.out.println("Posicao | Elementos | Acessos | Taxa de Acesso");
        System.out.println("--------|----------|---------|----------------");
        
        for (int i = 0; i < contadores.length; i++) {
            double taxaAcesso = distribuicao[i] > 0 ? (double) contadores[i] / distribuicao[i] : 0;
            System.out.printf("   %2d   |    %2d     |   %3d   |     %.2f%n", 
                i, distribuicao[i], contadores[i], taxaAcesso);
        }
        
        System.out.println("\nEstatisticas de Acesso:");
        System.out.println("Total de acessos: " + tabela.getTotalAcessos());
        System.out.println("Media de acessos por posicao: " + String.format("%.2f", tabela.getMediaAcessosPorPosicao()));
        
        // Encontrar posições mais e menos acessadas
        int maxAcessos = contadores[0];
        int minAcessos = contadores[0];
        int posMaxAcessos = 0;
        int posMinAcessos = 0;
        
        for (int i = 1; i < contadores.length; i++) {
            if (contadores[i] > maxAcessos) {
                maxAcessos = contadores[i];
                posMaxAcessos = i;
            }
            if (contadores[i] < minAcessos) {
                minAcessos = contadores[i];
                posMinAcessos = i;
            }
        }
        
        System.out.println("Posicao mais acessada: [" + posMaxAcessos + "] com " + maxAcessos + " acessos");
        System.out.println("Posicao menos acessada: [" + posMinAcessos + "] com " + minAcessos + " acessos");
    }
    
    private static void imprimirContadoresAcessoAberto(TabelaHashEnderecamentoAberto tabela) {
        System.out.println("\n" + tabela.getNomeFuncaoHash());
        System.out.println("---------------------------------------------------");
        
        int[] contadores = tabela.getContadoresAcesso();
        int[] distribuicao = tabela.getDistribuicao();
        
        System.out.println("Posicao | Ocupada | Acessos | Taxa de Acesso");
        System.out.println("--------|---------|---------|----------------");
        
        for (int i = 0; i < contadores.length; i++) {
            double taxaAcesso = distribuicao[i] > 0 ? (double) contadores[i] / distribuicao[i] : 0;
            System.out.printf("   %2d   |   %s    |   %3d   |     %.2f%n", 
                i, distribuicao[i] == 1 ? "SIM" : "NAO", contadores[i], taxaAcesso);
        }
        
        System.out.println("\nEstatisticas de Acesso:");
        System.out.println("Total de acessos: " + tabela.getTotalAcessos());
        System.out.println("Media de acessos por posicao: " + String.format("%.2f", tabela.getMediaAcessosPorPosicao()));
        
        // Encontrar posições mais e menos acessadas
        int maxAcessos = contadores[0];
        int minAcessos = contadores[0];
        int posMaxAcessos = 0;
        int posMinAcessos = 0;
        
        for (int i = 1; i < contadores.length; i++) {
            if (contadores[i] > maxAcessos) {
                maxAcessos = contadores[i];
                posMaxAcessos = i;
            }
            if (contadores[i] < minAcessos) {
                minAcessos = contadores[i];
                posMinAcessos = i;
            }
        }
        
        System.out.println("Posicao mais acessada: [" + posMaxAcessos + "] com " + maxAcessos + " acessos");
        System.out.println("Posicao menos acessada: [" + posMinAcessos + "] com " + minAcessos + " acessos");
    }
    
    private static void realizarTesteBusca(TabelaHash tabela, ListaDinamica nomes, String nomeTeste) {
        System.out.println("\n=== TESTE DE BUSCA: " + nomeTeste + " ===");
        
        // Resetar contadores antes do teste
        tabela.resetarContadoresAcesso();
        
        // Realizar buscas
        int encontrados = 0;
        for (int i = 0; i < nomes.getTamanho(); i += 50) { // Buscar a cada 50 nomes
            if (tabela.buscar(nomes.get(i))) {
                encontrados++;
            }
        }
        
        System.out.println("Total de buscas realizadas: " + (nomes.getTamanho() / 50));
        System.out.println("Nomes encontrados: " + encontrados);
        System.out.println("Taxa de sucesso: " + String.format("%.1f", (encontrados * 100.0 / (nomes.getTamanho() / 50))) + "%");
        
        imprimirContadoresAcesso(tabela);
    }
    
    private static void realizarTesteBuscaAberto(TabelaHashEnderecamentoAberto tabela, ListaDinamica nomes, String nomeTeste) {
        System.out.println("\n=== TESTE DE BUSCA: " + nomeTeste + " ===");
        
        // Resetar contadores antes do teste
        tabela.resetarContadoresAcesso();
        
        // Realizar buscas
        int encontrados = 0;
        for (int i = 0; i < nomes.getTamanho(); i += 50) { // Buscar a cada 50 nomes
            if (tabela.buscar(nomes.get(i))) {
                encontrados++;
            }
        }
        
        System.out.println("Total de buscas realizadas: " + (nomes.getTamanho() / 50));
        System.out.println("Nomes encontrados: " + encontrados);
        System.out.println("Taxa de sucesso: " + String.format("%.1f", (encontrados * 100.0 / (nomes.getTamanho() / 50))) + "%");
        
        imprimirContadoresAcessoAberto(tabela);
    }
    
    public static void main(String[] args) {
        imprimirTitulo("SISTEMA DE BUSCA COM CONTADORES DE ACESSO");
        
        System.out.println("\n[ETAPA 1] Lendo arquivo de nomes...");
        ListaDinamica nomes = LeitorArquivo.lerArquivo("female_names.txt");
        System.out.println("Total de nomes lidos: " + nomes.getTamanho());
        
        System.out.println("\n[ETAPA 2] Criando tabelas hash com encadeamento externo...");
        TabelaHash tabela1 = new TabelaHash1();
        TabelaHash tabela2 = new TabelaHash2();
        
        System.out.println("\n[ETAPA 3] Inserindo nomes nas tabelas...");
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabela1.inserir(nomes.get(i));
            tabela2.inserir(nomes.get(i));
        }
        System.out.println("Insercao concluida!");
        
        imprimirTitulo("ANALISE DE CONTADORES DE ACESSO - ENCADEAMENTO EXTERNO");
        
        realizarTesteBusca(tabela1, nomes, "Tabela Hash 1: Quantidade de Letras - 1");
        realizarTesteBusca(tabela2, nomes, "Tabela Hash 2: Chave % Tamanho");
        
        System.out.println("\n[ETAPA 4] Criando tabelas hash com enderecamento aberto...");
        TabelaHashEnderecamentoAberto tabelaLinear = new TabelaHashLinearProbing();
        TabelaHashEnderecamentoAberto tabelaQuadratica = new TabelaHashQuadraticProbing();
        TabelaHashEnderecamentoAberto tabelaDoubleHashing = new TabelaHashDoubleHashing();
        
        System.out.println("\n[ETAPA 5] Inserindo nomes nas tabelas de enderecamento aberto...");
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabelaLinear.inserir(nomes.get(i));
            tabelaQuadratica.inserir(nomes.get(i));
            tabelaDoubleHashing.inserir(nomes.get(i));
        }
        System.out.println("Insercao concluida!");
        
        imprimirTitulo("ANALISE DE CONTADORES DE ACESSO - ENDERECAMENTO ABERTO");
        
        realizarTesteBuscaAberto(tabelaLinear, nomes, "Linear Probing");
        realizarTesteBuscaAberto(tabelaQuadratica, nomes, "Quadratic Probing");
        realizarTesteBuscaAberto(tabelaDoubleHashing, nomes, "Double Hashing");
        
        imprimirTitulo("COMPARACAO FINAL DE EFICIENCIA");
        
        System.out.println("\nComparacao de Acessos Totais:");
        System.out.println("Encadeamento Externo - Hash 1: " + tabela1.getTotalAcessos() + " acessos");
        System.out.println("Encadeamento Externo - Hash 2: " + tabela2.getTotalAcessos() + " acessos");
        System.out.println("Linear Probing: " + tabelaLinear.getTotalAcessos() + " acessos");
        System.out.println("Quadratic Probing: " + tabelaQuadratica.getTotalAcessos() + " acessos");
        System.out.println("Double Hashing: " + tabelaDoubleHashing.getTotalAcessos() + " acessos");
        
        System.out.println("\nMedia de Acessos por Posicao:");
        System.out.println("Encadeamento Externo - Hash 1: " + String.format("%.2f", tabela1.getMediaAcessosPorPosicao()));
        System.out.println("Encadeamento Externo - Hash 2: " + String.format("%.2f", tabela2.getMediaAcessosPorPosicao()));
        System.out.println("Linear Probing: " + String.format("%.2f", tabelaLinear.getMediaAcessosPorPosicao()));
        System.out.println("Quadratic Probing: " + String.format("%.2f", tabelaQuadratica.getMediaAcessosPorPosicao()));
        System.out.println("Double Hashing: " + String.format("%.2f", tabelaDoubleHashing.getMediaAcessosPorPosicao()));
        
        imprimirLinha();
        System.out.println("FIM DA ANALISE DE CONTADORES DE ACESSO");
        imprimirLinha();
    }
}
