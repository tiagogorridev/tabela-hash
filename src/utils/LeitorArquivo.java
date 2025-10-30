package src.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import src.estruturas.lista.ListaDinamica;

public class LeitorArquivo {
    
    public static ListaDinamica lerArquivo(String nomeArquivo) {

        ListaDinamica nomes = new ListaDinamica();
        
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            
            String linha = br.readLine();

            while (linha != null) {
                
                linha = linha.trim();
                
                if (linha.length() > 0) nomes.adicionar(linha);

                linha = br.readLine();

            }
            
            br.close();
            
        } catch (IOException e) {

            System.out.println("Erro ao ler arquivo: " + e.getMessage());

        }
        
        return nomes;

    }

}