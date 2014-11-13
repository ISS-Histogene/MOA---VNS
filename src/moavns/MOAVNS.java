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
        long endTime = System.currentTimeMillis();
        System.out.println("TEMPO DE PROCESSAMENTO INICIAL " + (endTime - solucaoinicial.getStartTime()) + " ms");
        System.out.println();
        long startTimex = System.currentTimeMillis();
        OneFlip oneflip = new OneFlip(inicial);
        Solucao solucao2 = oneflip.One_flip_first(inicial, 0);
        for(Coluna col : solucao2.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println();
        System.out.println("Custo Total OneFlip: "+solucao2.getCustototal());
        System.out.println("TEMPO DE PROCESSAMENTO ONEFLIP " + (endTime2 - startTimex) + " ms");
        System.out.println();
        long startTimex2 = System.currentTimeMillis();
        OneFlip oneflipx = new OneFlip(inicial);
        Solucao solucao2x = oneflipx.eliminarRedundancia(inicial);
        for(Coluna col : solucao2x.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        long endTime2x = System.currentTimeMillis();
        System.out.println();
        System.out.println("Custo Total OneFlip Sem Redundancia: "+solucao2x.getCustototal());
        System.out.println("TEMPO DE PROCESSAMENTO ONEFLIP " + (endTime2x - startTimex2) + " ms");
        System.out.println();
        System.out.println("TEMPO DE PROCESSAMENTO TOTAL " + ((endTime - solucaoinicial.getStartTime())+(endTime2 - startTimex)+(endTime2x - startTimex2)) + " ms");
        
       
        
        
        
        
    }
    
}
