/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import java.sql.SQLException;
import java.util.*;
import java.lang.Integer;
import my.gui.DataBase;
/**
 *
 * @author Administrator
 */
public class tree {
    static public ArrayList columnas = new ArrayList();
    public tree(){
    }

    public static void tree(HashMap datos) throws SQLException{
		//int[] a = new int[5];
		int suma_total_clase=0;
		ArrayList<String> a = new ArrayList<String>();
		double resultado= 0.0;
		double split=0.0;
		double infodeT=0.0;
		double ganancia=0.0;
		double radio_ganancia=0.0;
		String columna_actual;
		ArrayList prueba = new ArrayList();
		double split_nodo=-10;
		String split_nodo_nombre="";
		int cantidad_total_reg=0;
		int i=0;
		//prueba=DataBase.getCantidadValores(DataBase.columna_decision);
		prueba.add("9");
		prueba.add("5");
		while (i<prueba.size()){
			cantidad_total_reg+=Integer.parseInt(prueba.get(i).toString());
			i++;
		}
		//System.out.println("AAAAAA---------------->"+prueba);
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
				a.clear();
				suma_total_clase=0;
				while (cantidad_it.hasNext()){
					Map.Entry cantidadval_me = (Map.Entry) cantidad_it.next();
					a.add(cantidadval_me.getValue().toString());
					suma_total_clase+=Integer.parseInt(cantidadval_me.getValue().toString());
                }
				resultado=resultado+entropia.infodeXT(cantidad_total_reg,suma_total_clase, a);
				split = split+entropia.splitinfo(cantidad_total_reg, suma_total_clase);
			}
			
			ganancia=infodeT-resultado;
			
			radio_ganancia=ganancia/split;
			System.out.println("infodeXT="+resultado);
			System.out.println("Split="+split);
			System.out.println("Ganancia="+ganancia);
			System.out.println("Radio de ganancia="+radio_ganancia);
			if(split_nodo < radio_ganancia){
				split_nodo=radio_ganancia;
				split_nodo_nombre=columna_actual;
			}
		}
		System.out.println("EL NODO ELEGIDO ES "+split_nodo_nombre+" CON EL VALOR "+split_nodo);
    }

}
