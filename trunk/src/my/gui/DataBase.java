/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import my.gui.gui;
import java.sql.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;




public class DataBase {
    //Variables de Lore para la BD
    public static Connection connection;
    //Conectar a la BD
    public static void Conectar(){
        String cadenaconexion = "";
        //String prueba = "jdbc:mysql://localhost/"+gui.nombrebd+"?" + "user="+gui.usuario+"&password="+gui.password;
        String prueba="jdbc:mysql://localhost:3306/com?" +"user=root&password=";
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
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al desconectar");
        }
    }

    public static Hashtable ParseoDB() throws SQLException{
        //String connectionUrl = "jdbc:mysql://localhost:3306/com?" + "user=root&password=";
        Hashtable hdb = new Hashtable();
        Statement stmt = null;
        ResultSet rs = null;

        ArrayList list = new ArrayList();
        ArrayList nom_columnas = new ArrayList();
        String tabla_d = "";
        int numreg = 0;
        String auxlista ="";
        //SQL query command
        String SQL = "show tables";
        stmt = connection.createStatement();
        rs = stmt.executeQuery(SQL);
        
        
        while (rs.next()) {
            //System.out.println(rs.getString("Tables_in_com"));
            list.add(rs.getString("Tables_in_com"));
        }
        System.out.println(list.get(0).toString());
        tabla_d = list.get(0).toString();
        //Nombres de las columnas
        SQL = "describe "+tabla_d;
        stmt = connection.createStatement();
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            System.out.println(rs.getString("Field"));
            nom_columnas.add(rs.getString("Field"));
        }
        SQL = "select count(*) from "+tabla_d;
        stmt = connection.createStatement();
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            //System.out.println(rs.getString("count(*)"));
            numreg = Integer.parseInt(rs.getString("count(*)"));
        }
        System.out.println(numreg);
        Iterator it = nom_columnas.iterator();
        while (it.hasNext()){
            //select puertas from cars group by puertas;
            auxlista = it.next().toString();
            SQL = "select "+auxlista+" from "+tabla_d+" group by "+auxlista;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
            //System.out.println(rs.getString("count(*)"));
                //numreg = Integer.parseInt(rs.getString("count(*)"));
            }
            System.out.println(SQL);
            
         }

        /*InstanceQuery query = new InstanceQuery();
        //query.setUsername(gui.usuario);
	//query.setPassword(gui.password);
        query.setUsername("root");
	query.setPassword("");
	//query.setQuery("show tables");
        query.setQuery("SELECT * FROM cars");

        Instances data = query.retrieveInstances();
        System.out.println(data);
        System.out.println("hola");*/

        /*try {
            connection.close();
            System.out.println("Desconectado");
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error de query");
        }*/
        return hdb;
    }
}
