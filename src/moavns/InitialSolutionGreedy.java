/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
        
import javax.swing.JFileChooser;

/**
 *
 * @author Gustavo
 */
public class InitialSolutionGreedy {
    
    public static Map<Integer, ArrayList<Integer>> linhasX = new HashMap();
    public static Map<Integer, Integer> ColunasCusto = new HashMap();
    public static Multimap<Integer, Integer> lista_de_linhas = TreeMultimap.create();
    public static ArrayList lista_de_colunas_gulosas = new ArrayList();
    public static ArrayList<Integer> linhasCobertas = new ArrayList();
    public static int qtdeLinhas = 0;
    public static int qtdeColunas = 0;
    public static int linhasRestantes = 0;
    public static String nomearquivo;
    public static long startTime;
    public static int custoTotal = 0;
    
    public static void leituraArquivo() throws IOException{
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            nomearquivo = file.getName();
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
            startTime = System.currentTimeMillis();
            String[] primeiralinha = reader.readLine().split(" ");
            int linhas = Integer.parseInt(primeiralinha[1]);
            qtdeLinhas = linhas;
            int colunas = Integer.parseInt(primeiralinha[2]);
            qtdeColunas = colunas;
            Integer cont = 1;
            while(cont<=colunas){
                String[] valores = reader.readLine().split(" ");
                for(int i = 1; i < valores.length; i++){
                    ArrayList<Integer> inicio = new ArrayList<Integer>();
                    linhasX.put(cont, inicio);
                    ColunasCusto.put(cont, Integer.parseInt(valores[i]));
                    cont++;
                }
            }
            Integer cont2 = 1;
            while(cont2<=linhas){
                
                int coberturalinha = Integer.parseInt(reader.readLine().split(" ")[1]);
                int cont3 = 1;
                while(cont3 <= coberturalinha){
                    String[] cobertura = reader.readLine().split(" ");
                    for(int j = 1; j<cobertura.length; j++){

                        if(linhasX.containsKey(Integer.parseInt(cobertura[j]))){

                            ArrayList adicionar = linhasX.get(Integer.parseInt(cobertura[j]));
                            adicionar.add(cont2);
                            linhasX.put(Integer.parseInt(cobertura[j]), adicionar);
                            cont3++;
                        }
                    }
                }
                cont2++;
            }
            for(Integer entry : linhasX.keySet()){
                int tamanho = linhasX.get(entry).size() *(-1);
                lista_de_linhas.put(tamanho, entry);
            }
        }
        
    }
    
    public static int melhorEscolha(int maiorcobertura){
        Iterator acharmelhor = lista_de_linhas.asMap().get(maiorcobertura).iterator();
        int menorcusto = Integer.MAX_VALUE;
        int melhorcoluna = 0;
        while(acharmelhor.hasNext()){
            int colunachecar = (Integer) acharmelhor.next();
            int custochecar = ColunasCusto.get(colunachecar);
            if (custochecar<menorcusto){
                menorcusto = custochecar;
                melhorcoluna = colunachecar;
            }
        }
        return melhorcoluna;
    }
    
    
    public static void InitialGreedySolution(){
        while (linhasRestantes<qtdeLinhas){

            Iterator it = lista_de_linhas.keySet().iterator();
            Integer maiorValor = (Integer) it.next();
            
            Integer coluna_que_mais_cobre = (Integer) lista_de_linhas.asMap().get(maiorValor).iterator().next();
            //Integer coluna_que_mais_cobre = melhorEscolha(maiorValor);
            custoTotal += ColunasCusto.get(coluna_que_mais_cobre);
            
            ArrayList colunasqueCobrem = linhasX.get(coluna_que_mais_cobre);
            lista_de_colunas_gulosas.add(coluna_que_mais_cobre);
            int valorReal = maiorValor * (-1);
            int c = 0;
            while(!(colunasqueCobrem.isEmpty())){
                linhasCobertas.add((Integer) colunasqueCobrem.get(c));
                removerValorColunas((Integer) colunasqueCobrem.get(c));
                linhasRestantes++;
                
            }
            atualizarTreeMap();
        }
    }

    
    
    public static void removerValorColunas(Integer linhaRemover){
        Iterator iterador = linhasX.keySet().iterator();
        while(iterador.hasNext()){
            Integer chave = (Integer) iterador.next();
            ArrayList listacolunas = linhasX.get(chave);
            listacolunas.remove(linhaRemover);
            linhasX.put(chave, listacolunas);
            
        }
    }
    
    public static void atualizarTreeMap(){
        List<Integer> listaprovisoria = new ArrayList();
        List<Integer> listaprovisoriaexcluir = new ArrayList();
        Iterator iterador = lista_de_linhas.keySet().iterator();
        while(iterador.hasNext()){
            Integer valorAtualizar = (Integer) iterador.next();
            int valorAtualizarReal = Math.abs(valorAtualizar);
            Iterator iterador2 = lista_de_linhas.get(valorAtualizar).iterator();
            while(iterador2.hasNext()){
                Integer colunaAtualizar = (Integer) iterador2.next();
                int novoTamanho = linhasX.get(colunaAtualizar).size();
                if(novoTamanho < valorAtualizarReal){
                    listaprovisoriaexcluir.add(valorAtualizar);
                    listaprovisoriaexcluir.add(colunaAtualizar);
                    
                    Integer novoTamanhoNeg = Math.negateExact(novoTamanho);
                    listaprovisoria.add(novoTamanhoNeg);
                    listaprovisoria.add(colunaAtualizar);
                }
            }
        }
        for(int h = 0; h <= (listaprovisoriaexcluir.size()-2); h+=2){
            if(lista_de_linhas.remove(listaprovisoriaexcluir.get(h), listaprovisoriaexcluir.get(h+1))){
                
            }
            else{

            }
        }
        
        for(int h = 0; h <= (listaprovisoria.size()-2); h+=2){
            lista_de_linhas.put(listaprovisoria.get(h), listaprovisoria.get(h+1));
        }
        
    }
    
    
}
