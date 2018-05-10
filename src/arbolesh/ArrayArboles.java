/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolesh;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import visual.Global;

/**
 *
 * @author casa
 */
public class ArrayArboles<K extends Comparable<K>, O> {

    public List<ArbolH> array;
    //public int[] idArboles;
    public List<Integer> padres;

    public ArrayArboles() {
        array = new LinkedList<ArbolH>();
        //idArboles = new int[DIMENSION];
        padres = new LinkedList<Integer>();
    }

    public void insertar(ArbolH a) {
        array.add(a);
        padres.add(-1);
    }

    public void eliminar(int valor) {
        if (array.get(valor).getRaiz() != null) {
            array.get(valor).limpiarEntradas(array.get(valor).getRaiz());
        }
        array.remove(valor);
        padres.remove(valor);
        int nval = 0;
        for (int i = 0; i < padres.size(); i++) {
            nval = padres.get(i);
            if (nval == valor) {
                padres.set(i, -1);
            } else if (nval > valor) {
                padres.set(i, nval - 1);
            }
        }
    }

    public void anidar(ArbolH padre, ArbolH hijo) {
        hijo.minSub = padre.minArb;
        hijo.maxSub = padre.maxArb;
        if (hijo.anidar(padre.getRaiz(), hijo.getRaiz())) {
            int hij = array.indexOf(hijo);
            int pad = array.indexOf(padre);
            padres.set(hij, pad);
        }
    }

    public void anidar(int padre, int hijo) {
        array.get(hijo).minSub = array.get(padre).minArb;
        array.get(hijo).maxSub = array.get(padre).maxArb;
        if ((array.get(hijo)).anidar((NodoI) (array.get(padre).getRaiz()), (NodoI) (array.get(hijo).getRaiz()))) {
            padres.set(hijo, padre);
        }
    }

    public void insertarNodos(int arbol, K vnuevo, O onuevo) {
        if (padres.get(arbol) != -1) {
            array.get(arbol).minSub = array.get(padres.get(arbol)).minArb;
            array.get(arbol).maxSub = array.get(padres.get(arbol)).maxArb;
        }
        (array.get(arbol)).insertar((NodoI) (array.get(arbol).getRaiz()), vnuevo, onuevo);
    }

    public void eliminarNodos(int arbol, K kdelete, List<O> loid) {
        if (padres.get(arbol) != -1) {
            array.get(arbol).minSub = array.get(padres.get(arbol)).minArb;
            array.get(arbol).maxSub = array.get(padres.get(arbol)).maxArb;
        }
        (array.get(arbol)).eliminar((NodoI) (array.get(arbol).getRaiz()), kdelete, loid);

    }

    public Set<O> buscarNodos(int arbol, K menor, K mayor, boolean sup) {
        Set<O> result = (array.get(arbol)).busqueda((NodoI) (array.get(arbol).getRaiz()), menor, mayor, true);
        return result;
    }

    public Set<O> buscarNodo(int arbol, K menor, K mayor) {
        Set<O> result = (array.get(arbol)).busquedaLocal((NodoI) (array.get(arbol).getRaiz()), menor, mayor);
        return result;
    }

    public void pintar(Graphics g) {
        //establecer coordenadas
        Global.origeny = 0;
        Global.origenx = 20;
        for (int i = 0; i < array.size(); i++) {
            //if(padres.get(i) ==-1){
            array.get(i).coordenadas();
            //}
        }
        pintarG(g);
    }

    private void pintarG(Graphics g) {
        //pintar arboles
        for (int i = 0; i < array.size(); i++) {

            array.get(i).pintar(g);
            //pintar informacion arbol
            Graphics2D g2 = (Graphics2D) g;

            Rectangle2D r2;
            Stroke pincel;
            RoundRectangle2D r3;
//            Line2D l;
//            g2.setStroke(new BasicStroke(1.0f));
//            l = new Line2D.Float(array.get(i).xorigen, array.get(i).yorigen, 1000, 10);
//            g2.draw(l);

            //pintado de rectangulo
            r3 = new RoundRectangle2D.Float(array.get(i).xorigen, array.get(i).yorigen + 10, 7 * Global.altoNodo + 30, Global.altoNodo, 25, 25);
            pincel = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
            g2.setColor(Color.red);
            g2.setStroke(pincel);
            g2.draw(r3);
            g2.setColor(new Color(255, 10, 10, 50));
            g2.fill(r3);

            //pintado de texto
            //g2.setStroke(new BasicStroke(1.0f));
            g2.setColor(Color.black);
            g2.setFont(new Font("Arial", Font.ITALIC, Global.letra));
            g2.drawString("Árbol " + (i + 1) + ": " + array.get(i).getId(), array.get(i).xorigen + Global.altoNodo, array.get(i).yorigen + 10 + Global.altoNodo / 4 + Global.altoNodo / 2);
        }
    }
}
