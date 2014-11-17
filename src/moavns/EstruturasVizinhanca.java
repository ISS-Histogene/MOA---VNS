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
import java.util.Objects;
import moavns.Solucao;
import java.util.Random;
import static moavns.SolucaoAleatoria.cobreTodasTwoFlip;
import static moavns.SolucaoAleatoria.cobrirLinhas;
import static moavns.SolucaoAleatoria.removerLinhas;


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
    private  static Ordering<Float> ordenacao = Ordering.natural();
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
    
    public Solucao RedundanciaBest(Solucao inicio){
        this.solucoes.put(inicio.getCustototal(), inicio);
        for(Integer entrada : linhasX.keySet()){
            Solucao novasolucao = new Solucao(inicio);
            Coluna testar = linhasX.get(entrada);
            novasolucao.getColunas().add(testar);
            novasolucao.setCustototal(novasolucao.getCustototal()+testar.getCusto());
            Solucao solucaox = eliminarRedundancia(novasolucao);
            //System.out.println(solucaox.getCustototal());
            String newsolution = MOAVNS.transformaSolucao(solucaox);
            if(!MOAVNS.solucoes.contains(newsolution)){
                this.solucoes.put(solucaox.getCustototal(), solucaox);
            }
        }
        return this.solucoes.get(this.solucoes.keySet().iterator().next()).iterator().next();
    }
    
    public Solucao TwoFlip(Solucao inicio){
        this.solucoes.put(inicio.getCustototal(), inicio);
        for(int a = 0; a<=inicio.getColunas().size()-2; a++){
            for(int b = a+1; b<=inicio.getColunas().size()-1;b++){
                for(Integer entrada1 : inicio.getLinhasX().keySet()){
                    for(Integer entrada2 : inicio.getLinhasX().keySet()){
                        if(!Objects.equals(entrada1, entrada2)){
                            Solucao newsolution = new Solucao(inicio);
                            removerLinhas(inicio.getColunas().get(a), newsolution);
                            removerLinhas(inicio.getColunas().get(b), newsolution);
                            newsolution.getColunas().remove(inicio.getColunas().get(a));
                            newsolution.getColunas().remove(inicio.getColunas().get(b));
                            cobrirLinhas(inicio.getColunas().get(a), newsolution);

                            if(cobreTodasTwoFlip( inicio.getLinhasX().get(entrada1), inicio.getLinhasX().get(entrada2), newsolution)){
                                newsolution.getColunas().remove(inicio.getColunas().get(a));
                                newsolution.getColunas().remove(inicio.getColunas().get(b));
                                newsolution.getColunas().add(inicio.getLinhasX().get(entrada1));
                                newsolution.getColunas().add(inicio.getLinhasX().get(entrada2));
                                newsolution.setCustototal(newsolution.getCustototal() - 
                                                                                        inicio.getColunas().get(a).getCusto() - 
                                                                                        inicio.getColunas().get(b).getCusto() + 
                                                                                        inicio.getLinhasX().get(entrada1).getCusto() + 
                                                                                        inicio.getLinhasX().get(entrada2).getCusto() );
                                Solucao verdadeira = this.eliminarRedundancia(newsolution);
                                String newstring = MOAVNS.transformaSolucao(verdadeira);
                                if(!MOAVNS.solucoes.contains(newstring)){
                                    this.solucoes.put(verdadeira.getCustototal(), verdadeira);
                                }
                            }

                            
                        }
                    }
                }
                
            }
        }
        return this.solucoes.get(this.solucoes.keySet().iterator().next()).iterator().next();
    }
    
    public Solucao One_flip_best(Solucao inicio, int x){
        this.solucoes.put(inicio.getCustototal(), inicio);
        for(Coluna col : inicio.getColunas()){
            for(Integer colx : inicio.getLinhasX().keySet()){           
                if(col.getNome()!=inicio.getLinhasX().get(colx).getNome()){
                    if(inicio.getLinhasX().get(colx).getCusto()<col.getCusto()){
                        Solucao testarsolucao = new Solucao(inicio);
                        removerLinhas(col, testarsolucao);
                        cobrirLinhas(col, testarsolucao);
                        ArrayList colunas = testarsolucao.getColunas();
                        colunas.remove(col);
                        testarsolucao.setColunas(colunas);
                        if(cobreTodas(inicio.getLinhasX().get(colx), testarsolucao)){
                            ArrayList novascolunas = testarsolucao.getColunas();
                            novascolunas.add(inicio.getLinhasX().get(colx));
                            testarsolucao.setColunas(novascolunas);
                            Solucao verdadeira = this.eliminarRedundancia(testarsolucao);
                            String newstring = MOAVNS.transformaSolucao(verdadeira);
                            if(!MOAVNS.solucoes.contains(newstring)){
                                verdadeira.setCustototal(verdadeira.getCustototal()-col.getCusto()+inicio.getLinhasX().get(colx).getCusto());
                                this.solucoes.put(verdadeira.getCustototal(), verdadeira);
                            }
                        }
                    }
                }
                
            }
        }
        return this.solucoes.get(this.solucoes.keySet().iterator().next()).iterator().next();
    }


    public  boolean cobreTodas(Coluna col, Solucao sol){
        int x = 0;
        ArrayList<Integer> cobertura = col.getCobertura();
        for(Integer entradateste : cobertura){
            Integer teste = entradateste;
            if(!(sol.getLinhasCobertas().contains(entradateste))){
                sol.getLinhasCobertas().add(entradateste);
            }
        }
        //System.out.println("Cobertura de linhas: " + sol.getLinhasCobertas().size());
        return sol.getLinhasCobertas().size()==sol.getQtdeLinhas();
        
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
        for(Coluna coluna : solucao.getColunas()){
            Float custo = coluna.getCusto();          
            colunas.put((custo*(-1)), coluna.getNome());
        }
        Solucao testarsolucao = new Solucao(solucao);
        for(Float chave : colunas.keySet()){
            Iterator iterador = colunas.get(chave).iterator();
            while(iterador.hasNext()){
                Solucao testar = new Solucao(testarsolucao);
                Coluna maiorcoluna = testarsolucao.getLinhasX().get(iterador.next());
                //System.out.println(maiorcoluna.getNome());
                testar.getLinhasCobertas().clear();
                cobrirLinhas(maiorcoluna, testar);
                if(testar.getLinhasCobertas().size()==testar.getQtdeLinhas()){
                    String newstring = MOAVNS.transformaSolucao(testar);
                    if(!MOAVNS.solucoes.contains(newstring)){
                        testar.getColunas().remove(maiorcoluna);
                        testar.setCustototal(testar.getCustototal()-maiorcoluna.getCusto());
                        testarsolucao = testar;
                    }
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
    
    public Solucao GerarVizinhoOneFlip(Solucao inicio){
        Random gerador = new Random();
        while(true){
            int indice = gerador.nextInt(inicio.getColunas().size()-1);
            for(Integer entrada : inicio.getLinhasX().keySet()){
                Coluna colx = inicio.getColunas().get(indice);
                //System.out.println(colx.getNome());
                Solucao testarsolucao = new Solucao(inicio);
                removerLinhas(colx, testarsolucao);
                cobrirLinhas(colx, testarsolucao);
                //System.out.println(testarsolucao.getLinhasCobertas().size());
                //System.out.println("XXX");
                testarsolucao.getColunas().remove(colx);
                Coluna testarcoluna = testarsolucao.getLinhasX().get(entrada);
                if(colx.getNome() != testarcoluna.getNome()){
                    if(cobreTodas(testarcoluna, testarsolucao)){
                        testarsolucao.getColunas().add(testarcoluna);
                        return testarsolucao;
                    }
                }
            }
        }
    }
    
}
