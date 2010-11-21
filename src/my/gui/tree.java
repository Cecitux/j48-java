
package my.gui;
import java.sql.SQLException;
import java.util.*;
/*import java.lang.Integer;
import my.gui.DataBase;
import my.gui.Log;
import my.gui.gui;*/


public class tree {
    static public ArrayList columnas = new ArrayList();
    public tree(){
    }
	//TODO: armar la lista para el grafico del arbol
    public static void generar_arbol(ArrayList columna,ArrayList valores,Double infodeT,ArrayList arbol, int profundidad) throws SQLException{
		HashMap mapa=new HashMap();
		ArrayList nodo=new ArrayList();
		ArrayList valores_aux=new ArrayList();
		ArrayList valores_col_decision=new ArrayList();
		int ultimo_valor=0;
		int ultima_columna=0;
		mapa=DataBase.ParseoDB(columna, valores);
		System.out.println("columna: "+columna);
		System.out.println("valores:"+valores);
		valores_col_decision=DataBase.getCantidadValores(columna, valores);
		nodo=calcular_nodo(mapa,valores_col_decision);
		infodeT=Double.parseDouble(nodo.get(1).toString());
		if(infodeT != 0){
			columna.add(nodo.get(0));
			//arbol.add(",");
			arbol.add(nodo.get(0));
			
			valores_aux=DataBase.getValoresCol(nodo.get(0).toString());
			arbol.add("@");
			while(!valores_aux.isEmpty()){
				arbol.add(profundidad);
				valores.add(valores_aux.get(0));
				arbol.add(valores_aux.get(0));
				valores_aux.remove(0);
				generar_arbol(columna, valores, infodeT,arbol, profundidad+1);
				arbol.add("*");
			}
			ultima_columna=columna.size()-1;
			columna.remove(ultima_columna);
			//arbol.add("#");
			if(!valores.isEmpty()){
				ultimo_valor=valores.size()-1;
				valores.remove(ultimo_valor);
			}
		}else{
			ultimo_valor=valores.size()-1;
			valores.remove(ultimo_valor);
			arbol.add(nodo.get(2));
			System.out.println("\nEs un nodo hoja con valor "+nodo.get(2)+"\n");
		}
	
    }

    public static ArrayList calcular_nodo(HashMap datos,ArrayList valores_col_decision) throws SQLException{
        int suma_total_clase=0;
        ArrayList<String> a = new ArrayList<String>();
		ArrayList label = new ArrayList();
		ArrayList<Integer> values = new ArrayList();
		ArrayList retorno=new ArrayList();

        double resultado= 0.0;
        double split=0.0;
        double infodeT=0.0;
        double ganancia=0.0;
        double radio_ganancia=0.0;
        String columna_actual;
        double split_nodo=-10;
        String split_nodo_nombre="";
        int cantidad_total_reg=0;
        int i=0;
		Integer auxiliar=0;
		label=DataBase.getValoresCol(DataBase.columna_decision);
		
        while (i<valores_col_decision.size()){
            cantidad_total_reg+=Integer.parseInt(valores_col_decision.get(i).toString());
            i++;
        }

        infodeT=entropia.infodeT(valores_col_decision);
        Log.datosLog.add("  " + new Date() + "\tCalculo de Info(T): " + infodeT + "\n");

        //if(infodeT!=0){
			Set columna_set = datos.entrySet();
			Iterator columna_it = columna_set.iterator();
			while(columna_it.hasNext()){
				HashMap valores= new HashMap();
				Map.Entry valores_me = (Map.Entry) columna_it.next();
				columnas.add(valores_me.getKey());
				valores.putAll((Map)valores_me.getValue());
				columna_actual=valores_me.getKey().toString();
				Log.datosLog.add("  " + new Date() + "\tColumna analizada: " + columna_actual + "\n");
				Set valores_set = valores.entrySet();
				Iterator valores_it = valores_set.iterator();
				resultado=0.0;
				split=0.0;
				while (valores_it.hasNext()){
					HashMap cantidad = new HashMap();
					Map.Entry cantidad_me = (Map.Entry) valores_it.next();
					cantidad.putAll((Map) cantidad_me.getValue());
					Set cantidad_set = cantidad.entrySet();
					Iterator cantidad_it=cantidad_set.iterator();
					a.clear();
					suma_total_clase=0;
					while (cantidad_it.hasNext()){
						i=0;
						Map.Entry cantidadval_me = (Map.Entry) cantidad_it.next();
						//label.add(cantidadval_me.getKey().toString());
						///values.add(cantidadval_me.getValue().toString());
						/*while(i<label.size()){
							if(label.get(i).equals(cantidadval_me.getKey().toString())){
								suma_auxiliar=values.get(i);
								suma_auxiliar+=Integer.parseInt(cantidadval_me.getValue().toString());
								values.set(i, suma_auxiliar);
								suma_auxiliar=0;
							}
							i++;
						}*/
						a.add(cantidadval_me.getValue().toString());
						suma_total_clase+=Integer.parseInt(cantidadval_me.getValue().toString());
					}
					if(infodeT!=0){
						resultado=resultado+entropia.infodeXT(cantidad_total_reg,suma_total_clase, a);
						split = split+entropia.splitinfo(cantidad_total_reg, suma_total_clase);
					}
				}
				if(infodeT!=0){
					ganancia=infodeT-resultado;
					radio_ganancia=ganancia/split;
					Log.datosLog.add("  " + new Date() + "\tCalculo de Info(T): " + resultado + "\n");
					Log.datosLog.add("  " + new Date() + "\tCalculo de SplitInfo(T): " + split + "\n");
					Log.datosLog.add("  " + new Date() + "\tCalculo de Ganancia(T): " + ganancia + "\n");
					Log.datosLog.add("  " + new Date() + "\tCalculo de RadioDeGanancia(T): " + radio_ganancia + "\n");
					if(split_nodo < radio_ganancia){
						split_nodo=radio_ganancia;
						split_nodo_nombre=columna_actual;
					}
				}
			}
			if(infodeT!=0){
				System.out.println("EL NODO ELEGIDO ES "+split_nodo_nombre+" CON EL VALOR "+split_nodo);
				Log.datosLog.add("\n  " + new Date() + "\tNodo Elegido: " + split_nodo_nombre +", con el valor: " + split_nodo + "\n\n");
			}else{
				System.out.println("!!!!!!!!!!!!!");
				System.out.println(label);
				System.out.println(valores_col_decision);
			}
		retorno.add(split_nodo_nombre);
		retorno.add(infodeT);
		if(infodeT==0){
			i=0;
			auxiliar=Integer.parseInt(valores_col_decision.get(i).toString());
			String aux=label.get(i).toString();
			while (i<valores_col_decision.size()){
				if (Integer.parseInt(valores_col_decision.get(i).toString())>auxiliar){
					aux=label.get(i).toString();
					auxiliar=Integer.parseInt(valores_col_decision.get(i).toString());
				}
				i++;
			}
			aux=aux.concat(" ("+auxiliar.toString()+"/"+cantidad_total_reg+")");
			//aux=aux.concat(auxiliar.toString());

			//aux=aux.concat(" )");
			//aux.concat("a");
			retorno.add(aux);
		}
		System.out.println("@@@@@"+retorno+"\n");
		return retorno;
    }


}
