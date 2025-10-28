// Using custom data structures as per project rules

public class BuscaComInformacoesDetalhadas {
    private static void imprimirLinha() { System.out.println("================================================================"); }
    
    private static void imprimirTitulo(String titulo) {
        imprimirLinha();
        System.out.println(titulo);
        imprimirLinha();
    }
    
    private static void demonstrarBuscaDetalhadaExterna(TabelaHash tabela, String[] nomesParaBuscar) {
        System.out.println("\n" + tabela.getNomeFuncaoHash());
        System.out.println("---------------------------------------------------");
        
        ListaResultadosBusca resultados = new ListaResultadosBusca();
        
        for (String nome : nomesParaBuscar) {
            ResultadoBusca resultado = tabela.buscarDetalhado(nome);
            resultados.adicionar(resultado);
            System.out.println(resultado.toString());
        }
        
        // Estatísticas dos resultados
        imprimirEstatisticasResultados(resultados, tabela.getNomeFuncaoHash());
    }
    
    private static void demonstrarBuscaDetalhadaAberta(TabelaHashEnderecamentoAberto tabela, String[] nomesParaBuscar) {
        System.out.println("\n" + tabela.getNomeFuncaoHash());
        System.out.println("---------------------------------------------------");
        
        ListaResultadosBusca resultados = new ListaResultadosBusca();
        
        for (String nome : nomesParaBuscar) {
            ResultadoBusca resultado = tabela.buscarDetalhado(nome);
            resultados.adicionar(resultado);
            System.out.println(resultado.toString());
        }
        
        // Estatísticas dos resultados
        imprimirEstatisticasResultados(resultados, tabela.getNomeFuncaoHash());
    }
    
    private static void demonstrarBuscaDetalhadaRedimensionavel(TabelaHashRedimensionavel tabela, String[] nomesParaBuscar) {
        System.out.println("\n" + tabela.getNomeFuncaoHash());
        System.out.println("---------------------------------------------------");
        
        ListaResultadosBusca resultados = new ListaResultadosBusca();
        
        for (String nome : nomesParaBuscar) {
            ResultadoBusca resultado = tabela.buscarDetalhado(nome);
            resultados.adicionar(resultado);
            System.out.println(resultado.toString());
        }
        
        // Estatísticas dos resultados
        imprimirEstatisticasResultados(resultados, tabela.getNomeFuncaoHash());
    }
    
    private static void demonstrarBuscaDetalhadaRedimensionavelAberto(TabelaHashRedimensionavelAberto tabela, String[] nomesParaBuscar) {
        System.out.println("\n" + tabela.getNomeFuncaoHash());
        System.out.println("---------------------------------------------------");
        
        ListaResultadosBusca resultados = new ListaResultadosBusca();
        
        for (String nome : nomesParaBuscar) {
            ResultadoBusca resultado = tabela.buscarDetalhado(nome);
            resultados.adicionar(resultado);
            System.out.println(resultado.toString());
        }
        
        // Estatísticas dos resultados
        imprimirEstatisticasResultados(resultados, tabela.getNomeFuncaoHash());
    }
    
    private static void imprimirEstatisticasResultados(ListaResultadosBusca resultados, String nomeTabela) {
        System.out.println("\n=== ESTATISTICAS DE BUSCA: " + nomeTabela + " ===");
        
        int totalBuscas = resultados.getTamanho();
        int encontrados = 0;
        int naoEncontrados = 0;
        int totalAcessos = 0;
        int maxAcessos = 0;
        int minAcessos = Integer.MAX_VALUE;
        
        for (int i = 0; i < resultados.getTamanho(); i++) {
            ResultadoBusca resultado = resultados.get(i);
            if (resultado.isEncontrado()) {
                encontrados++;
            } else {
                naoEncontrados++;
            }
            
            totalAcessos += resultado.getAcessos();
            if (resultado.getAcessos() > maxAcessos) {
                maxAcessos = resultado.getAcessos();
            }
            if (resultado.getAcessos() < minAcessos) {
                minAcessos = resultado.getAcessos();
            }
        }
        
        System.out.println("Total de buscas: " + totalBuscas);
        System.out.println("Elementos encontrados: " + encontrados);
        System.out.println("Elementos nao encontrados: " + naoEncontrados);
        System.out.println("Taxa de sucesso: " + String.format("%.1f", (encontrados * 100.0 / totalBuscas)) + "%");
        System.out.println("Total de acessos: " + totalAcessos);
        System.out.println("Media de acessos por busca: " + String.format("%.2f", (double) totalAcessos / totalBuscas));
        System.out.println("Maximo de acessos em uma busca: " + maxAcessos);
        System.out.println("Minimo de acessos em uma busca: " + minAcessos);
    }
    
