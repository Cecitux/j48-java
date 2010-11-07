/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import my.gui.gui;
import java.sql.*;
import java.util.*;


public class DataBase {
    //Variables de Lore para la BD
    public static Connection connection;
    public static ArrayList data = new ArrayList();
    public static String columna_decision="play";
    public static String tabla_d = "";
    //int[] cantValDecision;
    //Los nombres de las columnas de la tabla
    public static ArrayList nom_columnas = new ArrayList();
    //Conectar a la BD
    public static void Conectar(String nombre, String usuario, String pass) {
        // cadenaconexion = "";
        String prueba = "jdbc:mysql://localhost:3306/"+nombre+"?" + "user="+usuario+"&password="+pass;
        //String prueba="jdbc:mysql://localhost:3306/com?" +"user=root&password=123456"; //para lore y fer
        //String prueba="jdbc:mysql://localhost:3306/com?" +"user=root&password=mysql"; //para adri
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
	//String SQL = "select "+columna_decision+" from "+tabla_d+" group by "+columna_decision;
	String SQL = "select "+nom_columna+" from "+tabla_d+" group by "+nom_columna;
	stmt = connection.createStatement();
	rs = stmt.executeQuery(SQL);
	while (rs.next()) {
	    lista.add(rs.getString(nom_columna));
	}
	return lista;
    }

    public static ArrayList getCantidadValores(String nom_columna) throws SQLException{
	Statement stmt = null;
	ResultSet rs = null;
	ArrayList lista = new ArrayList();
	ArrayList val = new ArrayList();
	Iterator it;
	//String SQL = "select "+columna_decision+" from "+tabla_d+" group by "+columna_decision;
	val = getValoresCol(nom_columna);
	it = val.iterator();
	while (it.hasNext()){
	    String SQL = "select count("+nom_columna+") from "+tabla_d+" where "+nom_columna+"= '"+it.next().toString()+"'";
	    stmt = connection.createStatement();
	    rs = stmt.executeQuery(SQL);
	    while (rs.next()) {
		lista.add(rs.getString("count("+nom_columna+")"));
	    }
	}
	
	return lista;
    }
    //Funcion que devuelve los valores de las tablas para mostrarlos en pantalla
    public static void getValoresPantalla()throws SQLException{
	Statement stmt = null;
	ResultSet rs = null;
	//Esto no se usa, se debe hacer una funcion para traer los nombres de las columnas

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
	tabla_d = list.get(4).toString();
	//Nombres de las columnas
	SQL = "describe "+tabla_d;
	stmt = connection.createStatement();
	rs = stmt.executeQuery(SQL);
	while (rs.next()) {
	    nom_columnas.add(rs.getString("Field"));
	}
    }

    public static boolean buscarVal(ArrayList val, String val_c){
	Iterator it_val = val.iterator();
	while (it_val.hasNext()){
	    if(it_val.next().toString().contentEquals(val_c)){
		//System.out.println("son iguales: "+val_c);
		return true; //distinto de cero
	    }
	}
	//System.out.println("hola "+ val_c);
	return false;
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
	int numreg = 0, numvalcol=0;
	int band_col=0;
	String col_lista="";
	String val_col_lista="";
	String nom_columnas_aux="";
	String auxlista ="", auxlistaval="";
	String auxval = "", auxval1="";
	String SQL = "";
	String SQLcantnum="";
	String SQLcol="";
	Iterator itval, itval1;
	val_col_desicion = getValoresCol(columna_decision);
    //----------GUARDA TODAS LAS VARIABLES DE UNA COLUMNA
	//auxlista = columnas
	//queryactual = distintos valores de las columnas
	SQL = "select count(*) from "+tabla_d;
	stmt = connection.createStatement();
	rs = stmt.executeQuery(SQL);
	while (rs.next()) {
	    numreg = Integer.parseInt(rs.getString("count(*)"));
	}

	Iterator it_nomcol = nom_columnas.iterator();
	if(!nomcol.isEmpty() && !valactual.isEmpty()){
	    while (it_nomcol.hasNext()){
		nom_columnas_aux = it_nomcol.next().toString();
		if(!buscarVal(nomcol, nom_columnas_aux)){
		    nom_columnas_actual.add(nom_columnas_aux);

		}
	    }
	    System.out.println(nom_columnas_actual);
	    it_nomcol = nom_columnas_actual.iterator();
	}
	while (it_nomcol.hasNext()){
	    //select puertas from cars group by puertas;
	    auxlista = it_nomcol.next().toString();
	    queryactual = getValoresCol(auxlista);
	    HashMap hdbvalor_segundo_nivel = new HashMap();
	    if(!auxlista.contentEquals(columna_decision)){
		itval = queryactual.iterator();
		while (itval.hasNext()){
		    //contar la cantidad de ocurrencias con respecto a la columna objetivo
		    HashMap hdbval = new HashMap();
		    auxval = itval.next().toString();
		    //if(!auxval.contentEquals(valactual)){
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
	    }
	    queryactual.clear();
	}
	//}
	//Imprime el diccionario actual
	String s, s1;
	Iterator it21;
	for( it21 = hdb.keySet().iterator(); it21.hasNext();) {
		s = it21.next().toString();
		s1 = hdb.get(s).toString();
		System.out.println(s + " : " + s1);
	}
	//}

	return hdb;
    }
}
