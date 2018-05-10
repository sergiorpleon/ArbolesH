/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import javax.swing.*;

/**
 *
 * @author casa
 */
public class Buscar extends JPanel {

    public JLabel labArbol;
    public JComboBox combArbol;
    public JLabel labMin;
    public JTextField txtMin;
    public JLabel labMax;
    public JTextField txtMax;
    public JLabel labBuscar;
    public JButton btnBuscarLocal;
    public JButton btnBuscarGlobal;

    public Buscar(DefaultComboBoxModel cm) {
        super();

        GridLayout bl = new GridLayout(4, 5, 10, 10);
        setLayout(bl);

        labArbol = new JLabel("Árbol");

        combArbol = new JComboBox(cm);

        labMin = new JLabel("Llave mínima");

        txtMin = new JTextField("");

        labMax = new JLabel("Llave máxima");

        txtMax = new JTextField("");

        labBuscar = new JLabel("");

        btnBuscarLocal = new JButton("Búsqueda individual");
        btnBuscarGlobal = new JButton("Búsqueda multiple");

        //fila 1
        add(labArbol);
        add(combArbol);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        //fila 2       
        add(labMin);
        add(txtMin);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        //fila 3        
        add(labMax);
        add(txtMax);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        //fila 4        
        add(labBuscar);
        add(btnBuscarLocal);
        add(btnBuscarGlobal);
        add(new JLabel(""));
        add(new JLabel(""));
    }
}
