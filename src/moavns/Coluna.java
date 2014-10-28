/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

import java.util.ArrayList;

/**
 *
 * @author Gustavo
 */
public class Coluna {
    private int coberturaValor;
    private ArrayList cobertura;
    private float custo;
    private int nome;

    public int getNome() {
        return nome;
    }

    public void setNome(int nome) {
        this.nome = nome;
    }

    public int getCoberturaValor() {
        return coberturaValor;
    }

    public void setCoberturaValor(int coberturaValor) {
        this.coberturaValor = coberturaValor;
    }

    public ArrayList getCobertura() {
        return cobertura;
    }

    public void setCobertura(ArrayList cobertura) {
        this.cobertura = cobertura;
    }

    public Coluna(int nome) {
        this.coberturaValor = 0;
        this.cobertura = new ArrayList();
        this.custo = 0;
        this.nome = nome;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

   
    
}
