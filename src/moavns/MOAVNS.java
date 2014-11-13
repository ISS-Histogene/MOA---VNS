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
import java.util.Scanner;

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
        InitialSolutionGreedy solucaoinicial = new InitialSolutionGreedy();
        solucaoinicial.leituraArquivoCons();
        System.out.println("Nome Arquivo: "+solucaoinicial.getNomearquivo());
        Solucao inicial = solucaoinicial.InitialGreedySolution();
        for(Coluna col : solucaoinicial.getLista_de_colunas_gulosas()){
            System.out.printf(col.getNome() + " ");
        }
        System.out.println();
        System.out.println("Custo Total : "+solucaoinicial.getCustoTotal());
        System.out.println("\n");
        System.out.printf("Sem redundancia: ");
        EstruturasVizinhanca semredun = new EstruturasVizinhanca(inicial);
        Solucao solucaosem = semredun.eliminarRedundancia(inicial);
        for(Coluna col : solucaosem.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        System.out.println();
        
        long endTime = System.currentTimeMillis();
        System.out.println("TEMPO DE PROCESSAMENTO INICIAL " + (endTime - solucaoinicial.getStartTime()) + " ms");
        System.out.println();
        long startTimex = System.currentTimeMillis();
        EstruturasVizinhanca oneflip = new EstruturasVizinhanca(inicial);
        Solucao solucao2 = oneflip.One_flip_best(inicial, 0);
        for(Coluna col : solucao2.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        
        System.out.println();
        
        System.out.printf("Sem redundancia: ");
        Solucao solucaosem1 = oneflip.eliminarRedundancia(solucao2);
        for(Coluna col : solucaosem1.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        System.out.println();
        long endTime2 = System.currentTimeMillis();
        System.out.println();
        System.out.println("Custo Total OneFlip: "+solucao2.getCustototal());
        System.out.println("Custo Total OneFlip Sem Redundancia: "+solucaosem1.getCustototal());
        System.out.println("TEMPO DE PROCESSAMENTO ONEFLIP " + (endTime2 - startTimex) + " ms");
        System.out.println();
        
        long startTimex2 = System.currentTimeMillis();
        EstruturasVizinhanca twoflip = new EstruturasVizinhanca(inicial);
        Solucao solucao3 = twoflip.Two_flip_best(inicial);
        for(Coluna col : solucao3.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        System.out.println();
        Solucao solucaosem2 = twoflip.eliminarRedundancia(solucao3);
        System.out.printf("Sem redundancia: ");
        for(Coluna col : solucaosem2.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        long endTime3 = System.currentTimeMillis();
        System.out.println();
        System.out.println("Custo Total TwoFlip: "+solucao3.getCustototal());
        System.out.println("Custo Total Sem Redundancia: "+solucaosem2.getCustototal());
        System.out.println("TEMPO DE PROCESSAMENTO TwoFLIP " + (endTime3 - startTimex2) + " ms");
        System.out.println();
        
       
        
        
        
        
    }
    
}