    private static void compararMetodosBusca(TabelaHash tabela1, TabelaHash tabela2, String[] nomesParaBuscar) {
        System.out.println("\n=== COMPARACAO DE METODOS DE BUSCA ===");
        
        ListaResultadosBusca resultados1 = new ListaResultadosBusca();
        ListaResultadosBusca resultados2 = new ListaResultadosBusca();
        
        for (String nome : nomesParaBuscar) {
            resultados1.adicionar(tabela1.buscarDetalhado(nome));
            resultados2.adicionar(tabela2.buscarDetalhado(nome));
        }
        
        System.out.println("\nComparacao de Eficiencia:");
        System.out.println("Tabela 1 (" + tabela1.getNomeFuncaoHash() + "):");
        imprimirEstatisticasResultados(resultados1, tabela1.getNomeFuncaoHash());
        
        System.out.println("\nTabela 2 (" + tabela2.getNomeFuncaoHash() + "):");
        imprimirEstatisticasResultados(resultados2, tabela2.getNomeFuncaoHash());
        
        // Comparação direta
        int totalAcessos1 = 0;
        int totalAcessos2 = 0;
        for (int i = 0; i < resultados1.getTamanho(); i++) {
            totalAcessos1 += resultados1.get(i).getAcessos();
        }
        for (int i = 0; i < resultados2.getTamanho(); i++) {
            totalAcessos2 += resultados2.get(i).getAcessos();
        }
        
        System.out.println("\nComparacao Direta:");
        System.out.println("Diferenca de acessos totais: " + Math.abs(totalAcessos1 - totalAcessos2));
        System.out.println("Metodo mais eficiente: " + (totalAcessos1 < totalAcessos2 ? tabela1.getNomeFuncaoHash() : tabela2.getNomeFuncaoHash()));
    }
    
    private static void analisarPadroesAcesso(ListaResultadosBusca resultados) {
        System.out.println("\n=== ANALISE DE PADROES DE ACESSO ===");
        
        // Agrupar por número de acessos
        int[] contadoresAcesso = new int[10]; // 0-9 acessos
        for (int i = 0; i < resultados.getTamanho(); i++) {
            ResultadoBusca resultado = resultados.get(i);
            int acessos = resultado.getAcessos();
            if (acessos < contadoresAcesso.length) {
                contadoresAcesso[acessos]++;
            }
        }
        
        System.out.println("Distribuicao de acessos:");
        for (int i = 0; i < contadoresAcesso.length; i++) {
            if (contadoresAcesso[i] > 0) {
                System.out.println("  " + i + " acessos: " + contadoresAcesso[i] + " buscas");
            }
        }
        
        // Análise de posições mais acessadas
        System.out.println("\nPosicoes mais acessadas:");
        int[] contadoresPosicao = new int[32]; // Assumindo capacidade máxima de 32
        for (int i = 0; i < resultados.getTamanho(); i++) {
            ResultadoBusca resultado = resultados.get(i);
            if (resultado.getPosicao() < contadoresPosicao.length) {
                contadoresPosicao[resultado.getPosicao()]++;
            }
        }
        
        // Encontrar as 5 posições mais acessadas
        for (int rank = 0; rank < 5; rank++) {
            int maxPos = 0;
            int maxCount = 0;
            for (int i = 0; i < contadoresPosicao.length; i++) {
                if (contadoresPosicao[i] > maxCount) {
                    maxCount = contadoresPosicao[i];
                    maxPos = i;
                }
            }
            if (maxCount > 0) {
                System.out.println("  Posicao [" + maxPos + "]: " + maxCount + " buscas");
                contadoresPosicao[maxPos] = 0; // Remove para próxima iteração
            }
        }
    }
    
