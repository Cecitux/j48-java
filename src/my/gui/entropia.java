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
    /*public entropia(int cantt, int cantc[]){

        entropia.cantidad_de_clase=new int[cantt];
        entropia.cantidad_de_clase=cantc;
        entropia.cantidad_total=cantt;
        entropia.resulatdo_entrpia=0.0;
       
    }*/

    static double calcular_probabilidad(int universo, int a){
        double resultado;
        resultado=(double) a/universo;
                return resultado;
    }
    /**
     * Calcula el logaritmo en base dos del numeros
     * @param numero
     * @return logaritmo en base 2
     */
    static double logaritmo_base2(double numero){
       return (Math.log10(numero)/Math.log10(2));
    }

    public entropia(int cantt, int cantc[]){

        entropia.cantidad_de_clase=new int[cantt];
        entropia.cantidad_de_clase=cantc;
        entropia.cantidad_total=cantt;
        entropia.resulatdo_entrpia=0.0;

    }

   public entropia(){
       
   }

    public double calcular_entropia(int cantidad_total, int cantidad_de_clase[]){
        double probabilidad;
        for(int i=0; i < cantidad_de_clase.length; i++){
            probabilidad=entropia.calcular_probabilidad(cantidad_total, cantidad_de_clase[i]);
            entropia.resulatdo_entrpia-= (probabilidad*entropia.logaritmo_base2(probabilidad));
        }
        return entropia.resulatdo_entrpia;
    }


     
    

}
