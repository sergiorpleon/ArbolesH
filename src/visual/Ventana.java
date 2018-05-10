/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import arbolesh.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.*;

/**
 *
 * @author casa
 */
public class Ventana extends JFrame {

    public ArrayArboles ab;
    public ArrayArboles abAnterior;
    //private LinkedList idArboles = new LinkedList();
    public DefaultComboBoxModel cm;
    DefaultComboBoxModel padres = new DefaultComboBoxModel();
    DefaultComboBoxModel hijos = new DefaultComboBoxModel();
    private JTabbedPane tp;
    private Crear crear;
    private Insertar insertar;
    private Eliminar eliminar;
    private Buscar buscar;
    private Lienzo lienzo;
    private Lienzo lienzoAnterior;
    private int idelim;
    private int llaveelim;
    private VentanaEliminar ve;
    private PanelInferior pi;

    public Ventana() {
        super();
        setTitle("ArbolesH");

        ab = new ArrayArboles();
        cm = new DefaultComboBoxModel();

        this.setSize(3000, 3000);
        this.setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crear = new Crear(cm, hijos, padres, ab);
        insertar = new Insertar(cm);
        eliminar = new Eliminar(cm);
        buscar = new Buscar(cm);
        pi = new PanelInferior();

        lienzoAnterior = new Lienzo();
        lienzo = new Lienzo();

        lienzo.setPreferredSize(new Dimension(10000, 10000));
        JScrollPane jp = new JScrollPane();
        jp.setViewportView(lienzo);

        tp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tp.add("Crear", crear);
        tp.add("Insertar", insertar);
        tp.add("Eliminar", eliminar);
        tp.add("Buscar", buscar);

        add("North", tp);
        add("Center", jp);
        add("South", pi);

        //jp.getAccessibleContext().setAccessibleParent(jp);
        crear.btnCrear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    lienzoAnterior.pintar(ab);
                    //insertar valor en arbol
                    String id = crear.txtid.getText();
                    int orden = Integer.parseInt(crear.txtorden.getText());
                    if (orden < 1) {
                        JOptionPane.showMessageDialog(getParent(), "El orden tiene que ser superior a 0", "Error", 1);
                    } else {
                        //String valor =insertar.txtvalor.getText();
                        ArbolH h = new ArbolH(orden, id, -1000, 1000);

                        ab.insertar(h);
                        //actualizar combo

                        cm.removeAllElements();
                        for (int i = 0; i < ab.array.size(); i++) {
                            cm.addElement(i + 1);

                        }
                        crear.actualizarPadres();
                        //crear.actualizarHijos();
                        lienzo.pintar(ab);
                        // crear.combArbol = new JComboBox(cm);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(getParent(), "Valores de entrada no válido", "Error", 1);
                }
            }
        });

        crear.btnEliminar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    lienzoAnterior.pintar(ab);
                    //insertar valor en arbol
                    int id = (int) crear.combArbol.getSelectedItem() - 1;
                    ab.eliminar(id);
                    //actualizar combo
                    cm.removeAllElements();
                    for (int i = 0; i < ab.array.size(); i++) {
                        cm.addElement(i + 1);

                    }
                    //crear.actualizarHijos();
                    crear.actualizarPadres();
                    lienzo.pintar(ab);

                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(getParent(), "Eliminación no válida", "Error", 1);
                }
            }
        });

        crear.btnAnidar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //try {
                boolean puede = true;
                int valpadre = (int) crear.j1.getSelectedItem() - 1;
                int valhijo = (int) crear.j2.getSelectedItem() - 1;
                System.out.println(valhijo + " hij-pad " + valpadre);
                System.out.println("arr " + ab.padres.toString());
                while (puede) {

                    if ((int) ab.padres.get(valpadre) == -1) {
                        break;
                    }

                    if ((int) ab.padres.get(valpadre) == valhijo) {

                        puede = false;
                    } else {
                        valpadre = (int) ab.padres.get(valpadre);
                    }

                }

                System.out.println(valhijo + " hij-pad " + valpadre);
                if (puede) {
                    lienzoAnterior.pintar(ab);

                    //insertar valor en arbol
                    ab.anidar((Integer) crear.j1.getSelectedItem() - 1, (Integer) crear.j2.getSelectedItem() - 1);
                    lienzo.pintar(ab);
                }else{
                //} catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(getParent(), "Anidación no válida", "Error", 1);
                //}
                }
            }
        });

        insertar.btnInsertar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    lienzoAnterior.pintar(ab);

                    //insertar valor en arbol
                    int id = (int) insertar.combArbol.getSelectedItem() - 1;
                    int llave = Integer.parseInt(insertar.txtllave.getText());
                    int valor = Integer.parseInt(insertar.txtvalor.getText());

                    ab.insertarNodos(id, llave, valor);
                    lienzo.pintar(ab);
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(getParent(), "Árbol no válido", "Error", 1);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(getParent(), "Valores de entrada no válido", "Error", 1);
                }
            }
        });

        eliminar.btnEliminarNodo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    lienzoAnterior.pintar(ab);

                    //insertar valor en arbol
                    int id = (int) eliminar.combArbol.getSelectedItem() - 1;
                    int ellave = Integer.parseInt(eliminar.txtllave.getText());
                    //sobra
                    //int evalor = Integer.parseInt(eliminar.getText());
                    Set slis = (Set) ab.buscarNodo(id, ellave, ellave);
                    LinkedList lise = new LinkedList(slis);

                    //lise.add(evalor);
                    ab.eliminarNodos(id, ellave, lise);
                    lienzo.pintar(ab);
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(getParent(), "Árbol no válido", "Error", 1);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(getParent(), "Valores de entrada no válido", "Error", 1);
                }
            }
        });

        eliminar.btnEliminarValores.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    lienzoAnterior.pintar(ab);

                    //insertar valor en arbol
                    idelim = (int) eliminar.combArbol.getSelectedItem() - 1;
                    llaveelim = Integer.parseInt(eliminar.txtllave.getText());
                    //sobra
                    //int evalor = Integer.parseInt(eliminar.getText());
                    LinkedList lise; //= new LinkedList();
                    lise = new LinkedList(ab.buscarNodo(idelim, llaveelim, llaveelim));
                    //lise.add(evalor);
                    ve = new VentanaEliminar(lise, llaveelim, ab, idelim, llaveelim, lienzo);
                    ve.show();
