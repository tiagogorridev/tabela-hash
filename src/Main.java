package src;
import src.estruturas.hash.TabelaHash;
import src.estruturas.hash.TabelaHash1;
import src.estruturas.hash.TabelaHash2;
import src.estruturas.lista.ListaDinamica;
import src.utils.Cronometro;
import src.utils.LeitorArquivo;
import src.utils.PrintUtils;

public class Main {

    private static final String PATH_TXT = "src/resource/female_names.txt";
    
    public static void main(String[] args) {

        PrintUtils.imprimirTitulo("SISTEMA DE TABELAS HASH - ANALISE COMPARATIVA");
        
        System.out.println("\n[ETAPA 1] Lendo arquivo de nomes...");
        
        ListaDinamica nomes = LeitorArquivo.lerArquivo(PATH_TXT);

        System.out.printf("""
                Total de nomes lidos: %s
                \n[ETAPA 2] Criando tabelas hash...
        """, nomes.getTamanho());

        TabelaHash tabela1 = new TabelaHash1();
        TabelaHash tabela2 = new TabelaHash2();
        
        System.out.printf("""
                Capacidade de cada tabela: %s
                Metodo de tratamento de colisoes: Encadeamento Exterior
                \n[ETAPA 3] Inserindo nomes na Tabela 1...
        """, tabela1.getCapacidade());
        
        Cronometro cronometro1 = new Cronometro();
        cronometro1.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabela1.inserir(nomes.get(i));
        }
        
        long tempoInsercao1 = cronometro1.obterTempoDecorrido();

        System.out.println("""
                Insercao concluida!

                \n[ETAPA 4] Inserindo nomes na Tabela 2...
        """);

        Cronometro cronometro2 = new Cronometro();
        cronometro2.iniciar();
        
        for (int i = 0; i < nomes.getTamanho(); i++) {
            tabela2.inserir(nomes.get(i));
        }
        
        long tempoInsercao2 = cronometro2.obterTempoDecorrido();
        System.out.println("""
                Insercao concluida!

                \n[ETAPA 5] Realizando testes de busca...
        """);

        long tempoBusca1 = testarBuscas(tabela1, nomes);
        long tempoBusca2 = testarBuscas(tabela2, nomes);

        System.out.println("""
                Testes de busca concluidos!

                \n[ETAPA 6] Realizando testes de remocao...
                \nTabela 1:
        """);

        long tempoRemocao1 = testarRemocoes(tabela1, nomes);

        System.out.println("\nTabela 2:");
        
        long tempoRemocao2 = testarRemocoes(tabela2, nomes);
        
        System.out.println("\nTestes de remocao concluidos!");
        
        PrintUtils.imprimirTitulo("RESUMO FORMATADO");
        
        PrintUtils.printResumo(tabela1, tempoInsercao1, tempoBusca1, tempoRemocao1);
        
        System.out.println();
        
        PrintUtils.printResumo(tabela2, tempoInsercao2, tempoBusca2, tempoRemocao2);

        PrintUtils.imprimirTitulo("RELATORIO FINAL - RESULTADOS COMPARATIVOS");
        
        PrintUtils.imprimirRelatorioTabela(tabela1, tempoInsercao1, tempoBusca1);
        
        System.out.println();
        
        PrintUtils.imprimirRelatorioTabela(tabela2, tempoInsercao2, tempoBusca2);

        System.out.println();
        System.out.println("[ETAPA 7] Buscando nomes especificos com posicao...");
        String primeiro = nomes.get(0);
        String meio = nomes.get(nomes.getTamanho() / 2);
        String ultimo = nomes.get(nomes.getTamanho() - 1);
        String inexistente = "NOME_INEXISTENTE_123";
        String[] chavesBusca = new String[] { primeiro, meio, ultimo, inexistente };
        PrintUtils.imprimirBuscasComPosicao(tabela1, chavesBusca);
        PrintUtils.imprimirBuscasComPosicao(tabela2, chavesBusca);

        System.out.println();
        System.out.println("[ETAPA 8] Busca com contadores de acesso...");
        PrintUtils.imprimirBuscasComAcessos(tabela1, chavesBusca);
        PrintUtils.imprimirBuscasComAcessos(tabela2, chavesBusca);

        System.out.println();
        System.out.println("[ETAPA 9] Busca com informacoes detalhadas...");
        PrintUtils.imprimirBuscasDetalhadas(tabela1, chavesBusca);
        PrintUtils.imprimirBuscasDetalhadas(tabela2, chavesBusca);
        PrintUtils.imprimirTitulo("COMPARACAO DIRETA");

        System.out.printf("""
                \nNumero de Colisoes:
                Tabela 1: %s
                Tabela 2: %s
                Diferenca: %s
                \nFator de Carga:
                Tabela 1: %s
                Tabela 2: %s
                \nTempo de Insercao:
                Tabela 1: %s ms
                Tabela 2: %s ms
                \nTempo de Busca:
                Tabela 1: %s ms
                Tabela 2: %s ms
                \nTempo de Remocao:
                Tabela 1: %s ms
                Tabela 2: %s ms
            """, 
            tabela1.getNumeroColisoes(), 
            tabela2.getNumeroColisoes(), 
            (Math.abs(tabela1.getNumeroColisoes() - tabela2.getNumeroColisoes())),
            String.format("%.4f", tabela1.getFatorCarga()),
            String.format("%.4f", tabela2.getFatorCarga()),
            (tempoInsercao1 / 1_000_000.0),
            (tempoInsercao2 / 1_000_000.0),
            (tempoBusca1 / 1_000_000.0),
            (tempoBusca2 / 1_000_000.0),
            (tempoRemocao1 / 1_000_000.0),
            (tempoRemocao2 / 1_000_000.0)
        );
        
        PrintUtils.imprimirLinha();
        System.out.println("FIM DA EXECUCAO");
        PrintUtils.imprimirLinha();

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

}