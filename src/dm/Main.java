/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dm;
import java.sql.*;
import my.gui.DataBase;

import java.sql.SQLException;
import my.gui.entropia;
import java.util.*;
import my.gui.DataBase;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
       entropia e= new entropia();
       HashMap map= new HashMap();
       DataBase.Conectar();
       map=DataBase.ParseoDB();
       DataBase.Desconectar();
       //System.out.println(DataBase.data);

       int[] clase={5,3};
       System.out.println(e.calcular_entropia(8, clase));

    }

}
