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
    
    public static Solucao VND(Solucao solucaoxx){
        int k = 0;
        int x = 0;
        Solucao solucao = new Solucao(solucaoxx);
        while(true){
            if(k==0){
                EstruturasVizinhanca oneflip = new EstruturasVizinhanca(solucao);
                Solucao solucao2 = oneflip.One_flip_best(solucao, 0);
                Solucao solucaox = oneflip.eliminarRedundancia(solucao2);
                if(solucaox.getCustototal()<solucao.getCustototal()){
                    solucao = solucaox;
                }
                else{
                    //System.out.println("caiu");
                    EstruturasVizinhanca redundancia = new EstruturasVizinhanca(solucao);
                    Solucao solucaox1 = redundancia.RedundanciaBest(solucao);
                    if(solucaox1.getCustototal()<solucao.getCustototal()){
                        solucao = solucaox1;
                    }
                    else{
                        break;
                    }
                }
            }
            /*
            if(k==1){
                System.out.println("caiu");
                EstruturasVizinhanca redundancia = new EstruturasVizinhanca(solucao);
                Solucao solucaox = redundancia.RedundanciaBest(solucao);
                if(solucaox.getCustototal()<solucao.getCustototal()){
                    solucao = solucaox;
                }
                else{
                    k+=1;
                }
            }
            
            if(k==2){
                //System.out.println("twoflip");
                EstruturasVizinhanca twoflip = new EstruturasVizinhanca(solucao);
                Solucao solucao3 = twoflip.Two_flip_best(solucao);
                Solucao solucaox = twoflip.eliminarRedundancia(solucao3);
                if(solucaox.getCustototal()<solucao.getCustototal()){
                    solucao = solucaox;
                    k=0;
                }
                else{
                    k+=1;
                }
            }
                   */
            else{
                break;         
            }
        }
        return solucao;
    }
}
