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
    
    private  Map<Integer, Coluna> linhasX = new HashMap();
    private  Map<Integer, Float> ColunasCusto = new HashMap();
    private  Multimap<Integer, Integer> lista_de_linhas = TreeMultimap.create();
    private  ArrayList<Coluna> lista_de_colunas_gulosas = new ArrayList();
    //public static ArrayList<Integer> linhasCobertas = new ArrayList();
    private  ArrayList<Integer> linhasCobertas = new ArrayList();
    private  int qtdeLinhas = 0;
    private  int qtdeColunas = 0;
    private  int linhasRestantes = 0;
    private  String nomearquivo;
    private  long startTime;
    private  float custoTotal = 0;
    /*
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
                    Coluna colunanova = new Coluna(Integer.parseInt(valores[i]));
                    linhasX.put(cont, colunanova);
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
                            Coluna colunaatual = linhasX.get(Integer.parseInt(cobertura[j]));
                            colunaatual.setCoberturaValor(colunaatual.getCoberturaValor()+1);
                            ArrayList adicionar = colunaatual.getCobertura();
                            adicionar.add(cont2);
                            //linhasX.put(Integer.parseInt(cobertura[j]), colunaatual);
                            cont3++;
                        }
                    }
                }
                cont2++;
            }
            for(Integer entry : linhasX.keySet()){
                int tamanho = linhasX.get(entry).getCoberturaValor() *(-1);
                lista_de_linhas.put(tamanho, entry);
            }
        }
        
    }
    */
    public  void leituraArquivoCons() throws IOException{
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            nomearquivo = file.getName();
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
            startTime = System.currentTimeMillis();
            String[] primeiralinha = reader.readLine().split(" ");
            for(String elemento : primeiralinha){
                if(!(elemento.equals(""))){
                    try{
                        qtdeLinhas = Integer.parseInt(elemento);
                    }
                    catch(Exception ex){
                        continue;
                    }
                }
            }
            System.out.println(qtdeLinhas);
            
            String[] segundalinha = reader.readLine().split(" ");
            for(String elemento : segundalinha){
                if(!(elemento.equals(""))){
                    try{
                        qtdeColunas = Integer.parseInt(elemento);
                    }
                    catch(Exception ex){
                        continue;
                    }
                }
            }
            System.out.println(qtdeColunas);
            String pular = reader.readLine();
            Integer cont = 1;
            while(cont<=qtdeColunas){
                String[] valores = reader.readLine().split(" ");
                int controle = 0;

                for(String valor : valores){
                    if(!(valor.equals(""))){
                            //System.out.println("Cont: "+controle);
                            //System.out.println("valor: "+valor);
                            if(controle==2){
                                Coluna atualizar2 = linhasX.get(cont);
                                ArrayList adicionar = atualizar2.getCobertura();
                                atualizar2.setCoberturaValor(atualizar2.getCoberturaValor()+1);
                                adicionar.add(Integer.parseInt(valor));
                            }
                            
                            else if(controle==1){
                                Coluna atualizar = linhasX.get(cont);
                                atualizar.setCusto(Float.parseFloat(valor));
                                ColunasCusto.put(cont, Float.parseFloat(valor));
                                controle = 2;
                            }
                            else if(controle==0){
                                Coluna colunanova = new Coluna(Integer.parseInt(valor));
                                linhasX.put(cont, colunanova);
                                controle = 1;
                            }
                        

                    }
                }
                cont++;
            }
            for(Integer entry : linhasX.keySet()){
                int tamanho = linhasX.get(entry).getCoberturaValor() *(-1);
                //System.out.println(entry);
                //System.out.println(linhasX.get(entry).getCobertura());
                lista_de_linhas.put(tamanho, entry);
            }
        }
        
    }
    
    public  int melhorEscolha(int maiorcobertura){
        Iterator acharmelhor = lista_de_linhas.asMap().get(maiorcobertura).iterator();
        float menorcusto = Float.MAX_VALUE;
        int melhorcoluna = 0;
        while(acharmelhor.hasNext()){
            int colunachecar = (Integer) acharmelhor.next();
            float custochecar = ColunasCusto.get(colunachecar);
            if (custochecar<menorcusto){
                menorcusto = custochecar;
                melhorcoluna = colunachecar;
            }
        }
        return melhorcoluna;
    }
    
    
    public  Solucao InitialGreedySolution(){
        while (linhasCobertas.size()<qtdeLinhas){

            Iterator it = lista_de_linhas.keySet().iterator();
            Integer maiorValor = (Integer) it.next();
            
            Integer coluna_que_mais_cobre = (Integer) lista_de_linhas.asMap().get(maiorValor).iterator().next();
            //Integer coluna_que_mais_cobre = melhorEscolha(maiorValor);
            custoTotal += ColunasCusto.get(coluna_que_mais_cobre);
            
            ArrayList colunasqueCobrem = linhasX.get(coluna_que_mais_cobre).getCobertura();
            lista_de_colunas_gulosas.add(linhasX.get(coluna_que_mais_cobre));
            int valorReal = maiorValor * (-1);
         
            for(int c = 0; c<=colunasqueCobrem.size()-1;c++){
                if(!(linhasCobertas.contains((Integer) colunasqueCobrem.get(c)))){
                    linhasCobertas.add((Integer) colunasqueCobrem.get(c));
                    removerValorColunas((Integer) colunasqueCobrem.get(c));
                }
                
                
            }
            atualizarTreeMap();
        }
        return new Solucao(this.lista_de_colunas_gulosas, this.custoTotal, this.linhasX, this.linhasCobertas, this.qtdeLinhas, this.qtdeColunas);
    }

    
    
    public  void removerValorColunas(Integer linhaRemover){
        Iterator iterador = linhasX.keySet().iterator();
        while(iterador.hasNext()){
            Integer chave = (Integer) iterador.next();
            Coluna colunaatual = linhasX.get(chave);
            ArrayList listacolunas = colunaatual.getCobertura();
            if(listacolunas.contains(linhaRemover)){
                colunaatual.setCoberturaValor(colunaatual.getCoberturaValor()-1);
            }
            //listacolunas.remove(linhaRemover);
            //linhasX.put(chave, listacolunas);
            
        }
    }
    
    public  void atualizarTreeMap(){
        List<Integer> listaprovisoria = new ArrayList();
        List<Integer> listaprovisoriaexcluir = new ArrayList();
        Iterator iterador = lista_de_linhas.keySet().iterator();
        while(iterador.hasNext()){
            Integer valorAtualizar = (Integer) iterador.next();
            int valorAtualizarReal = Math.abs(valorAtualizar);
            Iterator iterador2 = lista_de_linhas.get(valorAtualizar).iterator();
            while(iterador2.hasNext()){
                Integer colunaAtualizar = (Integer) iterador2.next();
                int novoTamanho = linhasX.get(colunaAtualizar).getCoberturaValor();
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

    public Map<Integer, Coluna> getLinhasX() {
        return linhasX;
    }

    public Map<Integer, Float> getColunasCusto() {
        return ColunasCusto;
    }

    public Multimap<Integer, Integer> getLista_de_linhas() {
        return lista_de_linhas;
    }

    public ArrayList<Coluna> getLista_de_colunas_gulosas() {
        return lista_de_colunas_gulosas;
    }

    public ArrayList<Integer> getLinhasCobertas() {
        return linhasCobertas;
    }

    public int getQtdeLinhas() {
        return qtdeLinhas;
    }

    public int getQtdeColunas() {
        return qtdeColunas;
    }

    public int getLinhasRestantes() {
        return linhasRestantes;
    }

    public String getNomearquivo() {
        return nomearquivo;
    }

    public long getStartTime() {
        return startTime;
    }

    public float getCustoTotal() {
        return custoTotal;
    }
    
    
    
   
    
    
    
    
}
