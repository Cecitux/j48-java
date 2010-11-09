/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import java.sql.SQLException;
import java.util.*;
//import java.lang.Integer;
//import my.gui.DataBase;
/**
 *
 * @author Administrator
 */
public class tree {
    static public ArrayList columnas = new ArrayList();


    public static void tree(ArrayList prueba1, ArrayList prueba2, int lim) throws SQLException{
		String nodo_actual="";
		ArrayList valores = new ArrayList();
		int i=0;
		double infoT;
		Set nodo_set;
		HashMap nodo = new HashMap();
		/*ArrayList prueba1 = new ArrayList();
		ArrayList prueba2 = new ArrayList();*/
		HashMap map= new HashMap();
		/*prueba1.add("outlook");
		prueba1.add("wind");
		prueba2.add("rainy");
		prueba2.add("f");*/
		DataBase.Conectar("com", "root", "");
		DataBase.getNombresColumnas();
		map=DataBase.ParseoDB(prueba1, prueba2);
		DataBase.getValoresPantalla();
		nodo=tree.seleccionar_nodo(map, prueba1, prueba2);
		nodo_set=nodo.entrySet();
		Iterator nodo_it = nodo_set.iterator();

		//System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX ->"+nodo_actual);
		valores=DataBase.getValoresCol(nodo_actual);
		
		
		i=i+1;
		if(infoT<1){
			prueba1.add(nodo_actual);
			prueba2.add(valores.get(i));
			System.out.println("----------->"+prueba1+"--------"+prueba2);
			tree.tree(prueba1, prueba2, i);
			//prueba2.remove(i);
		}
		/*while(i<valores.size()){

		}*/
		//System.out.println(valores);
		System.out.println("----------->"+prueba1+"--------"+prueba2);
		DataBase.Desconectar();
    }

	public static HashMap seleccionar_nodo(HashMap datos, ArrayList prueba1, ArrayList prueba2) throws SQLException{
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
		HashMap retorno = new HashMap();
		/*ArrayList prueba1 = new ArrayList();
		ArrayList prueba2 = new ArrayList();*/
		/*prueba1.add("outlook");
		prueba1.add("wind");
		prueba2.add("rainy");
		prueba2.add("f");*/
		prueba=DataBase.getCantidadValores(DataBase.columna_decision, prueba1, prueba2);
		System.out.println("*************************************"+prueba);
		while (i<prueba.size()){
			cantidad_total_reg+=Integer.parseInt(prueba.get(i).toString());
			i++;
		}
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
			if(split==0.0){
				radio_ganancia=0.0;
			}else{
				radio_ganancia=ganancia/split;
			}
			System.out.println("infodeXT="+resultado);
			System.out.println("Split="+split);
			System.out.println("Ganancia="+ganancia);
			System.out.println("Radio de ganancia="+radio_ganancia);
			if(split_nodo < radio_ganancia){
				split_nodo=radio_ganancia;
				split_nodo_nombre=columna_actual;
			}
		}
		if(split_nodo==0.0){
			System.out.println("ES UN NODO HOJA");
		}else{
			System.out.println("EL NODO ELEGIDO ES "+split_nodo_nombre+" CON EL VALOR "+split_nodo);
		}
		retorno.put(split_nodo_nombre, split_nodo);
		return retorno;
	}

}
