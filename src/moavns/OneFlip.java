/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import moavns.Solucao;


/**
 *
 * @author Gustavo
 */
public class OneFlip {

    public long getStartTime() {
        return startTime;
    }
    private  long startTime;
    private  Map<Integer, Coluna> linhasX;
    private  ArrayList<Coluna> lista_de_colunas_gulosas;
    private  ArrayList<Integer> linhasCobertas;
    private  Multimap<Float, Solucao> solucoes;
    private  static Comparator comparador = new Comparador();
    private  static Ordering<Integer> ordenacao = Ordering.natural();
    private  static Comparator ordenacao1 = ordenacao;
    private  static Ordering<Solucao> ordenacao2 = Ordering.from(comparador);
    private  static Multimap<Float, Solucao> novassolucoes = TreeMultimap.create(ordenacao1,  ordenacao2);
    private  int qtdeLinhas;
    private  int qtdeColunas;
    private  float custoTotal;

    public OneFlip(Solucao solucao) {
        this.startTime = System.currentTimeMillis();
        this.linhasX = solucao.getLinhasX();
        this.lista_de_colunas_gulosas = solucao.getColunas();
        this.linhasCobertas = solucao.getLinhasCobertas();
        this.qtdeLinhas = solucao.getQtdeLinhas();
        this.qtdeColunas = solucao.getQtdeColunas();
        this.custoTotal = solucao.getCustototal();
        Comparator comparador = new Comparador();
        Ordering<Integer> ordenacao = Ordering.natural();
        Comparator ordenacao1 = ordenacao;
        Ordering<Solucao> ordenacao2 = Ordering.from(comparador);
        this.solucoes = TreeMultimap.create(ordenacao1,  ordenacao2);
    }

    public Map<Integer, Coluna> getLinhasX() {
        return linhasX;
    }

    public void setLinhasX(Map<Integer, Coluna> linhasX) {
        this.linhasX = linhasX;
    }

    public ArrayList<Coluna> getLista_de_colunas_gulosas() {
        return lista_de_colunas_gulosas;
    }

    public void setLista_de_colunas_gulosas(ArrayList<Coluna> lista_de_colunas_gulosas) {
        this.lista_de_colunas_gulosas = lista_de_colunas_gulosas;
    }

    public ArrayList<Integer> getLinhasCobertas() {
        return linhasCobertas;
    }

    public void setLinhasCobertas(ArrayList<Integer> linhasCobertas) {
        this.linhasCobertas = linhasCobertas;
    }

    public int getQtdeLinhas() {
        return qtdeLinhas;
    }

    public void setQtdeLinhas(int qtdeLinhas) {
        this.qtdeLinhas = qtdeLinhas;
    }

    public int getQtdeColunas() {
        return qtdeColunas;
    }

    public void setQtdeColunas(int qtdeColunas) {
        this.qtdeColunas = qtdeColunas;
    }

    public float getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(int custoTotal) {
        this.custoTotal = custoTotal;
    }
    
    
    
    
    public void removerLinhas(Coluna col, Solucao solucao){
        ArrayList<Integer> cobertas_pela_coluna = col.getCobertura();
        for(Integer linha : cobertas_pela_coluna){
            if(solucao.getLinhasCobertas().contains(linha)){
                solucao.getLinhasCobertas().remove(linha);
            }
        }
    }
    
    public void cobrirLinhas(Coluna naoincluir, Solucao solucao){
        for(Coluna col : solucao.getColunas()){
            if(col.getNome()!= naoincluir.getNome()){
                ArrayList<Integer> valores_cobertos_pela_coluna = col.getCobertura();
                for(Integer linha : valores_cobertos_pela_coluna){
                    if(!(solucao.getLinhasCobertas().contains(linha))){
                        solucao.getLinhasCobertas().add(linha);
                    }
                }
            }
        }
    }
    
