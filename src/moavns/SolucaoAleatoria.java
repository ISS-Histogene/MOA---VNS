/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author Guilherme
 */
public class SolucaoAleatoria {
    public static Solucao Vizinhos(Solucao solucao, int x){
        
        if(x==1){
            Solucao novasolucao = VizinhoRedundancia(solucao);
            if(!novasolucao.equals(solucao)){
                return novasolucao;
            }
        }
        
        else if(x==0){

            Solucao novasolucao = VizinhoOneFlip(solucao);
            if(!novasolucao.equals(solucao)){
                return novasolucao;
            }
        }
        else if(x==2){
            Solucao novasolucao1 = VizinhoTwoFlip(solucao);
            if(!(novasolucao1.equals(solucao))){
                //System.out.println("Caiu");
                return novasolucao1;
            }
        }
        
        return solucao;
    }
    
    public static Solucao VizinhoRedundancia(Solucao inicio){
        Comparator comparador = new Comparador();
        Ordering<Float> ordenacao = Ordering.natural();
        Comparator ordenacao1 = ordenacao;
        Ordering<Solucao> ordenacao2 = Ordering.from(comparador);
        Multimap<Float, Solucao> novassolucoes = TreeMultimap.create(ordenacao1,  ordenacao2);
        for(Integer entrada : inicio.getLinhasX().keySet()){
            Solucao novasolucao = new Solucao(inicio);
            Coluna testar = inicio.getLinhasX().get(entrada);
            novasolucao.getColunas().add(testar);
            novasolucao.setCustototal(novasolucao.getCustototal()+testar.getCusto());
            Solucao solucaox = eliminarRedundancia(novasolucao);
            String stringsolucao = MOAVNS.transformaSolucao(solucaox);
            if(!MOAVNS.solucoes.contains(stringsolucao)){
                return solucaox;
            }
        }
        return inicio;
    }
    
    
    public static Solucao eliminarRedundancia(Solucao solucao){
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
                testar.getLinhasCobertas().clear();
                cobrirLinhas(maiorcoluna, testar);
                if(testar.getLinhasCobertas().size()==testar.getQtdeLinhas()){
                    testar.getColunas().remove(maiorcoluna);
                    testar.setCustototal(testar.getCustototal()-maiorcoluna.getCusto());
                    testarsolucao = testar;
                }
            }
        }
        return testarsolucao;
    }
    
    public static Solucao VizinhoTwoFlip(Solucao inicio){
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
                                String newstring = MOAVNS.transformaSolucao(newsolution);
                                if(!MOAVNS.solucoes.contains(newstring)){
                                    return newsolution;
                                }
                            }

                            
                        }
                    }
                }
                
            }
        }
        return inicio;
    }
    
    
    public static boolean cobreTodasTwoFlip(Coluna col1, Coluna col2, Solucao sol){
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
    
    public static Solucao VizinhoOneFlip(Solucao inicio){
        for(Coluna col : inicio.getColunas()){
            for(Integer colx : inicio.getLinhasX().keySet()){           
                if(col.getNome()!=inicio.getLinhasX().get(colx).getNome()){
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
                        testarsolucao.setCustototal(testarsolucao.getCustototal()-col.getCusto()+inicio.getLinhasX().get(colx).getCusto());
                        String newstring = MOAVNS.transformaSolucao(testarsolucao);
                        //System.out.println(newstring);
                        if(!(MOAVNS.solucoes.contains(newstring))){
                            return testarsolucao;
                        }
                        else{
                            //System.out.println("repetido");
                        }
                    }
                }
                
            }
        }
        return inicio;
    }
    public static void removerLinhas(Coluna col, Solucao solucao){
        ArrayList<Integer> cobertas_pela_coluna = col.getCobertura();
        for(Integer linha : cobertas_pela_coluna){
            if(solucao.getLinhasCobertas().contains(linha)){
                solucao.getLinhasCobertas().remove(linha);
            }
        }
    }
    
    public static void cobrirLinhas(Coluna naoincluir, Solucao solucao){
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
    
    public static boolean cobreTodas(Coluna col, Solucao sol){
        int x = 0;
        ArrayList<Integer> cobertura = col.getCobertura();
        for(Integer entradateste : cobertura){
            if(!(sol.getLinhasCobertas().contains(entradateste))){
                sol.getLinhasCobertas().add(entradateste);
            }
        }
        //System.out.println("Cobertura de linhas: " + sol.getLinhasCobertas().size());
        if(sol.getLinhasCobertas().size()==sol.getQtdeLinhas()){
            return true;
        }
        else{
            return false;
        }
        
    }
}
