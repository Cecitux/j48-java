
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

    public static void generar_arbol(ArrayList columna,ArrayList valores,Double infodeT) throws SQLException{
		HashMap mapa=new HashMap();
		ArrayList nodo=new ArrayList();
		ArrayList valores_aux=new ArrayList();
		ArrayList valores_col_decision=new ArrayList();
		int ultimo_valor=0;
		int ultima_columna=0;

		mapa=DataBase.ParseoDB(columna, valores);
		valores_col_decision=DataBase.getCantidadValores(columna, valores);
		nodo=calcular_nodo(mapa,valores_col_decision);
		infodeT=Double.parseDouble(nodo.get(1).toString());
		System.out.println("infodeT: "+infodeT);
		System.out.println("**********");
		if(infodeT != 0){
			System.out.println("-----------------");
			System.out.println("columna: "+columna);
			columna.add(nodo.get(0));
			valores_aux=DataBase.getValoresCol(nodo.get(0).toString());
			while(!valores_aux.isEmpty()){
				valores.add(valores_aux.get(0));
				valores_aux.remove(0);
				System.out.println("valores:"+valores);
				System.out.println("valores_aux: "+valores_aux);
				generar_arbol(columna, valores, infodeT);
				System.out.println("voy a otra iteracion");
			}
			ultima_columna=columna.size()-1;
			columna.remove(ultima_columna);
			if(!valores.isEmpty()){
				ultimo_valor=valores.size()-1;
				valores.remove(ultimo_valor);
			}
		}else{
			ultimo_valor=valores.size()-1;
			valores.remove(ultimo_valor);
			System.out.println("Es un nodo hoja");
		}
    }

    public static ArrayList calcular_nodo(HashMap datos,ArrayList valores_col_decision ) throws SQLException{
        //int[] a = new int[5];
        int suma_total_clase=0;
        ArrayList<String> a = new ArrayList<String>();
	ArrayList retorno=new ArrayList();
        double resultado= 0.0;
        double split=0.0;
        double infodeT=0.0;
        double ganancia=0.0;
        double radio_ganancia=0.0;
        String columna_actual;
        //ArrayList valores_col_decision = new ArrayList();
        double split_nodo=-10;
        String split_nodo_nombre="";
        int cantidad_total_reg=0;
        int i=0;
        //prueba=DataBase.getCantidadValores(DataBase.columna_decision);

        /*valores_col_desicion.add("9");
        valores_col_desicion.add("5");*/
	//valores_col_decision=DataBase.getCantidadValores(columnas, columnas);
        while (i<valores_col_decision.size()){
            cantidad_total_reg+=Integer.parseInt(valores_col_decision.get(i).toString());
            i++;
        }
        //System.out.println("AAAAAA---------------->"+prueba);
        Set columna_set = datos.entrySet();
        Iterator columna_it = columna_set.iterator();
        infodeT=entropia.infodeT(valores_col_decision);
        //System.out.println("Info de T="+infodeT);
        Log.datosLog.add("  " + new Date() + "\tCalculo de Info(T): " + infodeT + "\n");
        
        while(columna_it.hasNext()){
            HashMap valores= new HashMap();
            Map.Entry valores_me = (Map.Entry) columna_it.next();
            columnas.add(valores_me.getKey());
            valores.putAll((Map)valores_me.getValue());
            columna_actual=valores_me.getKey().toString();
            //System.out.println(columna_actual);
            Log.datosLog.add("  " + new Date() + "\tColumna analizada: " + columna_actual + "\n");
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
           // System.out.println("infodeXT="+resultado);
            Log.datosLog.add("  " + new Date() + "\tCalculo de Info(T): " + resultado + "\n");
           // System.out.println("Split="+split);
            Log.datosLog.add("  " + new Date() + "\tCalculo de SplitInfo(T): " + split + "\n");
            //System.out.println("Ganancia="+ganancia);
            Log.datosLog.add("  " + new Date() + "\tCalculo de Ganancia(T): " + ganancia + "\n");
           // System.out.println("Radio de ganancia="+radio_ganancia);
            Log.datosLog.add("  " + new Date() + "\tCalculo de RadioDeGanancia(T): " + radio_ganancia + "\n");
            if(split_nodo < radio_ganancia){
                split_nodo=radio_ganancia;
                split_nodo_nombre=columna_actual;
            }
        }
        System.out.println("EL NODO ELEGIDO ES "+split_nodo_nombre+" CON EL VALOR "+split_nodo);
        Log.datosLog.add("  " + new Date() + "\tNodo Elegido: " + split_nodo_nombre +", con el valor: " + split_nodo + "\n");
	retorno.add(split_nodo_nombre);
	retorno.add(infodeT);
	return retorno;
    }

}
