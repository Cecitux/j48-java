/*
 * Clase Log, donde se definen los metodos de formateo de la informacion
 * y se almanenan los datos iniciales.
 */

package my.gui;
import java.awt.Panel;
import javax.swing.*;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.plaf.PanelUI;

public class Log {

    public static List<String> datosLog = new ArrayList<String>();

    public Log(){
            String encabezado = "\n  Implementación del Algoritmo J48\n" +
            "  Adriana Aranda (54210), Fernando Cardozo (51300), Lorena Figueredo (51160)\n" +
            "  Universidad Católica - Nuestra Señora de la Asunción\n" +
            "  Asunción, Paraguay  -  2010\n\n  " +
            new Date() + "\t   Inicio de la Ejecucion\n";
            datosLog.add(encabezado);
    }

    private static String formatoDato(){
            Date fechaHora = new java.util.Date();
            SimpleDateFormat formato = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
            return formato.format(fechaHora) + ": ";
    }

    public static void addMsn(String data){
            datosLog.add("\n" + formatoDato() + data);
    }
}
