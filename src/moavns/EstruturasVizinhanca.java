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
public class EstruturasVizinhanca {

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

    public EstruturasVizinhanca(Solucao solucao) {
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
    
    public void printcolunas(Solucao sol){
        for(Coluna col : sol.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        System.out.println();
    }
    
    public Solucao Two_flip_best(Solucao inicio){
        novassolucoes.clear();
        int a = 0;
        while(a<=inicio.getColunas().size()-2){
            int b = a + 1;
            while(b<=inicio.getColunas().size()-1){
                Solucao solucao = twoflipBuscaLocal(inicio, inicio.getColunas().get(a), inicio.getColunas().get(b));
                if(solucao!=inicio){
                    novassolucoes.put(solucao.getCustototal(), solucao);
                }
                b++;
            }
            a++;
        }
        return eliminarRedundancia(novassolucoes.get(novassolucoes.keySet().iterator().next()).iterator().next());
        
    }
    
    public Solucao twoflipBuscaLocal(Solucao solucao, Coluna coluna1, Coluna coluna2){
        float custo1 = coluna1.getCusto() + coluna2.getCusto();
        Coluna[] melhorescolunas = new Coluna[2];
        Solucao melhorsolucao = new Solucao(solucao);
        for(Integer entrada1 : linhasX.keySet()){
            for(Integer entrada2 : linhasX.keySet()){
                if(entrada1!=entrada2){
                    Solucao testarsolucao = new Solucao(solucao);
                    removerLinhas(testarsolucao.getLinhasX().get(entrada1), testarsolucao);
                    removerLinhas(testarsolucao.getLinhasX().get(entrada2), testarsolucao);
                    if((solucao.getColunas().contains(testarsolucao.getLinhasX().get(entrada1))) || (solucao.getColunas().contains(testarsolucao.getLinhasX().get(entrada2)))){
                        continue;
                    }
                    else if(cobreTodasTwoFlip(testarsolucao.getLinhasX().get(entrada1), testarsolucao.getLinhasX().get(entrada2), testarsolucao)){
                        float custoantigo = melhorsolucao.getCustototal();
                        float custonovo = testarsolucao.getCustototal() -
                                coluna1.getCusto() - coluna2.getCusto() + testarsolucao.getLinhasX().get(entrada1).getCusto() + testarsolucao.getLinhasX().get(entrada2).getCusto();
                        if(custonovo < custoantigo){
                            testarsolucao.getColunas().remove(coluna1);
                            testarsolucao.getColunas().remove(coluna2);
                            testarsolucao.getColunas().add(testarsolucao.getLinhasX().get(entrada1));
                            testarsolucao.getColunas().add(testarsolucao.getLinhasX().get(entrada2));
                            melhorsolucao = testarsolucao;
                            melhorsolucao.setCustototal(custonovo);
                        }
                    }
                }
            }
        }
        return melhorsolucao;
    }
    
    
    public Solucao One_flip_best(Solucao inicio, int x){
        solucoes.put(inicio.getCustototal(), inicio);
        novassolucoes.put(inicio.getCustototal(), inicio);
        for(Coluna col : inicio.getColunas()){
            Solucao testarsolucao = new Solucao(inicio);
            removerLinhas(col, testarsolucao);
            cobrirLinhas(col, testarsolucao);
            Coluna novacoluna;
            novacoluna = searchBetterSolution(col, testarsolucao);
            if (novacoluna.getCusto()<col.getCusto()){

                float custox = testarsolucao.getCustototal() - col.getCusto() + novacoluna.getCusto();

                testarsolucao.getColunas().remove(col);
                testarsolucao.getColunas().add(novacoluna);
                Solucao novasolucao = new Solucao(testarsolucao);
                novassolucoes.put(custox, novasolucao);
                solucoes.put(custox, novasolucao);
            }
        }
        Float chave = novassolucoes.keySet().iterator().next();
        Solucao solucaox = novassolucoes.get(chave).iterator().next();
        solucaox.setCustototal(chave);
        return solucaox;
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
    
    public  boolean cobreTodasTwoFlip(Coluna col1, Coluna col2, Solucao sol){
        ArrayList<Integer> cobertura1 = col1.getCobertura();
        ArrayList<Integer> cobertura2 = col2.getCobertura();
        for(Integer entradateste1 : cobertura1){
            if(!(sol.getLinhasCobertas().contains(entradateste1))){
                sol.getLinhasCobertas().add(entradateste1);
            }
        }
        for(Integer entradateste2 : cobertura2){
            if(!(sol.getLinhasCobertas().contains(entradateste2))){
                sol.getLinhasCobertas().add(entradateste2);
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
    
    
    public Solucao eliminarRedundancia(Solucao solucao){
        Multimap<Float, Integer> colunas = TreeMultimap.create();
        Map<Integer, Coluna> colunasx = new HashMap();
        for(Coluna coluna : solucao.getColunas()){
            Float custo = coluna.getCusto();          
            colunas.put((custo*(-1)), coluna.getNome());
            colunasx.put(coluna.getNome(), coluna);
        }
        Solucao testarsolucao = new Solucao(solucao);
        for(Float chave : colunas.keySet()){
            Iterator iterador = colunas.get(chave).iterator();
            while(iterador.hasNext()){
                Coluna maiorcoluna = colunasx.get(iterador.next());
                testarsolucao.getLinhasCobertas().clear();
                cobrirLinhas(maiorcoluna, testarsolucao);
                if(testarsolucao.getLinhasCobertas().size()==testarsolucao.getQtdeLinhas()){
                    System.out.println("Caiu redundancia!");
                    testarsolucao.getColunas().remove(maiorcoluna);
                    testarsolucao.setCustototal(custoColunas(testarsolucao));
                }
            }
        }
        return testarsolucao;
    }
    
    public int custoColunas(Solucao sol){
        int custox = 0;
        for(Coluna custo : sol.getColunas()){
            custox += custo.getCusto();
        }
        return custox;
    }
}
