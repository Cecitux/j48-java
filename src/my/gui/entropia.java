/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import java.lang.Math;
import java.util.Iterator;
/**
 *
 * @author Administrator
 */
public class entropia {
    static int cantidad_total;
    static int cantidad_de_clase[];
    static double resulatdo_entrpia;

   /**
     *
     * @param cantt: es la cantidad de filas de la tabla
     * @param cantc: es la cantidad de registros de ese tipo de dato
     */
    public entropia(int cantt, int cantc[]){

        entropia.cantidad_de_clase=new int[cantt];
        entropia.cantidad_de_clase=cantc;
        entropia.cantidad_total=cantt;
        entropia.resulatdo_entrpia=0.0;
        for (int i = 0; i<entropia.cantidad_total;i++) {
            System.out.println(entropia.cantidad_de_clase[i]);

        }
    }

    static double calcular_probabilidad(){
        double resultado=2.5;
        //resultado=(double) entropia.cantidad_de_clase/entropia.cantidad_total;
        return resultado;
    }
    /**
     * Calcula el logaritmo en base dos del numeros
     * @param numero
     * @return logaritmo en base 2
     */
    private double logaritmo_base2(double numero){
       return (Math.log10(numero)/Math.log10(2));
    }

    public double calcular_entripia(){
        double probabilidad;
        for(int i=0;i < entropia.cantidad_total;i++){
            probabilidad=entropia.calcular_probabilidad();
            entropia.resulatdo_entrpia=
        }


        return entropia.resulatdo_entrpia;
    }


     
    

}
