/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import java.lang.Math;
/**
 *
 * @author Administrator
 */
public class entropia {
    static int cantidad_total;
    static int[] cantidad_de_clase;
   
    

    private double calcular_probabilidad(){
        double resultado;
        resultado=(double) entropia.cantidad_de_clase/entropia.cantidad_total;
        return resultado;
    }
    /**
     * Calcula el logaritmo en base dos del nuero
     * @param numero
     * @return logaritmo en base 2
     */
    private double logaritmo_base2(double numero){
       return (Math.log10(numero)/Math.log10(2));
    }




     /**
     *
     * @param cantt: es la cantidad de filas de la tabla
     * @param cantc: es la cantidad de registros de ese tipo de dato
     */
    public entropia(int cantt, int[] cantc){
        entropia.cantidad_de_clase=cantc;
        entropia.cantidad_total=cantt;
    }

}
