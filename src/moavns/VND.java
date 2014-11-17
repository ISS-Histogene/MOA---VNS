/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moavns;

/**
 *
 * @author Guilherme
 */
public class VND {
    
    public static void printcolunas(Solucao sol){
        for(Coluna col : sol.getColunas()){
            System.out.printf(col.getNome() + " ");
        }
        System.out.println();
    }
    
    public static Solucao VND(Solucao solucaoxx, int k){
        int x = 0;
        Solucao solucao = new Solucao(solucaoxx);
        while(true){
            if(k==0){
                EstruturasVizinhanca redundancia = new EstruturasVizinhanca(solucao);
                Solucao solucaox1 = redundancia.RedundanciaBest(solucao);
                if(solucaox1.getCustototal()<solucaoxx.getQtdeColunas()){
                    return solucaox1;
                }
                k+=1;
            }
            if(k==1){
                EstruturasVizinhanca oneflip = new EstruturasVizinhanca(solucao);
                Solucao solucao2 = oneflip.One_flip_best(solucao, 0);
                Solucao solucaox = oneflip.eliminarRedundancia(solucao2);
                if(solucaox.getCustototal()<solucaoxx.getQtdeColunas()){
                    return solucaox;
                }
                k+=1;
            }

            if(k==2){
                break;
            }
                
        }
        //System.out.println("sem melhora");
        return solucao;
    }
}
