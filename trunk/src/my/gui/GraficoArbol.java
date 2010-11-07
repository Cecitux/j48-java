
package my.gui;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
//import java.lang.Math;


public class GraficoArbol extends javax.swing.JFrame {

    //public static List<String> arbol = new ArrayList<String>();


    /** Creates new form GraficoArbol */
    public GraficoArbol() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelGrafico = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arbol Generado");
        setResizable(false);

        PanelGrafico.setBackground(new java.awt.Color(255, 255, 255));
        PanelGrafico.setAutoscrolls(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setText("Arbol generado mediante el Algoritmo J48");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(PanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    
    public static int coordX = 0, coordY = 150, altoNodo = 50, anchoNodo = 70;
    public static int anteriorX = 0, anteriorY = 0, textoX = 0, textoY = 0;
    public static final Color celeste = new Color(153, 153, 255);
    public static final Color black = new Color(0, 0, 0);
    public int cantNodos = 1, indexNodo=0, indexArco=0, indexPadres=0, limite=0;
    public static int index=0, bandera=0;
    public String aux;
    public static List<String> arbolito = new ArrayList<String>();
    public static List<String> arbolitoCopia = new ArrayList<String>();
    public static List<String> nivel = new ArrayList<String>();
    public static List<String> ordenPadres = new ArrayList<String>();

    

    public GraficoArbol(JScrollPane PanelGrafico, JLabel jLabel1) {
        this.PanelGrafico = PanelGrafico;
        this.jLabel1 = jLabel1;
    }


    @Override public void paint(Graphics g) {


        super.paint(g);

        anteriorX = PanelGrafico.getWidth() / 2;
        anteriorY = 150 + altoNodo;

        coordX = PanelGrafico.getWidth() / (cantNodos + 1);
        cantNodos = 0;

        /* FORMATO DEL ARBOL: Una lista de strings con el siguiente formato:
         * Separacion de niveles: ";"
         * Formato de datos de Hijo: "Padre", "Hijo", "arco"
         * Separacion de hijos: "-"
         * Fin del Arbol: "#"
         *
         * Ejemplo de formato: {Raiz;Padre*Hijo1-arco1, Padre*Hijo2-arco2;....}
         *
         *         | A |
         *    arco1    arco2
         *    | B |    | C |
         *              arco3
         *              | D |
         * donde: A es la raiz y es padre de B y C con los arcos 1 y 2 respectivamente; y a su vez C es padre de D con el arco 3.
         * quedaria una lista asi: {"A", ";", "A", "B", "arco1", "A", "C", "arco2", "C", "D", "arco3", "#"}
         */


         /* FALTA HACER FUNCIONAR:
          * Hacer corresponder con el padre (pixeles)
          */
         
         arbolito.add("A"); arbolito.add(";");
         arbolito.add("A"); arbolito.add("B"); arbolito.add("arco1"); arbolito.add("-");
         arbolito.add("A"); arbolito.add("C"); arbolito.add("arco2"); arbolito.add(";");
         arbolito.add("B"); arbolito.add("D"); arbolito.add("arco3"); arbolito.add("-");
         arbolito.add("B"); arbolito.add("E"); arbolito.add("arco4"); arbolito.add("-");
         arbolito.add("C"); arbolito.add("F"); arbolito.add("arco5"); arbolito.add("-");
         arbolito.add("C"); arbolito.add("G"); arbolito.add("arco6"); arbolito.add("#");

         arbolitoCopia.add(arbolito.get(0));
         aux = arbolito.get(0);
         while (arbolito.isEmpty() == false){
            if (aux.compareTo(";") == 0 || aux.compareTo("#") == 0){
                cantNodos++;
                limite = nivel.size() / cantNodos - 1;
                System.out.println("\nEncontrado el nivel con nodos: " + nivel);
                //buscamos el orden de los padres, para poder realizar los arcos a los hijos
                for (int m = 0; m < nivel.size(); m++){
                    for (int n=0; n <= limite; n++){
                        indexPadres = 3 * n;
                        if (m == indexPadres){
                            if (ordenPadres.contains(nivel.get(indexPadres)) == false){
                                ordenPadres.add(nivel.get(indexPadres));
                            }
                        }
                    }
                }
                System.out.println("Orden de Padres: " + ordenPadres + ", CantNodos: " + cantNodos);
                
                coordX = PanelGrafico.getWidth() / (cantNodos + 1);
                //dibujar nivel//////////////////
                for(int i = 0; i < cantNodos; i++){
                    System.out.println("i = " + i);
                    g.setColor(celeste);
                    g.fillArc(coordX,coordY,anchoNodo,altoNodo,0,360);
                    g.setColor(black);
                    if (cantNodos == 1 && bandera == 0){ //nodo raiz
                        g.drawString(nivel.get(i), coordX+20, coordY+30);
                        anteriorX = coordX;
                        bandera = 1;
                    } else { //resto del arbol
                        indexNodo = 3 * i + 1;
                        g.drawString(nivel.get(indexNodo), coordX+20, coordY+30);

                        //correspondencia con el padre
                        System.out.println("Cantidad de Padres en nivel anterior: " + ordenPadres.size());
                        System.out.println("Posicion X de padre segun orden: " + ((PanelGrafico.getWidth() / (ordenPadres.size() + 1)) * (ordenPadres.lastIndexOf(nivel.get(3 * i)) + 1)) );
                        //anteriorX = (tamanho del panel / (cantidad de padres+1)) * (posicion del padre + i)
                        anteriorX = (PanelGrafico.getWidth() / (ordenPadres.size() + 1)) * (ordenPadres.lastIndexOf(nivel.get(3 * i)) + 1);

                        System.out.println("\n");

                        g.drawLine(anteriorX + anchoNodo / 2, anteriorY, coordX + anchoNodo / 2, coordY);
                        textoX = coordX;
                        textoY = anteriorY + 70;
                        indexArco = 3 * i + 2;
                        g.drawString(nivel.get(indexArco), textoX, textoY);
                    }
                    coordX = coordX + PanelGrafico.getWidth() / (cantNodos + 1);
                }
                anteriorY = coordY + altoNodo;
                coordY = coordY + 150;
                ///////////////////////////////
                nivel.clear();
                ordenPadres.clear();
                index = 0;
                cantNodos = 0;
            }
            if (aux.compareTo(";") != 0){
                if (aux.compareTo("-") == 0){
                    cantNodos++;
                } else {
                    nivel.add(arbolito.get(0));
                    index++;
                }
            }
            arbolito.remove(0);
            arbolitoCopia.add(arbolito.get(0));
            aux = arbolito.get(0);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraficoArbol().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane PanelGrafico;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
