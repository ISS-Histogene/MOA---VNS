/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Gustavo
 */
public class MOAVNS {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        InitialSolutionGreedy.leituraArquivo();
        System.out.println("Nome Arquivo: "+InitialSolutionGreedy.nomearquivo);
        InitialSolutionGreedy.InitialGreedySolution();
        System.out.println(InitialSolutionGreedy.lista_de_colunas_gulosas);
        long endTime = System.currentTimeMillis();
        System.out.println("TEMPO DE PROCESSAMENTO TOTAL " + (endTime - InitialSolutionGreedy.startTime) + " ms");
        System.out.println();
        
        
        
    }
    
}
