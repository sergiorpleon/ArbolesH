/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author casa
 */
public class Eliminar extends JPanel {

    public JLabel labArbol;
    public JComboBox combArbol;
    public JLabel labllave;
    public JTextField txtllave;
    public JLabel labValor;
    //public JTextField txtValor;
    //public JLabel labEliminar;
    public JButton btnEliminarValores;
    public JButton btnEliminarNodo;

    public Eliminar(DefaultComboBoxModel cm) {
        super();
        GridLayout bl = new GridLayout(4, 5, 10, 10);
        setLayout(bl);

        labArbol = new JLabel("Árbol");

        combArbol = new JComboBox(cm);

        labllave = new JLabel("Llave");

        txtllave = new JTextField("");

        labValor = new JLabel("Valor");

        btnEliminarValores = new JButton("Eliminar valores");

        btnEliminarNodo = new JButton("Eliminar llave");
        //fila 1
        add(labArbol);
        add(combArbol);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        //fila 2
        add(labllave);
        add(txtllave);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        //fila 3
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        //fila 4
        add(new JLabel(""));
        add(btnEliminarValores);
        add(btnEliminarNodo);
        add(new JLabel(""));
        add(new JLabel(""));
    }
}
