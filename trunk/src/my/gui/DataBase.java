/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import my.gui.gui;
import java.sql.*;
import java.util.*;

//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

//import java.awt.*;
//import java.awt.event.*;
//import java.util.ArrayList;
//import java.util.Iterator;




public class DataBase {
    //Variables de Lore para la BD
    public static Connection connection;
    public static ArrayList data = new ArrayList();
    public static String columna_desicion="seguridad";
    //Conectar a la BD
    public static void Conectar() {
        // cadenaconexion = "";
        //String prueba = "jdbc:mysql://localhost/"+gui.nombrebd+"?" + "user="+gui.usuario+"&password="+gui.password;
        String prueba="jdbc:mysql://localhost:3306/com?" +"user=root&password=123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //String connectionUrl = "jdbc:mysql://localhost/mysql?" +"user=root&password=123456";
            connection = DriverManager.getConnection(prueba);
            System.out.println("Conectado");

            //nueva conexion
            //Class.forName(gui.nombrebd+".mysql.jdbc.Driver");
            //Ese "com" no se modifica
            /*Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(prueba);
		DatabaseMetaData dbmd = connection.getMetaData();
		System.out.println("Connection to " + dbmd.getDatabaseProductName()
				+ " " + dbmd.getDatabaseProductVersion() + " successful.\n");*/

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

    public static HashMap ParseoDB() throws SQLException{
        HashMap hdb = new HashMap();
        Statement stmt = null;
        ResultSet rs = null;

        ArrayList list = new ArrayList();
        ArrayList nom_columnas = new ArrayList();
        ArrayList queryactual = new ArrayList();
        ArrayList val_col_desicion = new ArrayList();
        ArrayList auxvalor = new ArrayList();
        //nombre de la tabla de la BD, si hay mas de una se toma la primera
        String tabla_d = "";
        int numreg = 0, numvalcol=0;
        String auxlista ="";
        String auxcant = "";
        String auxval = "", auxval1="";
        String lineafila = "";
        //SQL query command
        String SQL = "show tables";
        String SQLcantnum="";
        String SQLcol="";
        stmt = connection.createStatement();
        rs = stmt.executeQuery(SQL);
        
        //Ver nombres de las tablas (se toma la primera)
        while (rs.next()) {
            list.add(rs.getString("Tables_in_com"));
        }
        
        tabla_d = list.get(0).toString();
        //Nombres de las columnas
        SQL = "describe "+tabla_d;
        stmt = connection.createStatement();
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            nom_columnas.add(rs.getString("Field"));
        }
        //Para almacenar todos los valores del campo target
        //val_col_desicion
        SQL = "select "+columna_desicion+" from "+tabla_d+" group by "+columna_desicion;
        stmt = connection.createStatement();
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            val_col_desicion.add(rs.getString(columna_desicion));
        }

   //----------GUARDAR TODAS LAS VARIABLES DE UNA COLUMNA
        //auxlista = columnas
        //queryactual = distintos valores de las columnas
        SQL = "select count(*) from "+tabla_d;
        stmt = connection.createStatement();
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            numreg = Integer.parseInt(rs.getString("count(*)"));
        }
        Iterator it_nomcol = nom_columnas.iterator();
        Iterator itval, itval1;
        while (it_nomcol.hasNext()){
            //select puertas from cars group by puertas;
            auxlista = it_nomcol.next().toString();
            SQL = "select "+auxlista+" from "+tabla_d+" group by "+auxlista;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);
            //agrega a una lista los distintos valores de una lista
            while (rs.next()) {
                queryactual.add(rs.getString(auxlista));
                
            }
            HashMap hdbvalor_segundo_nivel = new HashMap();
            if(!auxlista.contentEquals(columna_desicion)){
                itval = queryactual.iterator();
                //val_col_desicion
                
                while (itval.hasNext()){
                    //contar la cantidad de ocurrencias con respecto a la columna objetivo
                    HashMap hdbval = new HashMap();
                    auxval = itval.next().toString();
                    itval1 = val_col_desicion.iterator();
                    while (itval1.hasNext()){
                        auxval1 = itval1.next().toString();
                        SQLcol = "select "+auxlista+" from "+tabla_d+" where "+columna_desicion+" = '"+auxval1+"' and "+auxlista+"= '"+auxval+"'";

                        SQLcantnum = "select count("+auxlista+") from "+tabla_d+" where "+columna_desicion+" = '"+auxval1+"' and "+auxlista+"= '"+auxval+"'";
                        stmt = connection.createStatement();
                        rs = stmt.executeQuery(SQLcantnum);
                        while (rs.next()) {
                            numvalcol = Integer.parseInt(rs.getString("count("+auxlista+")"));
                        }
                        hdbval.put(auxval1, numvalcol);
                        auxvalor.add(hdbval);
                        
                   }
                   hdbvalor_segundo_nivel.put(auxval, hdbval);
                }
                queryactual.clear();
                hdb.put(auxlista, hdbvalor_segundo_nivel);
               
            }
            queryactual.clear();
        }
      //Imprime el diccionario actual
         String s, s1;
         Iterator it21;
         for( it21 = hdb.keySet().iterator(); it21.hasNext();) {
                s = it21.next().toString();
                s1 = hdb.get(s).toString();
                System.out.println(s + " : " + s1);
          }
  
         //Para mostrar en pantalla
        SQL = "select * from "+tabla_d;
        stmt = connection.createStatement();
        rs = stmt.executeQuery(SQL);
        it_nomcol = nom_columnas.iterator();
        while (rs.next()) {
            while (it_nomcol.hasNext()){
                lineafila = lineafila + "\t"+ rs.getString(it_nomcol.next().toString());
             }
            lineafila = lineafila.substring(1);
            lineafila = lineafila + "\n      ";
            data.add(lineafila);
            it_nomcol = nom_columnas.iterator();
            lineafila = "";
        }
        
        return hdb;
    }
}
