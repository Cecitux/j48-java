/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
//import java.lang.Math;
import java.util.*;
/**
 *
 * @author Administrator
 */
public class entropia {
    static int cantidad_total;
    static int cantidad_de_clase[];
    static double resultado_entropia;

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
    //Hashtable a = new Hashtable();
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
	if (numero==0){
	    return 0.0;
	}else{
	    return (Math.log10(numero)/Math.log10(2));
	}
    }

    public entropia(int cantt, int cantc[]){

        entropia.cantidad_de_clase=new int[cantt];
        entropia.cantidad_de_clase=cantc;
        entropia.cantidad_total=cantt;
        entropia.resultado_entropia=0.0;

    }

   public entropia(){
       
   }

   /**
    *
    * @param numero_registros: es el numero total de registros de la tabla
    * @param cantidad_total: es la cantidad
    * @param cantidad_de_clase
    * @return
    */
    public static double infodeXT(int numero_registros, int cantidad_total, int cantidad_de_clase[]){
        double probabilidad;
        entropia.resultado_entropia=0.0;
        for(int i=0; i < cantidad_de_clase.length; i++){
            probabilidad=entropia.calcular_probabilidad(cantidad_total, cantidad_de_clase[i]);
            entropia.resultado_entropia-= (probabilidad*entropia.logaritmo_base2(probabilidad));
        }
		entropia.resultado_entropia=(double)cantidad_total/numero_registros*entropia.resultado_entropia;
        return entropia.resultado_entropia;
    }

	public static double splitinfo(int numero_registros, int cantidad_de_clase[]){
		double splitinfo=0.0;
		double probabilidad;
		for(int i=0; i < cantidad_de_clase.length; i++){
            probabilidad=entropia.calcular_probabilidad(numero_registros, cantidad_de_clase[i]);
            splitinfo-= (probabilidad*entropia.logaritmo_base2(probabilidad));
        }
		return splitinfo;
	}
	public static double infodeT(ArrayList columna){
		double infoT=0.0;
		double probabilidad=0.0;
		int cantidad_infodT=0;
		int i=0;
		while(i<columna.size()){
			cantidad_infodT+=Integer.parseInt(columna.get(i).toString());
			i++;
		}
		i=0;
		while(i<columna.size()){
			probabilidad=entropia.calcular_probabilidad(cantidad_infodT, Integer.parseInt(columna.get(i).toString()));
			infoT-=(probabilidad*entropia.logaritmo_base2(probabilidad));
			i++;
		}
		return infoT;
	}
}
