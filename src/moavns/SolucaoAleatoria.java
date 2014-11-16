/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

import java.util.ArrayList;

/**
 *
 * @author Guilherme
 */
public class SolucaoAleatoria {
    public static Solucao Vizinhos(Solucao solucao, int x){
        if(x==0){
            Solucao novasolucao = VizinhoOneFlip(solucao);
            if(novasolucao!=solucao){
                return novasolucao;
            }
            else{
                x+=1;
            }
        }
        return solucao;
    }
    
    public static Solucao VizinhoTwoFlip(Solucao inicio){
        int a = 0;
        while(a<=inicio.getColunas().size()-2){
            int b = a + 1;
            while(b<=inicio.getColunas().size()-1){
                Solucao solucao = twoflipBuscaLocal(inicio, inicio.getColunas().get(a), inicio.getColunas().get(b));
                if(solucao!=inicio){
                    return solucao;
                }
                b++;
            }
            a++;
        }
        return inicio;
        
    }
    
    public static Solucao twoflipBuscaLocal(Solucao solucao, Coluna coluna1, Coluna coluna2){
        float custo1 = coluna1.getCusto() + coluna2.getCusto();
        Coluna[] melhorescolunas = new Coluna[2];
        Solucao melhorsolucao = new Solucao(solucao);
        for(Integer entrada1 : solucao.getLinhasX().keySet()){
            for(Integer entrada2 : solucao.getLinhasX().keySet()){
                if(entrada1!=entrada2){
                    Solucao testarsolucao = new Solucao(solucao);
                    removerLinhas(testarsolucao.getLinhasX().get(entrada1), testarsolucao);
                    removerLinhas(testarsolucao.getLinhasX().get(entrada2), testarsolucao);
                    if((solucao.getColunas().contains(testarsolucao.getLinhasX().get(entrada1))) || (solucao.getColunas().contains(testarsolucao.getLinhasX().get(entrada2)))){
                        continue;
                    }
                    else if(cobreTodasTwoFlip(testarsolucao.getLinhasX().get(entrada1), testarsolucao.getLinhasX().get(entrada2), testarsolucao)){
                        float custonovo = testarsolucao.getCustototal() -
                                coluna1.getCusto() - coluna2.getCusto() + testarsolucao.getLinhasX().get(entrada1).getCusto() + testarsolucao.getLinhasX().get(entrada2).getCusto();

                        testarsolucao.getColunas().remove(coluna1);
                        testarsolucao.getColunas().remove(coluna2);
                        testarsolucao.getColunas().add(testarsolucao.getLinhasX().get(entrada1));
                        testarsolucao.getColunas().add(testarsolucao.getLinhasX().get(entrada2));
                        testarsolucao.setCustototal(custonovo);
                        return testarsolucao;
                        
                    }
                }
            }
        }
        return melhorsolucao;
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
