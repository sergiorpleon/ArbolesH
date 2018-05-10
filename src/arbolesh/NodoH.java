/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolesh;

import comportamiento.Dibujable;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;
import java.util.List;
import visual.Global;

/**
 *
 * @author casa
 */
public class NodoH<K extends Comparable<K>, O> implements Dibujable{
    //variables para pintura
    int xorigen;
    int yorigen;
    int xEntrada;
    int yEntrada;
    int xFinal;
    List<Integer> xValores = new LinkedList<Integer>();
    
    //variables implementacion
    private int n; //numero de entradas
    public List<K> llaves;
    public List<List<O>> oids;
    public NodoH hermano;
    public NodoI padre;
    public NodoI supN;

    public NodoH() {
        llaves = new LinkedList<K>();
        oids = new LinkedList<List<O>>();
        hermano = null;
        padre = null;
        supN = null;
    }

    public NodoH getHermano() {
        return hermano;
    }

    public void setHermano(NodoH hermano) {
        this.hermano = hermano;
    }

    public List<K> getLlaves() {
        return llaves;
    }

    public void setLlaves(List<K> llaves) {
        this.llaves = llaves;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public List<List<O>> getOids() {
        return oids;
    }

    public void setOids(List<List<O>> oids) {
        this.oids = oids;
    }

    public NodoI getPadre() {
        return padre;
    }

    public void setPadre(NodoI padre) {
        this.padre = padre;
    }

    public NodoI getSupN() {
        return supN;
    }

    public void setSupN(NodoI supN) {
        this.supN = supN;
    }

    @Override
    public void coordenadas() {
        //inicializar();
        int tmp = llaves.size();
        yEntrada = yorigen;
        int tmpX = xorigen;
        xEntrada = tmpX + (Global.largoValor * tmp) / 2;

        xFinal = tmpX + Global.largoValor * tmp;
    }

    @Override
    public void pintar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int tmpX = xorigen;
        int tmpY = yorigen;

        Rectangle2D r2;
        Stroke pincel;

        for (int i = 0; i < llaves.size(); i++) {
            //texto
            r2 = new Rectangle2D.Float(tmpX, tmpY, Global.largoValor, Global.altoNodo);
            pincel = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER);
            g2.setColor(Color.green);
            g2.setStroke(pincel);
            g2.draw(r2);

            g2.setColor(Color.black);
            g2.setFont(new Font("Arial", Font.ITALIC, Global.letra));
            g2.drawString(llaves.get(i) + "", (tmpX + Global.largoValor / 2 - 4*((int)Math.log(Double.parseDouble(llaves.get(i).toString())))/2), (tmpY + Global.altoNodo / 2 + (2 + Global.letra) / 3));

            int tmpYoids = tmpY + 2*Global.altoNodo;

            //pintar oids
            //texto
            Line2D l;
            RoundRectangle2D r3;
            g2.setStroke(new BasicStroke(1.0f));
             l = new Line2D.Float(tmpX+Global.largoValor/2, tmpY + Global.altoNodo, tmpX+Global.largoValor/2, tmpYoids);
            g2.draw(l);
            
            r3 = new RoundRectangle2D.Float(tmpX + 2, tmpYoids, Global.largoValor - 4, Global.altoNodo * oids.get(i).size(), 25,25);
            pincel = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER);
            g2.setColor(Color.green);
            g2.setStroke(pincel);
            g2.draw(r3);
            g2.setColor(new Color(0, 255, 10, 50));
            g2.fill(r3);

            int tmpYo = tmpYoids;
            for (int j = 0; j < oids.get(i).size(); j++) {
                g2.setColor(Color.black);
                g2.setFont(new Font("Arial", Font.ITALIC, Global.letra));
                g2.drawString(oids.get(i).get(j) + "", (tmpX + Global.largoValor / 2 -Global.altoNodo/15 - Global.altoNodo/20- 4*((int)Math.log(Double.parseDouble(oids.get(i).get(j).toString())))/2), (tmpYo  + Global.altoNodo / 2 + (2 + Global.letra) / 3));
                tmpYo += Global.altoNodo;
            }
            tmpX = tmpX + Global.largoValor;
        }

        //*pintar nodo
        //*en un nodo llamar a pintar Valores
        //*pintar punteros
        
//        //fin pintar enlaces
//        int lx=0;
//        int ly=0;
//        Line2D l;
//        if(supN!=null){
//            g2.setColor(Color.blue);
//                g2.setStroke(new BasicStroke(1.0f));
//                
//                    lx = supN.xorigen;
//                    ly = supN.yorigen;
//                
//                pincel = new BasicStroke(0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
//                g2.setStroke(pincel);
//                l = new Line2D.Float(xorigen, yorigen, lx, ly);
//                g2.draw(l);
//        }
    }

    public void inicializar() {
        xorigen = 0;
        yorigen = 0;
        xEntrada = 0;
        yEntrada = 0;
        xValores = new LinkedList<Integer>();
    }
}