    public static void main(String[] args) {
        imprimirTitulo("SISTEMA DE BUSCA COM INFORMACOES DETALHADAS");
        
        System.out.println("\n[ETAPA 1] Lendo arquivo de nomes...");
        ListaDinamica nomes = LeitorArquivo.lerArquivo("female_names.txt");
        System.out.println("Total de nomes lidos: " + nomes.getTamanho());
        
        System.out.println("\n[ETAPA 2] Criando tabelas hash...");
        TabelaHash tabela1 = new TabelaHash1();
        TabelaHash tabela2 = new TabelaHash2();
        TabelaHashEnderecamentoAberto tabelaLinear = new TabelaHashLinearProbing();
        TabelaHashEnderecamentoAberto tabelaQuadratica = new TabelaHashQuadraticProbing();
        TabelaHashRedimensionavel tabelaRedimensionavel1 = new TabelaHashRedimensionavel1();
        TabelaHashRedimensionavel tabelaRedimensionavel2 = new TabelaHashRedimensionavel2();
        TabelaHashRedimensionavelAberto tabelaRedimensionavelLinear = new TabelaHashRedimensionavelLinearProbing();
        
        System.out.println("\n[ETAPA 3] Inserindo nomes nas tabelas...");
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabela1.inserir(nomes.get(i));
            tabela2.inserir(nomes.get(i));
            tabelaLinear.inserir(nomes.get(i));
            tabelaQuadratica.inserir(nomes.get(i));
            tabelaRedimensionavel1.inserir(nomes.get(i));
            tabelaRedimensionavel2.inserir(nomes.get(i));
            tabelaRedimensionavelLinear.inserir(nomes.get(i));
        }
        System.out.println("Insercao concluida!");
        
        // Selecionar nomes para busca detalhada
        String[] nomesParaBuscar = {
            "Alice", "Maria", "Ana", "Sofia", "Julia", 
            "Beatriz", "Laura", "Gabriela", "Isabella", "Manuela",
            "Larissa", "Valentina", "Giovanna", "Mariana", "Nicole"
        };
        
        imprimirTitulo("BUSCA DETALHADA - ENCADEAMENTO EXTERNO");
        
        demonstrarBuscaDetalhadaExterna(tabela1, nomesParaBuscar);
        demonstrarBuscaDetalhadaExterna(tabela2, nomesParaBuscar);
        
        imprimirTitulo("BUSCA DETALHADA - ENDERECAMENTO ABERTO");
        
        demonstrarBuscaDetalhadaAberta(tabelaLinear, nomesParaBuscar);
        demonstrarBuscaDetalhadaAberta(tabelaQuadratica, nomesParaBuscar);
        
        imprimirTitulo("BUSCA DETALHADA - TABELAS REDIMENSIONAVEIS");
        
        demonstrarBuscaDetalhadaRedimensionavel(tabelaRedimensionavel1, nomesParaBuscar);
        demonstrarBuscaDetalhadaRedimensionavel(tabelaRedimensionavel2, nomesParaBuscar);
        demonstrarBuscaDetalhadaRedimensionavelAberto(tabelaRedimensionavelLinear, nomesParaBuscar);
        
        imprimirTitulo("COMPARACAO DE METODOS");
        
        compararMetodosBusca(tabela1, tabela2, nomesParaBuscar);
        
        // Análise de padrões
        ListaResultadosBusca todosResultados = new ListaResultadosBusca();
        for (String nome : nomesParaBuscar) {
            todosResultados.adicionar(tabela1.buscarDetalhado(nome));
        }
        analisarPadroesAcesso(todosResultados);
        
        imprimirTitulo("RESUMO DAS INFORMACOES DETALHADAS");
        
        System.out.println("\nInformacoes fornecidas por cada busca:");
        System.out.println("✓ boolean encontrado - Se o elemento foi encontrado");
        System.out.println("✓ int posicao - Posicao onde o elemento foi encontrado/buscado");
        System.out.println("✓ int acessos - Numero de acessos realizados durante a busca");
        System.out.println("✓ String chave - Chave que foi buscada");
        System.out.println("✓ String metodoHash - Metodo de hash utilizado");
        System.out.println("✓ String detalhesAdicionais - Informacoes extras sobre a busca");
        
        System.out.println("\nBeneficios da busca detalhada:");
        System.out.println("• Analise de performance detalhada");
        System.out.println("• Identificacao de padroes de acesso");
        System.out.println("• Comparacao de eficiencia entre metodos");
        System.out.println("• Debugging e otimizacao de hash functions");
        System.out.println("• Monitoramento de colisoes e distribuicao");
        
        imprimirLinha();
        System.out.println("FIM DA DEMONSTRACAO DE BUSCA DETALHADA");
        imprimirLinha();
    }
}
