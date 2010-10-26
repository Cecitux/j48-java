/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.gui;
import java.sql.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import weka.core.Instances;
import weka.experiment.InstanceQuery;


public class DataBase {
    //Variables de Lore para la BD
    public static Connection connection;
    InstanceQuery query;
    //Conectar a la BD
    public static void Conectar(){
        String cadenaconexion = "";
        String prueba = "jdbc:mysql://localhost/com?" + "user=root&password=";
        try {
            /*Class.forName("com.mysql.jdbc.Driver");
            //String connectionUrl = "jdbc:mysql://localhost/mysql?" +"user=root&password=123456";
            connection = DriverManager.getConnection(prueba);
            System.out.println("Conectado");*/

            //nueva conexion
            Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(prueba);
		DatabaseMetaData dbmd = connection.getMetaData();
		System.out.println("Connection to " + dbmd.getDatabaseProductName()
				+ " " + dbmd.getDatabaseProductVersion() + " successful.\n");

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
}
