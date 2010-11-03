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
        Set columna_set = datos.entrySet();
        Iterator columna_it = columna_set.iterator();
	
        while(columna_it.hasNext()){
            HashMap valores= new HashMap();
            Map.Entry valores_me = (Map.Entry) columna_it.next();
	    columnas.add(valores_me.getKey());
            valores.putAll((Map)valores_me.getValue());
	    System.out.println(valores);
	    System.out.println(valores_me.getKey());
	    Set valores_set = valores.entrySet();
            Iterator valores_it = valores_set.iterator();
            while (valores_it.hasNext()){
		HashMap cantidad = new HashMap();
                Map.Entry cantidad_me = (Map.Entry) valores_it.next();
		cantidad.putAll((Map) cantidad_me.getValue());
		System.out.println(cantidad);
		Set cantidad_set = cantidad.entrySet();
		Iterator cantidad_it=cantidad_set.iterator();
		int i=0;
		while (cantidad_it.hasNext()){
		    
		    //String a = new String;
		    Map.Entry cantidadval_me = (Map.Entry) cantidad_it.next();
		    //a[i]=Integer.parseInt(cantidadval_me.getValue());
		   a[i]=Integer.parseInt(cantidadval_me.getValue().toString());
		   i++;
//
//
                }
		//System.out.println(a[0]+a[1]);
		System.out.println("ENTROPIA:"+entropia.calcular_entropia((a[0]+a[1]), a));
            }
        }
    }

}
