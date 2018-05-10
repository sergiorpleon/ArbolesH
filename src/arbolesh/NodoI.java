/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolesh;

import comportamiento.Dibujable;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import visual.Global;

/**
 *
 * @author casa
 */
public class NodoI<K extends Comparable<K>, O> implements Dibujable{
    //varibles para pintar

    int xorigen;
    int yorigen;
    int xEntrada;
    int yEntrada;
    List<Integer> xPuntero = new LinkedList<Integer>();
    
    //varibles implementacion
    private int n; //numero entradas
    private List<K> llaves;
    private List<NodoI> nodosI;
    private List<NodoH> nodosH;
    private int l;
    private List<PunteroL> punL;
    private NodoI padre;
    private NodoI supN;

    public NodoI() {
        llaves = new LinkedList<K>();
        nodosI = new LinkedList<NodoI>();
        nodosH = new LinkedList<NodoH>();
        punL = new LinkedList<PunteroL>();
        padre = null;
        supN = null;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
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

    public List<NodoH> getNodosH() {
        return nodosH;
    }

    public void setNodosH(List<NodoH> nodosH) {
        this.nodosH = nodosH;
    }

    public List<NodoI> getNodosI() {
        return nodosI;
    }

    public void setNodosI(List<NodoI> nodosI) {
        this.nodosI = nodosI;
    }

    public NodoI getPadre() {
        return padre;
    }

    public void setPadre(NodoI padre) {
        this.padre = padre;
    }

    public List<PunteroL> getPunL() {
        return punL;
    }

    public void setPunL(List<PunteroL> punL) {
        this.punL = punL;
    }

    public NodoI getSupN() {
        return supN;
    }

    public void setSupN(NodoI supN) {
        this.supN = supN;
    }

    @Override
    public void coordenadas() {
        inicializar();
        if (!nodosI.isEmpty()) {
            for (int i = 0; i < nodosI.size(); i++) {
                nodosI.get(i).coordenadas();
            }
            //apunta a nodoI
            NodoI tmp1 = nodosI.get((Math.max((int) (llaves.size() / 2), 0)));
            NodoI tmp2 = nodosI.get((Math.max((int) (llaves.size() / 2) + 1, 1)));
            
            yorigen = tmp1.yorigen - Global.verticalEspacio;
            xorigen = (tmp1.xorigen + tmp2.xorigen) / 2;// - Global.horizontalEspacio - 4 * Global.largoValor;
            //System.out.println("si");
            yEntrada = yorigen;
            xEntrada = xorigen + ((Global.largoValor + Global.largoPuntero) * (llaves.size()) / 2);
            int tmpX = xorigen + Global.largoPuntero / 2;
            for (int i = 0; i < nodosI.size(); i++) {
                xPuntero.add(tmpX);
                tmpX = tmpX + Global.largoPuntero;
                tmpX = tmpX + Global.largoValor;
            }


        } else {
            //apunta a hoja
            NodoH tmp = nodosH.get((int) (llaves.size() / 2));
            yorigen = tmp.yorigen - Global.verticalEspacio;
            xorigen = tmp.xorigen;// - Global.horizontalEspacio - 4 * Global.largoValor;

            yEntrada = yorigen;
            xEntrada = xorigen + Global.largoPuntero / 2 + ((Global.largoValor + Global.largoPuntero) * (llaves.size()) / 2);

            int tmpX = xorigen + Global.largoPuntero / 2;
            for (int i = 0; i < nodosH.size(); i++) {
                xPuntero.add(tmpX);

                tmpX = tmpX + Global.largoPuntero;
                tmpX = tmpX + Global.largoValor;
            }
        }
    }

    @Override
    public void pintar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int tmpX = xorigen;
        int tmpY = 0;

        Ellipse2D c2;
        Rectangle2D r2;
        Line2D l;
        int lx = 0;
        int ly = 0;
        Stroke pincel;

        if (nodosH.isEmpty()) {
            tmpY = nodosI.get(0).yorigen - Global.verticalEspacio;
            for (int i = 0; i < nodosI.size(); i++) {
                nodosI.get(i).pintar(g);
            }
        } else {
            tmpY = nodosH.get(0).yorigen - Global.verticalEspacio;
            for (int i = 0; i < nodosH.size(); i++) {
                nodosH.get(i).pintar(g);
            }
        }

        for (int i = 0; i < llaves.size(); i++) {
            //-----pintado de rectangulo punteros
            r2 = new Rectangle2D.Float(tmpX, tmpY, Global.largoPuntero, Global.altoNodo);
            pincel = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER);

            g2.setColor(Color.blue);
            g2.setStroke(pincel);
            g2.draw(r2);

            tmpX = tmpX + Global.largoPuntero;
            //-----pintado de arista punteros
            //tmpY = tmpY;
            //pintar arista
            //g2.setColor(Color.pink);

            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(1.0f));
            if (!nodosH.isEmpty()) {
                lx = nodosH.get(i).xEntrada;
                ly = nodosH.get(i).yEntrada;
            } else {
                lx = nodosI.get(i).xEntrada;
                ly = nodosI.get(i).yEntrada;
            }
            c2 = new Ellipse2D.Float(xPuntero.get(i) - 1, yorigen + Global.altoNodo / 2 - 1, 2, 2);
            g2.draw(c2);

