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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gustavo
 */
public class Solucao implements Comparable<Solucao> {
    private ArrayList<Coluna> colunas;
    private float custototal;
    Map<Integer, Coluna> linhasX = new HashMap();
    private ArrayList<Integer> linhasCobertas;
    private int qtdeLinhas;
    private int qtdeColunas;

    public Map<Integer, Coluna> getLinhasX() {
        return linhasX;
    }

    public void setLinhasX(Map<Integer, Coluna> linhasX) {
        this.linhasX = linhasX;
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

    public Solucao() {
    }
    
    public Solucao(Solucao copiar){
        this.colunas = new ArrayList();
        for(Coluna col : copiar.getColunas()){
            this.colunas.add(col);
        }
        this.custototal = custototal;
        for(Integer numero : copiar.getLinhasX().keySet()){
            this.linhasX.put(numero, copiar.getLinhasX().get(numero));
        }
        this.linhasCobertas = new ArrayList();
        for(Integer linha : copiar.getLinhasCobertas()){
            this.linhasCobertas.add(linha);
        }
        this.qtdeLinhas = copiar.qtdeLinhas;
        this.qtdeColunas = copiar.qtdeColunas;
        this.custototal = new Float(copiar.getCustototal());
    }

    
    
    public Solucao(ArrayList<Coluna> colunas, float custototal, Map<Integer, Coluna> linhasX, ArrayList<Integer> linhasCobertas, int qtdeLinhas, int qtdeColunas) {
        this.colunas = new ArrayList();
        for(Coluna col : colunas){
            this.colunas.add(col);
        }
        this.custototal = custototal;
        for(Integer numero : linhasX.keySet()){
            this.linhasX.put(numero, linhasX.get(numero));
        }
        this.linhasCobertas = new ArrayList();
        for(Integer linha : linhasCobertas){
            this.linhasCobertas.add(linha);
        }
        this.qtdeLinhas = qtdeLinhas;
        this.qtdeColunas = qtdeColunas;
    }

    public ArrayList<Coluna> getColunas() {
        return colunas;
    }

    public void setColunas(ArrayList<Coluna> colunas) {
        this.colunas = colunas;
    }

    public float getCustototal() {
        return custototal;
    }

    public void setCustototal(float custototal) {
        this.custototal = custototal;
    }

    @Override
    public int compareTo(Solucao outraSolucao) {
        if (this.custototal < outraSolucao.getCustototal()) {
            return -1;
        }
        if (this.custototal > outraSolucao.getCustototal()) {
            return 1;
        }
        return 0;
    }
    
    
    
}
