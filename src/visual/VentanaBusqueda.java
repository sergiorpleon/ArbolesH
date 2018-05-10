/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.*;

/**
 *
 * @author casa
 */
public class VentanaBusqueda extends JDialog{
    //Jlist para mostrar el resultado de la busqueda y un boton aceptar
    JList list;
    
    public VentanaBusqueda(Set l){
        super();
        setTitle("Resultado");
        Object[] ordenado = l.toArray();
        Arrays.sort(ordenado);
        list = new JList(ordenado);
        JButton btn = new JButton("Regresar");
        
        list.setPreferredSize(new Dimension(5*Global.digitos, l.size()*18));
        JScrollPane jp = new JScrollPane();
        jp.setViewportView(list);
        
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
                setVisible(false);
            }
        });
    }
}
