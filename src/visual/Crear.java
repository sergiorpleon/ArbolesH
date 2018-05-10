/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import arbolesh.ArrayArboles;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

/**
 *
 * @author casa
 */
public class Crear extends JPanel {
    public JLabel labid;
    public JTextField txtid;
    public JLabel laborden;
    public JTextField txtorden;
    public JButton btnCrear;
    
    public JLabel labArbol;
    public JComboBox combArbol;
    public JButton btnEliminar;
    
    public DefaultComboBoxModel padre;
    public DefaultComboBoxModel hijo;
    public ArrayArboles aa;
    public JComboBox j1;
    public JComboBox j2;
    public JButton btnAnidar;

    public Crear(DefaultComboBoxModel cm, DefaultComboBoxModel hijos, DefaultComboBoxModel padres, ArrayArboles ab) {
        super();

        GridLayout bl = new GridLayout(4, 6, 10, 10);
        setLayout(bl);

        labid = new JLabel("ID");
        labid.setHorizontalAlignment(JLabel.RIGHT);

        txtid = new JTextField("");

        laborden = new JLabel("Dimensión");
        laborden.setHorizontalAlignment(JLabel.RIGHT);


        txtorden = new JTextField("");

        btnCrear = new JButton("Crear");

        labArbol = new JLabel("Árbol");
        labArbol.setHorizontalAlignment(JLabel.RIGHT);

        combArbol = new JComboBox(cm);

        btnEliminar = new JButton("Eliminar");


        this.aa = ab;

        JLabel l1 = new JLabel("Padre");
        l1.setHorizontalAlignment(JLabel.RIGHT);
        JLabel l2 = new JLabel("Hijo");
        l2.setHorizontalAlignment(JLabel.RIGHT);


        this.padre = padres;
        actualizarPadres();
        j1 = new JComboBox(padres);

        this.hijo = hijos;
        j2 = new JComboBox(hijos);
        actualizarHijos();


        j1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizarHijos();
            }
        });

        btnAnidar = new JButton("Anidar");

        //fila 1
        add(labid);
        add(txtid);
        add(labArbol);
        add(combArbol);
        add(l1);
        add(j1);
        //fila 2
        add(laborden);
        add(txtorden);
        add(new JLabel(""));
        add(new JLabel(""));
        add(l2);
        add(j2);
        //fila 3
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        //fila 4
        add(new JLabel(""));
        add(btnCrear);
        add(new JLabel(""));
        add(btnEliminar);
        add(new JLabel(""));
        add(btnAnidar);
    }

    public void actualizarPadres() {
        padre.removeAllElements();
        for (int i = 0; i < aa.array.size(); i++) {
            padre.addElement(i + 1);
        }
    }

    public void actualizarHijos() {
        int valor = (int) j1.getSelectedIndex();
        hijo.removeAllElements();
        for (int i = 0; i < aa.array.size(); i++) {
            if (i != valor && (int)aa.padres.get(i) == -1) {
                hijo.addElement(i + 1);
            }
        }
//        if (valor != -1) {
//            while ((int)aa.padres.get(valor) != -1) {
//                hijo.removeElement(valor);
//                valor = (int) aa.padres.get(valor);
//            }
//            hijo.removeElement(valor);
//        }
    }
}
