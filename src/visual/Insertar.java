/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import arbolesh.ArrayArboles;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author casa
 */
public class Insertar extends JPanel{
    public JLabel labArbol;
    public JComboBox combArbol;
    public JLabel labllave;
    public JTextField txtllave;
    public JLabel labvalor;
    public JTextField txtvalor;
    public JButton btnInsertar;
    public Insertar(DefaultComboBoxModel cm) {
        super();

        GridLayout bl = new GridLayout(4, 5, 10, 10);
        setLayout(bl);

        labArbol = new JLabel("Árbol");

        combArbol = new JComboBox(cm);

        labllave = new JLabel("Llave");

        txtllave = new JTextField("");

        labvalor = new JLabel("Valor");

        txtvalor = new JTextField("");

        btnInsertar = new JButton("Insertar");
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
        add(labvalor);
        add(txtvalor);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));
        //fila 4
        add(new JLabel(""));
        add(btnInsertar);
        add(new JLabel(""));
        add(new JLabel(""));
        add(new JLabel(""));

    }

    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    public void setBtnInsertar(JButton btnInsertar) {
        this.btnInsertar = btnInsertar;
    }

    public JComboBox getCombArbol() {
        return combArbol;
    }

    public void setCombArbol(JComboBox combArbol) {
        this.combArbol = combArbol;
    }

    public JLabel getLabArbol() {
        return labArbol;
    }

    public void setLabArbol(JLabel labArbol) {
        this.labArbol = labArbol;
    }

    public JLabel getLabllave() {
        return labllave;
    }

    public void setLabllave(JLabel labllave) {
        this.labllave = labllave;
    }

    public JLabel getLabvalor() {
        return labvalor;
    }

    public void setLabvalor(JLabel labvalor) {
        this.labvalor = labvalor;
    }

    public JTextField getTxtllave() {
        return txtllave;
    }

    public void setTxtllave(JTextField txtllave) {
        this.txtllave = txtllave;
    }

    public JTextField getTxtvalor() {
        return txtvalor;
    }

    public void setTxtvalor(JTextField txtvalor) {
        this.txtvalor = txtvalor;
    }
    
    
}
