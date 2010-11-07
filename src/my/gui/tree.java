/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import java.util.*;
import java.lang.Integer;
/**
 *
 * @author Administrator
 */
public class tree {
    static public ArrayList columnas = new ArrayList();
    public tree(){
    }

    public static void tree(HashMap datos){
		int[] a = new int[5];
		double resultado= 0.0;
		double split=0.0;
		double infodeT;
		double ganancia;
		double radio_ganancia;
		String columna_actual;
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("9");
		prueba.add("5");
		Set columna_set = datos.entrySet();
		Iterator columna_it = columna_set.iterator();
		infodeT=entropia.infodeT(prueba);
		System.out.println("Info de T="+infodeT);
        while(columna_it.hasNext()){
            HashMap valores= new HashMap();
            Map.Entry valores_me = (Map.Entry) columna_it.next();
			columnas.add(valores_me.getKey());
            valores.putAll((Map)valores_me.getValue());
			columna_actual=valores_me.getKey().toString();
			System.out.println(columna_actual);
			//System.out.println(valores);
			Set valores_set = valores.entrySet();
            Iterator valores_it = valores_set.iterator();
			resultado=0.0;
			split=0.0;
            while (valores_it.hasNext()){
				HashMap cantidad = new HashMap();
                Map.Entry cantidad_me = (Map.Entry) valores_it.next();
				cantidad.putAll((Map) cantidad_me.getValue());
				//System.out.println(cantidad);
				Set cantidad_set = cantidad.entrySet();
				Iterator cantidad_it=cantidad_set.iterator();
				int i=0;
				while (cantidad_it.hasNext()){
					Map.Entry cantidadval_me = (Map.Entry) cantidad_it.next();
				   a[i]=Integer.parseInt(cantidadval_me.getValue().toString());
				   i++;
                }
				resultado=resultado+entropia.infodeXT(14,(a[0]+a[1]), a);
				split = split+entropia.splitinfo(14, (a[0]+a[1]));
			}
			System.out.println("infodeXT="+resultado);
			System.out.println("Split="+split);
			ganancia=infodeT-resultado;
			System.out.println("Ganancia="+ganancia);
			radio_ganancia=ganancia/split;
			System.out.println("Radio de ganancia="+radio_ganancia);
		}
    }

}
