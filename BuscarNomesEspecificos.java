// Using custom data structures as per project rules

public class BuscarNomesEspecificos {
    private static void imprimirLinha() { System.out.println("================================================================"); }
    
    private static void imprimirTitulo(String titulo) {
        imprimirLinha();
        System.out.println(titulo);
        imprimirLinha();
    }
    
    private static void demonstrarBuscaExterna(TabelaHash tabela, String nomeFuncao) {
        System.out.println("\n" + nomeFuncao);
        System.out.println("---------------------------------------------------");
        
        // Buscar alguns nomes específicos
        String[] nomesParaBuscar = {"Alice", "Maria", "Ana", "Sofia", "Julia", "Beatriz", "Laura", "Gabriela"};
        
        for (String nome : nomesParaBuscar) {
            String resultado = tabela.buscarComPosicao(nome);
            System.out.println(resultado);
        }
        
        // Mostrar distribuição da posição onde cada nome deveria estar
        System.out.println("\nCalculo das posicoes teoricas:");
        for (String nome : nomesParaBuscar) {
            int posicaoTeorica = calcularPosicaoTeorica(tabela, nome);
            System.out.println("Nome '" + nome + "' deveria estar na posicao [" + posicaoTeorica + "]");
        }
    }
    
    private static void demonstrarBuscaAberta(TabelaHashEnderecamentoAberto tabela, String nomeFuncao) {
        System.out.println("\n" + nomeFuncao);
        System.out.println("---------------------------------------------------");
        
        // Buscar alguns nomes específicos
        String[] nomesParaBuscar = {"Alice", "Maria", "Ana", "Sofia", "Julia", "Beatriz", "Laura", "Gabriela"};
        
        for (String nome : nomesParaBuscar) {
            String resultado = tabela.buscarComPosicao(nome);
            System.out.println(resultado);
        }
        
        // Mostrar distribuição da posição onde cada nome deveria estar
        System.out.println("\nCalculo das posicoes teoricas:");
        for (String nome : nomesParaBuscar) {
            int posicaoTeorica = calcularPosicaoTeoricaAberta(tabela, nome);
            System.out.println("Nome '" + nome + "' deveria estar na posicao [" + posicaoTeorica + "]");
        }
    }
    
    private static int calcularPosicaoTeorica(TabelaHash tabela, String nome) {
        // Simular o cálculo da função hash para mostrar a posição teórica
        if (tabela instanceof TabelaHash1) {
            return (nome.length() - 1) % 32;
        } else if (tabela instanceof TabelaHash2) {
            int hash = 0;
            for (int i = 0; i < nome.length(); i++) {
                hash += nome.charAt(i);
            }
            return hash % 32;
        }
        return -1;
    }
    
    private static int calcularPosicaoTeoricaAberta(TabelaHashEnderecamentoAberto tabela, String nome) {
        // Simular o cálculo da função hash para mostrar a posição teórica
        int hash = 0;
        for (int i = 0; i < nome.length(); i++) {
            hash += nome.charAt(i);
        }
        return hash % 32;
    }
    
    // Interactive search removed to comply with project rules (no built-in data structures)
    
    public static void main(String[] args) {
        imprimirTitulo("SISTEMA DE BUSCA DE NOMES ESPECIFICOS");
        
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
        
        imprimirTitulo("DEMONSTRACAO DE BUSCA COM POSICOES - ENCADEAMENTO EXTERNO");
        
        demonstrarBuscaExterna(tabela1, "Tabela Hash 1: Quantidade de Letras - 1");
        demonstrarBuscaExterna(tabela2, "Tabela Hash 2: Chave % Tamanho");
        
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
        
        imprimirTitulo("DEMONSTRACAO DE BUSCA COM POSICOES - ENDERECAMENTO ABERTO");
        
        demonstrarBuscaAberta(tabelaLinear, "Linear Probing");
        demonstrarBuscaAberta(tabelaQuadratica, "Quadratic Probing");
        demonstrarBuscaAberta(tabelaDoubleHashing, "Double Hashing");
        
        imprimirTitulo("ANALISE COMPARATIVA DE DISTRIBUICAO");
        
        System.out.println("\nComparacao de colisoes:");
        System.out.println("Encadeamento Externo - Hash 1: " + tabela1.getNumeroColisoes() + " colisoes");
        System.out.println("Encadeamento Externo - Hash 2: " + tabela2.getNumeroColisoes() + " colisoes");
        System.out.println("Linear Probing: " + tabelaLinear.getNumeroColisoes() + " colisoes");
        System.out.println("Quadratic Probing: " + tabelaQuadratica.getNumeroColisoes() + " colisoes");
        System.out.println("Double Hashing: " + tabelaDoubleHashing.getNumeroColisoes() + " colisoes");
        
        System.out.println("\nFatores de carga:");
        System.out.println("Encadeamento Externo: " + String.format("%.3f", tabela1.getLoadFactor()));
        System.out.println("Enderecamento Aberto: " + String.format("%.3f", tabelaLinear.getLoadFactor()));
        
        imprimirLinha();
        System.out.println("FIM DA DEMONSTRACAO");
        imprimirLinha();
    }
}
