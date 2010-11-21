/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import my.gui.gui;
import java.sql.*;
import java.util.*;


public class DataBase {
    public static Connection connection;
    public static ArrayList data = new ArrayList();
    public static String columna_decision="";
    public static String tabla_d = "";
    public static int get_promedio=0;
    //Los nombres de las columnas de la tabla
    public static ArrayList nom_columnas = new ArrayList();

    public static ArrayList nom_col_dis = new ArrayList();
    public static ArrayList val_col_dis = new ArrayList();
    //Conectar a la BD
    public static void Conectar(String nombre, String usuario, String pass) {
        String prueba = "jdbc:mysql://localhost:3306/"+nombre+"?" + "user="+usuario+"&password="+pass;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //String connectionUrl = "jdbc:mysql://localhost/mysql?" +"user=root&password=123456";
            connection = DriverManager.getConnection(prueba);
            System.out.println("Conectado");


        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: "+ cE.toString());
        }

    }

    public static void Desconectar(){ //se desconecta de la base de datos
        try {
            connection.close();
            System.out.println("Desconectado");
        } catch (SQLException ex) {
            System.out.println("Error al desconectar");
        }
    }
    //Funcion que devuelve los distintos valores de una columna
    public static ArrayList getValoresCol(String nom_columna) throws SQLException{
	Statement stmt = null;
	ResultSet rs = null;
	ArrayList lista = new ArrayList();
	int posactual;
	int cantcol=0, i=0;
	String SQL = "";
	if(nom_col_dis.isEmpty()){
	    SQL = "select "+nom_columna+" from "+tabla_d+" group by "+nom_columna;
	    stmt = connection.createStatement();
	    rs = stmt.executeQuery(SQL);
	    while (rs.next()) {
		lista.add(rs.getString(nom_columna));
	    }
	    
	}else{
	    posactual = buscarValDis(nom_col_dis, nom_columna);
	    if(posactual != -1){
		lista.add("<= "+val_col_dis.get(posactual).toString());
		lista.add("> "+val_col_dis.get(posactual).toString());
		
	    }else{
		SQL = "select "+nom_columna+" from "+tabla_d+" group by "+nom_columna;
		//System.out.println("gvc "+SQL);
		stmt = connection.createStatement();
		rs = stmt.executeQuery(SQL);
		while (rs.next()) {
		    lista.add(rs.getString(nom_columna));
		}
	    }
	}
	
	return lista;
    }

    public static String truncarStr(String val){
	if(val.contains("=")){
	    val = val.substring(3);
	}else if(val.contains("<") || val.contains(">")){
	    val = val.substring(2);
	}
	return val;
    }

    public static ArrayList getCantidadValores(ArrayList nomcol, ArrayList nomval) throws SQLException{
	Statement stmt = null;
	ResultSet rs = null;
	ArrayList lista = new ArrayList();
	ArrayList val = new ArrayList();
	Iterator it;
	String val_col_lista="", col_lista="";
	int i=0, b=0;

	val = getValoresCol(columna_decision);
	it = val.iterator();
	while (it.hasNext()){
		String SQL = "select count("+columna_decision+") from "+tabla_d+" where "+columna_decision+"= '"+it.next().toString()+"'";
		if(!nomcol.isEmpty() && !nomval.isEmpty()){
		    Iterator it_col = nomcol.iterator();
		    Iterator it_colval = nomval.iterator();
		    while (it_col.hasNext()){
				i=0;
			    val_col_lista = it_colval.next().toString();
			    col_lista = it_col.next().toString();
				while (nom_col_dis.size()>i){
					if(nom_col_dis.get(i).equals(col_lista)){
						b=1;
					}
					i++;
				}
				if(b==0){
					val_col_lista = truncarStr(val_col_lista);
					SQL = SQL + " and "+col_lista+"= '"+val_col_lista+"'";
				}else{
					if(val_col_lista.contains(">") || val_col_lista.contains("<")){
					    //val_col_lista = truncarStr(val_col_lista);
					    SQL = SQL + " and "+col_lista+" "+val_col_lista+"";
					}else{
					    SQL = SQL + " and "+col_lista+"= '"+val_col_lista+"'";
					}
				}
		    }
		}
		stmt = connection.createStatement();
		rs = stmt.executeQuery(SQL);
		while (rs.next()) {
		    lista.add(rs.getString("count("+columna_decision+")"));
		}
	}
	return lista;
    }
    //Funcion que devuelve los valores de las tablas para mostrarlos en pantalla
    public static void getValoresPantalla()throws SQLException{
	Statement stmt = null;
	ResultSet rs = null;
	String lineafila = "";
	 //Para mostrar en pantalla
	String SQL = "select * from "+tabla_d;
	stmt = connection.createStatement();
	rs = stmt.executeQuery(SQL);
	Iterator it_nomcol = nom_columnas.iterator();
	while (rs.next()) {
	    while (it_nomcol.hasNext()){
		lineafila = lineafila + "\t"+ rs.getString(it_nomcol.next().toString());
	    }
	    lineafila = lineafila.substring(1);
	    lineafila = lineafila + "\t\n      ";
	    data.add(lineafila);
	    it_nomcol = nom_columnas.iterator();
	    lineafila = "";
	}
    }
    //funcion que obitiene el nombre de la tabla y los nombres de las columnas
    public static void getNombresColumnas()throws SQLException{
	//SQL query command
	Statement stmt = null;
	ResultSet rs = null;
	String SQL = "show tables";
	ArrayList list = new ArrayList();
	stmt = connection.createStatement();
	rs = stmt.executeQuery(SQL);
	//Ver nombres de las tablas (se toma la primera)
	while (rs.next()) {
		list.add(rs.getString("Tables_in_com"));
	}
	//tabla_d = list.get(5).toString();
	//Nombres de las columnas
	SQL = "describe "+tabla_d;
	stmt = connection.createStatement();
	rs = stmt.executeQuery(SQL);
	while (rs.next()) {
		nom_columnas.add(rs.getString("Field"));
	}
    }
    //busca un valor especifico en una lista
    public static boolean buscarVal(ArrayList val, String val_c){
	Iterator it_val = val.iterator();
	while (it_val.hasNext()){
		if(it_val.next().toString().contentEquals(val_c)){
		return true; //distinto de cero
		}
	}
	return false;
    }
    //busca un valor especifico en una lista y devuelve la posicion en la que esta
    public static int buscarValDis(ArrayList val, String val_c){
	int i=0, tam;
	tam = val.size();
	while(i<tam){
		if(val.get(i).toString().equals(val_c)){
		return i;
		}
		i++;
	}
	return -1;
    }

    public static void getValoresAvg(ArrayList nom_col)throws SQLException{
	String SQLdt = "", sqldatatype="", auxlista = "", SQL="";
	Statement stmt = null;
	ResultSet rs = null;
	ArrayList queryactual = new ArrayList();
	Double valor=0.0;
	int val = 0;
	//Para sacar todos los valores promedio
	nom_col_dis.clear();
	val_col_dis.clear();
	Iterator it_nomcol = nom_col.iterator();
	while (it_nomcol.hasNext()){
	    auxlista = it_nomcol.next().toString();
	    
	    SQLdt = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+tabla_d+"' AND COLUMN_NAME = '"+auxlista+"'";
	    stmt = connection.createStatement();
	    rs = stmt.executeQuery(SQLdt);
	    while (rs.next()) {
		sqldatatype = rs.getString("DATA_TYPE").toString();
	    }
	    if(sqldatatype.contentEquals("int")){
		SQLdt = "select avg("+auxlista+") from "+tabla_d;
		stmt = connection.createStatement();
		rs = stmt.executeQuery(SQLdt);
		while (rs.next()) {
		    valor = Double.parseDouble(rs.getString("avg("+auxlista+")"));
		    val = valor.intValue();
		}
		nom_col_dis.add(auxlista);
		val_col_dis.add(val);
	    }else{
		if(buscarDataTypes(auxlista)==1){
		    SQLdt = "select avg("+auxlista+") from "+tabla_d;
		    stmt = connection.createStatement();
		    rs = stmt.executeQuery(SQLdt);
		    while (rs.next()) {
			valor = Double.parseDouble(rs.getString("avg("+auxlista+")"));
			val = valor.intValue();
		    }
		    nom_col_dis.add(auxlista);
		    val_col_dis.add(val);
		}
	    }
	}
	//System.out.println("funcion nueva "+nom_col_dis+" "+val_col_dis);
    }

    public static int buscarDataTypes(String auxlista )throws SQLException{
	String SQL = "";
	SQL = "select "+auxlista+" from "+tabla_d+" group by "+auxlista;
	String valactual="";
	Statement stmt = null;
	ResultSet rs = null;
	ArrayList lista = new ArrayList();
	Iterator it;
	int valact = 0;
	stmt = connection.createStatement();
	rs = stmt.executeQuery(SQL);
	while (rs.next()) {
	    lista.add(rs.getString(auxlista));
	}
	it = lista.iterator();
	while(it.hasNext()){
	    valactual = it.next().toString();
	    try {
		valact = Integer.parseInt(valactual);
	    } catch (Exception ex) {
		//no sirve para discretizar
		return 0;
	    }
	}
	//sirve para discretizar
	return 1;
    }
    /**
     *
     * @param nomcol:
     * @param valactual:
     * @return
     * @throws SQLException
     */
    public static HashMap ParseoDB(ArrayList nomcol, ArrayList valactual) throws SQLException{
	HashMap hdb = new HashMap();
	Statement stmt = null;
	ResultSet rs = null;
	ArrayList nom_columnas_actual = new ArrayList();
	ArrayList queryactual = new ArrayList();
	ArrayList val_col_desicion = new ArrayList();

	//ArrayList auxvalor = new ArrayList();
	int numreg = 0, numvalcol=0, numvalcoldismay=0, numvalcoldismen=0;
	double numvalcold=0.0;
	int band_col=0;
	String col_lista="";
	String val_col_lista="";
	String nom_columnas_aux="";
	String auxlista ="", auxlistaval="";
	String auxval = "", auxval1="";
	String SQL = "";
	String SQLcantnum="", SQLprom="", SQLdt="", sqldatatype="";
	String SQLcol="";
	Iterator itval, itval1;
	int posactual=0, posactualres;
	val_col_desicion = getValoresCol(columna_decision);
    //----------GUARDA TODAS LAS VARIABLES DE UNA COLUMNA
	//auxlista = columnas
	//queryactual = distintos valores de las columnas
	//System.out.println("inicio "+nom_col_dis+" "+val_col_dis);
	SQL = "select count(*) from "+tabla_d;
	stmt = connection.createStatement();
	rs = stmt.executeQuery(SQL);
	while (rs.next()) {
	    numreg = Integer.parseInt(rs.getString("count(*)"));
	}
	//System.out.println("GET_PROMEDIO "+get_promedio);

	Iterator it_nomcol = nom_columnas.iterator();
	//if(nomcol.contains(columna_decision)){
	if((!nomcol.isEmpty() && !valactual.isEmpty())){
	    while (it_nomcol.hasNext()){
		nom_columnas_aux = it_nomcol.next().toString();
		if(!buscarVal(nomcol, nom_columnas_aux)){
		    nom_columnas_actual.add(nom_columnas_aux);
		}
	    }
	    it_nomcol = nom_columnas_actual.iterator();
	    if(nom_columnas_actual.contains(columna_decision) && nom_columnas_actual.size()==1){
		it_nomcol = nom_columnas.iterator();
	    }

	}
	//llamar aca a la funcion promedio
	if(get_promedio==1){
	    if(nom_columnas_actual.isEmpty()){
		//System.out.println("Esta aca porque se itera por todas las columnas");
		getValoresAvg(nom_columnas);
		get_promedio=0;
	    }else{
		getValoresAvg(nom_columnas_actual);
		get_promedio=0;
	    }
	}
	
	while (it_nomcol.hasNext()){
	    //select puertas from cars group by puertas;
	    auxlista = it_nomcol.next().toString();
	    SQL = "select "+auxlista+" from "+tabla_d+" group by "+auxlista;
		stmt = connection.createStatement();
		rs = stmt.executeQuery(SQL);
		while (rs.next()) {
		    queryactual.add(rs.getString(auxlista));
		}
	    HashMap hdbvalor_segundo_nivel = new HashMap();
	    if(!auxlista.contentEquals(columna_decision)){
		if(nom_col_dis.contains("columna") && (nom_col_dis.size() == 1)){
		    itval = queryactual.iterator();
		    while (itval.hasNext()){
			//contar la cantidad de ocurrencias con respecto a la columna objetivo
			HashMap hdbval = new HashMap();
			auxval = itval.next().toString();
			itval1 = val_col_desicion.iterator();
			while (itval1.hasNext()){
			    auxval1 = itval1.next().toString();
			    SQLcantnum = "select count("+auxlista+") from "+tabla_d+" where "+columna_decision+" = '"+auxval1+"' and "+auxlista+"= '"+auxval+"'";
			    if(!nomcol.isEmpty() && !valactual.isEmpty()){
				Iterator it_col = nomcol.iterator();
				Iterator it_colval = valactual.iterator();
				while (it_col.hasNext()){
				    val_col_lista = it_colval.next().toString();
				    col_lista = it_col.next().toString();
				    SQLcantnum = SQLcantnum + " and "+col_lista+"= '"+val_col_lista+"'";
				}
			    }
			    //System.out.println(SQLcantnum);
			    stmt = connection.createStatement();
			    rs = stmt.executeQuery(SQLcantnum);
			    while (rs.next()) {
				numvalcol = Integer.parseInt(rs.getString("count("+auxlista+")"));
			    }
			    hdbval.put(auxval1, numvalcol);
			}
			hdbvalor_segundo_nivel.put(auxval, hdbval);
		    }
		    queryactual.clear();
		    hdb.put(auxlista, hdbvalor_segundo_nivel);
		}else{
///------DESDE AQUI SE DISCRETIZAN LOS VALORES (SI LOS HAY)
		    //System.out.println("no tiene q entrar");
		    itval = queryactual.iterator();
		    while (itval.hasNext()){
			//contar la cantidad de ocurrencias con respecto a la columna objetivo
			HashMap hdbval = new HashMap();
			itval1 = val_col_desicion.iterator();
			while (itval1.hasNext()){
			    auxval1 = itval1.next().toString();
			    hdbval.put(auxval1, 0);
			}
			auxval = itval.next().toString();
			itval1 = val_col_desicion.iterator();
			posactual = buscarValDis(nom_col_dis, auxlista);
			while (itval1.hasNext()){
			    auxval1 = itval1.next().toString();
			    if(posactual != -1){
				auxval = truncarStr(auxval);
				if(Integer.parseInt(auxval) <= Integer.parseInt(val_col_dis.get(posactual).toString())){
				    SQLcantnum = "select count("+auxlista+") from "+tabla_d+" where "+columna_decision+" = '"+auxval1+"' and "+auxlista+"<= "+Integer.parseInt(val_col_dis.get(posactual).toString())+"";
				}else{
				    SQLcantnum = "select count("+auxlista+") from "+tabla_d+" where "+columna_decision+" = '"+auxval1+"' and "+auxlista+"> "+Integer.parseInt(val_col_dis.get(posactual).toString())+"";
				}
			    }else{
				SQLcantnum = "select count("+auxlista+") from "+tabla_d+" where "+columna_decision+" = '"+auxval1+"' and "+auxlista+"= '"+auxval+"'";
			    }
			    int band=0;
			    if(!nomcol.isEmpty() && !valactual.isEmpty()){
				Iterator it_col = nomcol.iterator();
				Iterator it_colval = valactual.iterator();
				while (it_col.hasNext()){
				    val_col_lista = it_colval.next().toString();
				    col_lista = it_col.next().toString();
				    //System.out.println("val_col_lista= "+val_col_lista);
				    posactualres = buscarValDis(nom_col_dis, col_lista);
				    if(posactualres != -1){
					if(val_col_lista.contains("<")){
					    band=1;
					}
					val_col_lista = truncarStr(val_col_lista);
					//System.out.println("val= "+val_col_lista+"col= "+col_lista);
					//if(Integer.parseInt(val_col_lista) <= Integer.parseInt(val_col_dis.get(posactualres).toString())){
					if(band==1){
					    SQLcantnum = SQLcantnum + " and "+col_lista+"<= "+val_col_lista+"";
					    band=0;
					}else{
					    SQLcantnum = SQLcantnum + " and "+col_lista+"> "+val_col_lista+"";
					}
					//System.out.println("gola1 "+Integer.parseInt(val_col_lista)+" "+Integer.parseInt(val_col_dis.get(posactualres).toString()));
				    }else{
					SQLcantnum = SQLcantnum + " and "+col_lista+"= '"+val_col_lista+"'";
				    }
				}
			    }
			    //System.out.println(SQLcantnum);
			    stmt = connection.createStatement();
			    rs = stmt.executeQuery(SQLcantnum);
			    while (rs.next()) {
				numvalcol = Integer.parseInt(rs.getString("count("+auxlista+")"));
			    }
			   
			    hdbval.put(auxval1, numvalcol);
			}
			if(posactual != -1){
			    if(Integer.parseInt(auxval) <= Integer.parseInt(val_col_dis.get(posactual).toString())){
				hdbvalor_segundo_nivel.put("<="+val_col_dis.get(posactual).toString(), hdbval);
			    }else{
				hdbvalor_segundo_nivel.put(">"+val_col_dis.get(posactual).toString(), hdbval);
			    }
			}else{
			    hdbvalor_segundo_nivel.put(auxval, hdbval);
			}

		    }

		    queryactual.clear();
		    hdb.put(auxlista, hdbvalor_segundo_nivel);
		}
		queryactual.clear();
	    }
	}

	//Imprime el diccionario actual
	String s, s1;
	Iterator it21;
	for( it21 = hdb.keySet().iterator(); it21.hasNext();) {
		s = it21.next().toString();
		s1 = hdb.get(s).toString();
		System.out.println(s + " : " + s1);
	}

	return hdb;
    }
}
