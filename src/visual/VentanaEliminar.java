/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import arbolesh.ArrayArboles;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;

/**
 *
 * @author casa
 */
public class VentanaEliminar<K> extends JDialog {

    public JList list;
    Integer llave;
    public JButton btn;
    ArrayArboles newab;
    int newidelim;
    Integer newllaveelim;
    Lienzo newlienzo;

    public VentanaEliminar(LinkedList l, Integer llave, ArrayArboles ab, int idelim, Integer llaveelim, Lienzo lienzo) {
        super();
        
         newab = ab;
        newidelim= idelim;
        newllaveelim=llaveelim;
        newlienzo = lienzo;
        
        setTitle("Valores");
        list = new JList(l.toArray());
        list.setSelectionMode(2);
        list.setPreferredSize(new Dimension(5 * Global.digitos, l.size() * 18));
        JScrollPane jp = new JScrollPane();
        jp.setViewportView(list);

        btn = new JButton("Eliminar");

        Box b = Box.createVerticalBox();
        b.add(jp);
        b.add(btn);

        getContentPane().add(b);

        setLocationRelativeTo(getParent());
        setModalityType(ModalityType.APPLICATION_MODAL);
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        pack();

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("elrm " + list.getSelectedValuesList());
                newab.eliminarNodos(newidelim, newllaveelim, list.getSelectedValuesList());
                newlienzo.pintar(newab);
                setVisible(false);
            }
        });
    }
}
