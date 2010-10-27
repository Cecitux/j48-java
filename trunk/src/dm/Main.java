/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dm;
import java.sql.*;
import my.gui.DataBase;
/**
 *
 * @author Adriana
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws SQLException {
        // TODO code application logic here
        DataBase.Conectar();
        DataBase.ParseoDB();
        DataBase.Desconectar();
    }

}
