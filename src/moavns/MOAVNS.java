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
        EstruturasVizinhanca semredun = new EstruturasVizinhanca(inicial);
        Solucao solucaosem = semredun.eliminarRedundancia(inicial);
        System.out.println("Solucao: "+solucaosem.getCustototal());
        int limite = 0;
        Solucao solucaox = new Solucao(solucaosem);
        while(limite<=80){
            Solucao solucaoaleatoria = SolucaoAleatoria.Vizinhos(solucaox, 0);
            //System.out.println(solucaoaleatoria.getCustototal());
            Solucao solucaovnd = VND.VND(solucaoaleatoria);
            //System.out.println(solucaovnd.getCustototal());
            if(solucaovnd.getCustototal()<solucaox.getCustototal()){
                solucaox = solucaovnd;
                limite = 0;
            }
            else{
                limite += 1;
            }
        }
        
        for(Coluna col : solucaox.getColunas()){
                    System.out.printf(col.getNome()+" ");
                }
                System.out.println();
                System.out.println("Custo Total: "+solucaox.getCustototal());

       
        
        
        
        
    }
    
}