    public Solucao One_flip_first(Solucao inicio, int x){
        solucoes.put(inicio.getCustototal(), inicio);
        novassolucoes.put(inicio.getCustototal(), inicio);
        int limite = 1000000;
        while(!(solucoes.isEmpty())){
            Float chave = solucoes.keySet().iterator().next();
            //System.out.println("Chave: "+chave);
            Solucao atual = solucoes.get(chave).iterator().next();
            //printSolucao(atual);
            for(Coluna col : atual.getColunas()){
                Solucao testarsolucao = new Solucao(atual);
                //System.out.println("Atual solucao: "+atual.getLinhasCobertas().size());
                //System.out.println("Testar solucao: "+testarsolucao.getLinhasCobertas().size());
                removerLinhas(col, testarsolucao);
                //System.out.println("Atual solucao 2: "+atual.getLinhasCobertas().size());
                //System.out.println("Testar solucao 2: "+testarsolucao.getLinhasCobertas().size());
                cobrirLinhas(col, testarsolucao);
                //System.out.println("Atual solucao 3: "+atual.getLinhasCobertas().size());
                //System.out.println("Testar solucao 3: "+testarsolucao.getLinhasCobertas().size());
                Coluna novacoluna;
                if(x==0){
                    novacoluna = searchBetterSolution(col, testarsolucao);
                }
                else{
                    novacoluna = searchBetterSolutionVarias(col, testarsolucao);
                }

                if (novacoluna.getCusto()<col.getCusto()){
                    limite--;
                    //System.out.println("Atual: "+ testarsolucao.getCustototal());
                    //System.out.println("Tirar: "+ col.getCusto());
                    //System.out.println("Colocar: "+novacoluna.getCusto());
                    float custox = testarsolucao.getCustototal() - col.getCusto() + novacoluna.getCusto();
                    //System.out.println("Custo x: "+ custox);
                    //System.out.println("\n\n");
                    testarsolucao.getColunas().remove(col);
                    testarsolucao.getColunas().add(novacoluna);
                    Solucao novasolucao = new Solucao(testarsolucao);
                    novassolucoes.put(custox, novasolucao);
                    solucoes.put(custox, novasolucao);
                }
                
            }
            if(x==0){
                break;
            }
            if(limite<0){
                break;
            }
        }
        Float chave = novassolucoes.keySet().iterator().next();
        Solucao solucaox = novassolucoes.get(chave).iterator().next();
        solucaox.setCustototal(chave);
        return solucaox;
    }
    
    
    public void printSolucao(Solucao sol){
        for(Coluna coluna : sol.getColunas()){
            System.out.printf(coluna.getNome()+ " ");
        }
        System.out.println();
        System.out.println(sol.getLinhasCobertas());
    }
    
    public  Coluna searchBetterSolution(Coluna colunaanterior, Solucao solucao){
        Float melhor = colunaanterior.getCusto();
        Coluna melhorColuna = colunaanterior;
        for(Integer numcol : solucao.getLinhasX().keySet()){
            if(numcol!=colunaanterior.getNome()){
                Coluna col = solucao.getLinhasX().get(numcol);
                if(col.getCusto() < melhor){
                    if(cobreTodas(col, solucao)){
                        //System.out.println("Caiu x!");
                        melhorColuna = col;
                        melhor = col.getCusto();
                    }
                }
            }
        }
        return melhorColuna;
    }
    
    public  Coluna searchBetterSolutionVarias(Coluna colunaanterior, Solucao solucao){
        Float melhor = colunaanterior.getCusto();
        Coluna melhorColuna = colunaanterior;
        for(Integer numcol : solucao.getLinhasX().keySet()){
            if(numcol!=colunaanterior.getNome()){
                Coluna col = solucao.getLinhasX().get(numcol);
                if(col.getCusto() < melhor){
                    if(cobreTodas(col, solucao)){
                        //System.out.println("Caiu x!");
                        return col;
                    }
                }
            }
        }
        return colunaanterior;
    }
    
    public  boolean cobreTodas(Coluna col, Solucao sol){
        int x = 0;
        ArrayList<Integer> cobertura = col.getCobertura();
        for(Integer entradateste : cobertura){
            if(!(sol.getLinhasCobertas().contains(entradateste))){
                sol.getLinhasCobertas().add(entradateste);
            }
        }
        if(sol.getLinhasCobertas().size()==sol.getQtdeLinhas()){
            //System.out.println("Cobertura de linhas: " + linhasCobertas.size());
            return true;
        }
        else{
            return false;
        }
        
    }
}
