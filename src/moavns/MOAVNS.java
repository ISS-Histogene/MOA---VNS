/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

import com.google.common.collect.Ordering;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Gustavo
 */
public class MOAVNS {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    
    
    public static ArrayList<String> solucoes = new ArrayList();
    
    public static String transformaSolucao(Solucao solucao){
        SortedSet<Integer> cols = new TreeSet();
        String add = "";
        for(Coluna col : solucao.getColunas()){
            cols.add(col.getNome());
        }
        for(Integer entrada : cols){
            add+= String.valueOf(entrada);
            add+="/";
        }
        return add;
    }
    
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
        Solucao melhorsolucao  = new Solucao(solucaosem);
        int x = 0;
        while(limite<=20){
            //System.out.println(limite);
            Solucao solucaoaleatoria = SolucaoAleatoria.Vizinhos(solucaox, x);
            //System.out.println("aleatoria: "+solucaoaleatoria.getCustototal());
            solucoes.add(transformaSolucao(solucaoaleatoria));
            Solucao solucaovnd = VND.VND(solucaoaleatoria, 0);
            solucoes.add(transformaSolucao(solucaovnd));
            //System.out.println(solucaoaleatoria.getCustototal()+" "+solucaovnd.getCustototal()+" "+melhorsolucao.getCustototal());
            if(solucaovnd.getCustototal()<melhorsolucao.getCustototal()){
                System.out.println("MelhorSolucao: "+solucaovnd.getCustototal());
                solucaox = solucaovnd;
                melhorsolucao = solucaox;
            }
            else{
                //System.out.println("Caiu else");
                limite+=1;
                if(x==0){
                    x=1;
                }
                else{
                    if(x==1){
                        x=2;
                    }
                    else{
                        x = 0;
                    }
                }
            }
        }
        
        for(Coluna col : melhorsolucao.getColunas()){
                    System.out.printf(col.getNome()+" ");
                }
                System.out.println();
                System.out.println("Custo Total: "+melhorsolucao.getCustototal());

       
        
        
        
        
    }
    
}