            l = new Line2D.Float(xPuntero.get(i), yorigen + Global.altoNodo / 2, lx, ly);
            g2.draw(l);

            //fin pintar aristas


            //texto
            r2 = new Rectangle2D.Float(tmpX, tmpY, Global.largoValor, Global.altoNodo);
            pincel = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER);

            g2.setColor(Color.blue);
            g2.setStroke(pincel);
            g2.draw(r2);

            g2.setColor(Color.black);
            g2.setFont(new Font("Arial", Font.ITALIC, Global.letra));
            g2.drawString(llaves.get(i) + "", (tmpX + Global.largoValor / 2 - 4 * ((int) Math.log(Double.parseDouble(llaves.get(i).toString()))) / 2), (tmpY + Global.altoNodo / 2 + (2 + Global.letra) / 3));

            tmpX = tmpX + Global.largoValor;

        }

        r2 = new Rectangle2D.Float(tmpX, tmpY, Global.largoPuntero, Global.altoNodo);
        pincel = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2.setColor(Color.blue);
        g2.setStroke(pincel);
        g2.draw(r2);
        //pintar arista
        //g2.setColor(Color.pink);
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(1.0f));
        if (nodosI.isEmpty()) {
            lx = nodosH.get(llaves.size()).xEntrada;
            ly = nodosH.get(llaves.size()).yEntrada;
        } else {
            lx = nodosI.get(llaves.size()).xEntrada;
            ly = nodosI.get(llaves.size()).yEntrada;
        }
        c2 = new Ellipse2D.Float(xPuntero.get(llaves.size()) - 1, yorigen + Global.altoNodo / 2 - 1, 2, 2);
        g2.draw(c2);
        l = new Line2D.Float(xPuntero.get(llaves.size()), yorigen + Global.altoNodo / 2, lx, ly);
        g2.draw(l);

        //fin pintar aristas

        //pintar enlaces a otros arboles
        if (punL != null && !punL.isEmpty()) {

            for (int i = 0; i < punL.size(); i++) {
                PunteroL pl = punL.get(i);

                g2.setColor(Color.red);
                g2.setStroke(new BasicStroke(1.0f));
                if (pl.getSubI() != null) {

                    lx = pl.getSubI().xEntrada;
                    ly = pl.getSubI().yEntrada;
                } else {
                    lx = pl.getSubH().xEntrada;
                    ly = pl.getSubH().yEntrada;
                }
                float punteo1[] = {10.0f, 4.0f};
                //linea discontinua
                //BasicStroke pincel1 = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 3.0f, punteo1, 10.0f);
                //linea recta
                pincel = new BasicStroke(0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                g2.setStroke(pincel);
                l = new Line2D.Float(xEntrada, yEntrada + Global.altoNodo, xEntrada, yEntrada + Global.altoNodo + 5 + i * 5);
                g2.draw(l);
                l = new Line2D.Float(xEntrada, yEntrada + Global.altoNodo + 5 + i * 5, lx, ly - 5);
                g2.draw(l);
                l = new Line2D.Float(lx, ly - 5, lx, ly);
                g2.draw(l);
            }
        }
        //fin pintar enlaces
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
//                
//        }
    }

    public void inicializar() {
        xorigen = 0;
        yorigen = 0;
        xEntrada = 0;
        yEntrada = 0;
        xPuntero = new LinkedList<Integer>();
    }
}
