public class LeitorArquivo {
    
    public static ListaDinamica lerArquivo(String nomeArquivo) {
        ListaDinamica nomes = new ListaDinamica();
        
        try {
            java.io.FileReader fr = new java.io.FileReader("female_names.txt");
            java.io.BufferedReader br = new java.io.BufferedReader(fr);
            
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.length() > 0) {
                    nomes.adicionar(linha);
                }
            }
            
            br.close();
            fr.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        
        return nomes;
    }
}