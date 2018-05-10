/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import arbolesh.ArrayArboles;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author casa
 */
public class PanelInferior extends JPanel {
    public JRadioButton btnchico;
    public JRadioButton btnmedio;
    public JRadioButton btngrande;
    public JButton btnEjemplo;
    public JLabel labLogo;

    public PanelInferior() {
        super();
      
        setLayout(new GridLayout(1, 3));
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        add(p1);
        add(p2);
        add(p3);
        p1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p3.setLayout(new FlowLayout(FlowLayout.RIGHT, 40, 0));

        ButtonGroup bg = new ButtonGroup();
        bg.add(btnchico);
        btnchico = new JRadioButton("chico");
        btnmedio = new JRadioButton("medio");
        btngrande = new JRadioButton("grande");
        bg.add(btnchico);
        bg.add(btnmedio);
        bg.add(btngrande);

        btnEjemplo = new JButton("Ejemplo");
        labLogo = new JLabel("H-Tree");

        btnmedio.setSelected(true);

        p1.add(btnchico);
        p1.add(btnmedio);
        p1.add(btngrande);


        p3.add(btnEjemplo);
        p3.add(labLogo);
    }
}