//                    ve.btn.addActionListener(new ActionListener() {
//
//                        @Override
//                        public void actionPerformed(ActionEvent ae) {
//                            System.out.println("elrm " + ve.list.getSelectedValuesList());
//                            ab.eliminarNodos(idelim, llaveelim, ve.list.getSelectedValuesList());
//                            lienzo.pintar(ab);
//                            ve.setVisible(false);
//                        }
//                    });
                    //ab.eliminarNodos(id, ellave, lise);
                    //lienzo.pintar(ab);
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(getParent(), "Árbol no válido", "Error", 1);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(getParent(), "Valores de entrada no válido", "Error", 1);
                }
            }
        });

        buscar.btnBuscarLocal.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        try {
                            //insertar valor en arbol
                            int id = (int) buscar.combArbol.getSelectedItem() - 1;
                            int minima = Integer.parseInt(buscar.txtMin.getText());
                            //sobra
                            int maxima = Integer.parseInt(buscar.txtMax.getText());

                            Set l = (HashSet) ab.buscarNodo(id, minima, maxima);
                            VentanaBusqueda vb = new VentanaBusqueda(l);
                            vb.show();
                            lienzo.pintar(ab);
                        } catch (NullPointerException e) {
                            JOptionPane.showMessageDialog(getParent(), "Árbol no válido", "Error", 1);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(getParent(), "Formato de entrada no válido", "Error", 1);
                        }
                    }
                });

        buscar.btnBuscarGlobal.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        try {
                            System.out.println("a " + ((ArbolH) ab.array.get(0)).minArb);
                            PunteroL lpun = (PunteroL) ((ArbolH) ab.array.get(0)).getRaiz().getPunL().get(0);
                            System.out.println("lp minpunt " + lpun.getMin());
                            System.out.println("lp minimo raiz " + ((ArbolH) ab.array.get(1)).minimo(((ArbolH) ab.array.get(1)).getRaiz()));
                            System.out.println("lp minimo Arb " + ((ArbolH) ab.array.get(1)).minArb);

                            //insertar valor en arbol
                            int id = (int) buscar.combArbol.getSelectedItem() - 1;
                            int minima = Integer.parseInt(buscar.txtMin.getText());
                            //sobra
                            int maxima = Integer.parseInt(buscar.txtMax.getText());

                            Set l = (HashSet) ab.buscarNodos(id, minima, maxima, true);
                            VentanaBusqueda vb = new VentanaBusqueda(l);
                            vb.show();
                            lienzo.pintar(ab);
                        } catch (NullPointerException e) {
                            JOptionPane.showMessageDialog(getParent(), "Árbol no válido", "Error", 1);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(getParent(), "Formato de entrada no válido", "Error", 1);
                        }
                    }
                });
        //Eventos panel inferior
        pi.btnchico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Global.letra = 10;
                Global.altoNodo = 12;
                Global.largoPuntero = 5;
                Global.largoValor = 20;

                Global.horizontalEspacio = 5;
                Global.verticalEspacio = 50;

                Global.distanciaArboles = 30;
                lienzo.pintar(ab);
            }
        });
        pi.btnmedio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Global.letra = 12;
                Global.altoNodo = 20;
                Global.largoPuntero = 10;
                Global.largoValor = 40;

                Global.horizontalEspacio = 10;
                Global.verticalEspacio = 100;

                Global.distanciaArboles = 70;
                lienzo.pintar(ab);
            }
        });

        pi.btngrande.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Global.letra = 25;
                Global.altoNodo = 40;
                Global.largoPuntero = 20;
                Global.largoValor = 80;

                Global.horizontalEspacio = 10;
                Global.verticalEspacio = 200;

                Global.distanciaArboles = 150;

                lienzo.pintar(ab);
            }
        });

        pi.btnEjemplo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int i = ab.array.size() - 1; i >= 0; i--) {
                    ab.eliminar(i);
                }

                //arbol 1
                ArbolH<Integer, Integer> a = new ArbolH<Integer, Integer>(-100, 100);
                a.setM(2);
                NodoH<Integer, Integer> h1 = new NodoH<Integer, Integer>();
                for (int i = 10; i < 22; i++) {
                    a.insertar(a.getRaiz(), new Integer(i), new Integer(i));
                }
                for (int i = 33; i < 44; i++) {
                    a.insertar(a.getRaiz(), new Integer(i), new Integer(i));
                }
                for (int i = 55; i < 86; i++) {
                    a.insertar(a.getRaiz(), new Integer(i), new Integer(i));
                }
