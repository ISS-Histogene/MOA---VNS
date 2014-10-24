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
    
    public static Map<Integer, ArrayList> linhasX = new HashMap();
    public static Multimap<Integer, Integer> lista_de_linhas = TreeMultimap.create();
    public static ArrayList lista_de_colunas_gulosas = new ArrayList();
    public static ArrayList<Integer> linhasCobertas = new ArrayList();
    public static int qtdeLinhas = 0;
    public static int qtdeColunas = 0;
    public static int linhasRestantes = 0;
    
    public static void leituraArquivo() throws IOException{
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
            String[] primeiralinha = reader.readLine().split(" ");
            int linhas = Integer.parseInt(primeiralinha[1]);
            qtdeLinhas = linhas;
            int colunas = Integer.parseInt(primeiralinha[2]);
            qtdeColunas = colunas;
            Integer cont = 1;
            while(cont<=colunas){
                String[] valores = reader.readLine().split(" ");
                for(int i = 1; i < valores.length; i++){
                    cont++;
                }
            }
            Integer cont2 = 1;
            while(cont2<=linhas){
                ArrayList<Integer> colunasquecobrem = new ArrayList();
                int coberturalinha = Integer.parseInt(reader.readLine().split(" ")[1]);
                int cont3 = 1;
                while(cont3 <= coberturalinha){
                    String[] cobertura = reader.readLine().split(" ");
                    for(int j = 1; j<cobertura.length; j++){
                        colunasquecobrem.add(Integer.parseInt(cobertura[j]));
                        cont3++;
                    }
                }
                linhasX.put(cont2, colunasquecobrem);
                lista_de_linhas.put(colunasquecobrem.size()*(-1), cont2);
                cont2++;
            }
        }
        
    }
    
    
    public static void InitialGreedySolution(){
        while (linhasRestantes<qtdeLinhas){
            Iterator it = lista_de_linhas.keySet().iterator();
            int maiorValor = (int) it.next();
            int coluna_que_mais_cobre = lista_de_linhas.asMap().get(maiorValor).iterator().next();
            System.out.println("Maior Valor: "+maiorValor);
            System.out.println("coluna: "+coluna_que_mais_cobre);
            ArrayList colunasqueCobrem = linhasX.get(coluna_que_mais_cobre);
            lista_de_colunas_gulosas.add(coluna_que_mais_cobre);
            int valorReal = maiorValor * (-1);
            int c = 0;
            while(!(colunasqueCobrem.isEmpty())){
                linhasCobertas.add((Integer) colunasqueCobrem.get(c));
                removerValorColunas((Integer) colunasqueCobrem.get(c));
                
            }
            linhasRestantes+= valorReal;
            atualizarTreeMap();
        }
    }
    
    
    public static void removerValorColunas(int linhaRemover){
        Iterator iterador = linhasX.keySet().iterator();
        while(iterador.hasNext()){
            ArrayList listacolunas = (ArrayList) linhasX.get(iterador.next());
            if (listacolunas.remove((Object) linhaRemover)){

            }
        }
    }
    
    public static void atualizarTreeMap(){
        List<Integer> listaprovisoria = new ArrayList();
        List<Integer> listaprovisoriaexcluir = new ArrayList();
        Iterator iterador = lista_de_linhas.keySet().iterator();
        while(iterador.hasNext()){
            int valorAtualizar = (int) iterador.next();
            int valorAtualizarReal = valorAtualizar*(-1);
            Iterator iterador2 = lista_de_linhas.asMap().get(valorAtualizar).iterator();
            while(iterador2.hasNext()){
                Integer colunaAtualizar = (Integer) iterador2.next();
                int novoTamanho = linhasX.get(colunaAtualizar).size();
                if(novoTamanho < valorAtualizarReal){
                    listaprovisoriaexcluir.add(valorAtualizar);
                    listaprovisoriaexcluir.add(colunaAtualizar);
                    
                    listaprovisoria.add(novoTamanho);
                    listaprovisoria.add(colunaAtualizar);
                }
            }
        }
        for(int h = 0; h <= (listaprovisoriaexcluir.size()-2); h+=2){
            lista_de_linhas.remove(listaprovisoriaexcluir.get(h), listaprovisoriaexcluir.get(h+1));
        }
        
        for(int h = 0; h <= (listaprovisoria.size()-2); h+=2){
            lista_de_linhas.put(listaprovisoria.get(h), listaprovisoria.get(h+1));
        }
        
    }
    
    
}
