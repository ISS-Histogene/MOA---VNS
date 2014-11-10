/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

import java.util.Comparator;

/**
 *
 * @author Gustavo
 */
public class Comparador implements Comparator<Solucao> {
    @Override
    public int compare(Solucao solucao, Solucao outraSolucao) {
        return solucao.compareTo(outraSolucao);
    }
}
