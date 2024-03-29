
package my.gui;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
//import java.lang.Math;


public class GraficoArbol extends javax.swing.JFrame {

    public static int coordX = 0, coordY = 120, altoNodo = 50, anchoNodo = 100;
    public static int TamInicial, Tamanho;
    public static int anteriorX = 0, anteriorY = 0, textoX = 0, textoY = 0;
    public static final Color celeste = new Color(153, 153, 255);
    public static final Color black = new Color(0, 0, 0);
    public int cantNodos = 1, indexNodo=0, indexArco=0, indexPadres=0, limite=0;
    public static int index=0, bandera=0,cont=0, posAux=0;
    public String aux;
    public static List<String> arbolito = new ArrayList<String>();
    public static List<String> arbolitoCopia = new ArrayList<String>();
    public static List<String> nivel = new ArrayList<String>();
    public static List<String> nivelAnterior = new ArrayList<String>();
    public static List<String> ordenPadres = new ArrayList<String>();
    public List<Integer> posicion = new ArrayList<Integer>();

    /** Creates new form GraficoArbol */
    public GraficoArbol(ArrayList arbolgrafico) {
        initComponents();
	arbolito=arbolgrafico;
        TamInicial = PanelGrafico.getWidth();
        Tamanho = TamInicial;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        PanelGrafico = new javax.swing.JScrollPane();

        setTitle("Arbol Generado");
        setAlwaysOnTop(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Arbol generado mediante el Algoritmo J48");

        PanelGrafico.setAutoscrolls(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(PanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 1165, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

       

    public GraficoArbol(JScrollPane PanelGrafico, JLabel jLabel1) {
        this.PanelGrafico = PanelGrafico;
        this.jLabel1 = jLabel1;
    }

	public static ArrayList parseaAbol(ArrayList prueba1){
		ArrayList salida = new ArrayList();
		ArrayList<Integer> ar  = new ArrayList<Integer>();
		ArrayList<Integer> as  = new ArrayList<Integer>();
		ArrayList<Integer> nu  = new ArrayList<Integer>();
		String padre="";
		int nivel=0, limite=0;
		int num, b=0;
		salida.add(prueba1.get(0));
		salida.add(";");
		//padre=prueba1.get(0).toString();
		//salida.add(prueba1.get(0));
		int i=0;
		while(i<prueba1.size()){
			try{

				num = Integer.parseInt(prueba1.get(i).toString());
				if(num>limite){limite=num;}
                                if (prueba1.get(i - 1).equals("@") || prueba1.get(i - 1).equals("*"))
                                    nu.add(i);
			}catch(Exception e){

			}
			if(prueba1.get(i)=="@"){
				ar.add(i);
			}
			if(prueba1.get(i)=="*"){
				as.add(i);
			}
			i++;
		}
		i=0;
		num=0;
		while(num<=limite){
			while(i<nu.size()){
				nivel=Integer.parseInt(prueba1.get(nu.get(i)).toString());
				if(num==nivel){
					if(b==0){
						padre=prueba1.get(nu.get(i)-2).toString();
						b=1;
					}
					salida.add(padre);
					salida.add(prueba1.get(nu.get(i)+2));
					salida.add(prueba1.get(nu.get(i)+1));
					salida.add("-");
				}
				if(nivel==num-1){
					b=0;
				}
				i++;
			}
			i=salida.size();
			salida.remove(i-1);
			salida.add(";");
			num++;
			i=0;
		}
		i=salida.size();
		salida.remove(i-1);
		salida.add("#");

		return salida;
	}

    @Override public void paint(Graphics g) {

        super.paint(g);

        anteriorX = Tamanho / 2; //PanelGrafico.getWidth() / 2;
        anteriorY = 140 + altoNodo;

        coordX = Tamanho / (cantNodos + 1); //PanelGrafico.getWidth() / (cantNodos + 1);
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
         * quedaria una lista asi: {"A", ";", "A", "B", "arco1", "A", "C", "arco2",";", "C", "D", "arco3", "#"}
         */
         if (arbolito.size() == 0){
             for(int i=0; i<arbolitoCopia.size(); i++)
                 arbolito.add(arbolitoCopia.get(i));
             arbolitoCopia.clear();
         }
         System.out.println("Arbolito: " + arbolito);

        arbolitoCopia.add(arbolito.get(0));
         //posicion.add(0);
         aux = arbolito.get(0);
         while (arbolito.isEmpty() == false){
         
            if (aux.compareTo(";") == 0 || aux.compareTo("#") == 0){

                cont++;
                if (arbolitoCopia.contains(";") == true && posicion.contains(arbolitoCopia.lastIndexOf(";")) == false){
                    posicion.add(arbolitoCopia.lastIndexOf(";"));
                }
                if (arbolitoCopia.contains("#") == true  && posicion.contains(arbolitoCopia.lastIndexOf("#")) == false){
                    posicion.add(arbolitoCopia.lastIndexOf("#"));
                }

                if (cont > 2){
                    //nivelAnterior = arbolito.subList(posicion.get(cont-2), posicion.get(cont-1));
                    for(int p = posicion.get(cont-3) + 1; p < posicion.get(cont-2); p++){
                        if (arbolitoCopia.get(p).equals("-") == false)
                            nivelAnterior.add(arbolitoCopia.get(p));
                    }
                } else if (cont == 1 || cont == 2){
                    if (aux.compareTo(";") == 0){
                        posAux = arbolitoCopia.indexOf(";");
                    } else {
                        posAux = arbolitoCopia.indexOf("#");
                    }
                    for (int n=0; n<posAux; n++){
                        nivelAnterior.add(arbolitoCopia.get(n));
                    }
                }
                

                cantNodos++;
                limite = nivelAnterior.size() / cantNodos;
                //buscamos el orden de los padres, para poder realizar los arcos a los hijos
                for (int m = 0; m < nivelAnterior.size(); m++){
                    for (int n=0; n <= limite; n++){
                        if (nivelAnterior.size() == 1){
                            indexPadres = 3 * n;
                            if (m == indexPadres){
                                ordenPadres.add(nivelAnterior.get(indexPadres));
                            }
                        } else {
                            indexPadres = 3 * n + 1;
                            if (m == indexPadres){
                                ordenPadres.add(nivelAnterior.get(indexPadres));
                            }
                        }
                    }
                }
                System.out.println("Orden Padres: " + ordenPadres + "\nCantidad de Nodos: " + cantNodos);
                
                coordX = Tamanho / (cantNodos + 1); //PanelGrafico.getWidth() / (cantNodos + 1);
                //dibujar nivel//////////////////
                for(int i = 0; i < cantNodos; i++){
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
                        //anteriorX = (tamanho del panel / (cantidad de padres+1)) * (posicion del padre + i)
                        anteriorX = (PanelGrafico.getWidth() / (ordenPadres.size() + 1)) * (ordenPadres.lastIndexOf(nivel.get(3 * i)) + 1);

                        g.drawLine(anteriorX + anchoNodo / 2, anteriorY, coordX + anchoNodo / 2, coordY);
                        textoX = (( anteriorX + anchoNodo / 2 ) + ( coordX + anchoNodo )) / 2;
                        textoY = ( anteriorY + coordY ) / 2;
                        indexArco = 3 * i + 2;
                        g.drawString(nivel.get(indexArco), textoX, textoY);
                    }
                    coordX = coordX + Tamanho / (cantNodos + 1); //PanelGrafico.getWidth() / (cantNodos + 1);
                }
                anteriorY = coordY + altoNodo;
                coordY = coordY + 140;
                ///////////////////////////////
                nivel.clear();
                nivelAnterior.clear();
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
            if (arbolito.size() != 0){
                arbolitoCopia.add(arbolito.get(0));
                aux = arbolito.get(0);
            }else{
                coordX = 0;
                coordY = 120;
                bandera = 0;
                arbolito.clear();
                nivelAnterior.clear();
                nivel.clear();
                posicion.clear();
                cont=0;
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new GraficoArbol().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane PanelGrafico;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