//                for (int i = 100; i < 180; i++) {
//                    a.insertar(a.getRaiz(), new Integer(i), new Integer(i));
//                }
                ab.insertar(a);

                //arbol 2
                ArbolH<Integer, Integer> b = new ArbolH<Integer, Integer>(-100, 100);
                b.setM(2);

                NodoH<Integer, Integer> h2 = new NodoH<Integer, Integer>();
                for (int i = 22; i < 55; i++) {
                    b.insertar(b.getRaiz(), new Integer(i), new Integer(i));
                }
                for (int i = 60; i < 79; i++) {
                    b.insertar(b.getRaiz(), new Integer(i), new Integer(i));
                }
                ab.insertar(b);

                //arbol 3
                ArbolH<Integer, Integer> c = new ArbolH<Integer, Integer>(-100, 100);
                c.setM(2);
                for (int i = 10; i < 35; i++) {
                    c.insertar(c.getRaiz(), new Integer(i), new Integer(i));
                }
                for (int i = 43; i < 50; i++) {
                    c.insertar(c.getRaiz(), new Integer(i), new Integer(i));
                }
//                for (int i = 120; i < 150; i++) {
//                    c.insertar(c.getRaiz(), new Integer(i), new Integer(i));
//                }
                ab.insertar(c);

                ab.anidar(a, b);
                ab.anidar(b, c);

//
//                ArbolH d = (ArbolH) ab.array.get(0);
//                ArbolH e = (ArbolH) ab.array.get(1);
//                ArbolH f = (ArbolH) ab.array.get(2);
//                List doids;
//                for (int i = 140; i > 30; i--) {
//                    doids =new LinkedList();
//                    doids.add( new Integer(i));
//                    d.eliminar(d.getRaiz(), new Integer(i), doids);
//                    e.eliminar(e.getRaiz(), new Integer(i), doids);
//                    f.eliminar(f.getRaiz(), new Integer(i), doids);
//                }
//                //83-55
//                for (int i = 83; i > 55; i--) {
//                    doids =new LinkedList();
//                    doids.add( new Integer(i));
//                    d.eliminar(d.getRaiz(), new Integer(i), doids);
//                }
//                for (int i = 22; i > 14; i--) {
//                    doids =new LinkedList();
//                    doids.add( new Integer(i));
//                    d.eliminar(d.getRaiz(), new Integer(i), doids);
//                }
                cm.removeAllElements();
                for (int i = 0; i < ab.array.size(); i++) {
                    cm.addElement(i + 1);

                }

                crear.actualizarHijos();
                crear.actualizarPadres();

                lienzoAnterior.pintar(ab);
                lienzo.pintar(ab);
            }
        });
    }

    public static void main(String[] args) {
        Ventana n = new Ventana();
        n.setExtendedState(JFrame.MAXIMIZED_BOTH);
        n.setVisible(true);
    }
}
