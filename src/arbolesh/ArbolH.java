/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 */
package arbolesh;


import comportamiento.Dibujable;
import java.awt.Graphics;
import java.util.*;
import visual.Global;

/**
 *
 * @author casa
 */
public class ArbolH<K extends Comparable<K>, O> implements Dibujable {
    //varibles para pintar

    public int xorigen;
    public int yorigen;
    public int largoArbol;
    public int altoArbol;
    public int niveles;
    //varibles implementacion
    private String id;
    public K minArb;
    public K maxArb;
    public K minGlob;
    public K maxGlob;
    
    public K minSub;
    public K maxSub;
    
    private int m; //dimension minima de nodos
    private NodoH ini;
    private NodoI raiz;

    public ArbolH() {
        this.m = 2;
        this.id = "id vacío";
        raiz = null;
        minArb = null;
        maxArb = null;
    }

    public ArbolH(int m) {
        this.m = m;
        this.id = "id vacío";
        raiz = null;
        minArb = null;
        maxArb = null;
    }

    public ArbolH(int m, String id) {
        this.m = m;
        this.id = id;
        raiz = null;
        minArb = null;
        maxArb = null;
    }

    public ArbolH(int m, String id, K minGlob, K maxGlob) {
        this.m = m;
        this.id = id;
        raiz = null;
        this.minGlob = minGlob;
        this.maxGlob = maxGlob;
    }
    
    public ArbolH( K minGlob, K maxGlob) {
        this.minGlob = minGlob;
        this.maxGlob = maxGlob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public NodoI getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoI raiz) {
        this.raiz = raiz;
    }

    public NodoH getIni() {
        return ini;
    }

    public void setIni(NodoH ini) {
        this.ini = ini;
    }

    /**
     ************************************************************************
     *********************************BUSCAR**********************************
     * *************************************************************************
     */
    //---metodo que busca solo en el arbol
    //>busca el nodo hoja donde esta el rango minimo o el mayor inmediato
    //>busca en los hermanos hasta llegar a un rango mayor que el maximo
    //*devuelve una lista con los oids
    public Set<O> busquedaLocal(NodoI nodInt, K menor, K mayor) {
        List<K> llaves_nodInt = nodInt.getLlaves();
        List<NodoI> nodosI_nodInt = nodInt.getNodosI();
        List<NodoH> nodosH_nodInt = nodInt.getNodosH();
        Set<O> valores = new HashSet<O>();
        //caso que apunte a nodo interno
        if (raiz.getLlaves().isEmpty()) {
            valores.addAll(busquedaLocal(nodosH_nodInt.get(0), menor, mayor));
            return valores;
        }

        if (nodosH_nodInt.isEmpty()) {
            for (int i = 0; i < llaves_nodInt.size(); i++) {
                //si el minimo es menor que K busco en el nodo hijo IzqK
                if (menor.compareTo(llaves_nodInt.get(i)) < 0) {
                    valores.addAll(busquedaLocal(nodosI_nodInt.get(i), menor, mayor));
                    break;
                }
                // si llegue hasta la ultima llave sin que menor<Ki busco en la ultima rama
                if (i == (llaves_nodInt.size() - 1)) {
                    valores.addAll(busquedaLocal(nodosI_nodInt.get(i + 1), menor, mayor));
                }
            }

            /////////////////////////////////////////////////////////
        } else {
            //caso que apunte a nodo hoja
            for (int i = 0; i < llaves_nodInt.size(); i++) {
                //si el minimo es menor que K busco en el nodo hijo IzqK
                if (menor.compareTo(llaves_nodInt.get(i)) < 0) {
                    List<O> bh = busquedaLocal(nodosH_nodInt.get(i), menor, mayor);
                    valores.addAll(bh);
                    break;
                }
                // si llegue hasta la ultima llave sin que menor<Ki busco en la ultima rama
                if (i == (llaves_nodInt.size() - 1)) {
                    valores.addAll(busquedaLocal(nodosH_nodInt.get(i + 1), menor, mayor));
                }
            }
            // }
        }//abrebiar luego de que okkk
        return valores;
    }

    public List<O> busquedaLocal(NodoH nodHoj, K menor, K mayor) {
        List<K> llaves_nodHoj = nodHoj.getLlaves();
        List<List<O>> oids_nodHoj = nodHoj.getOids();
        NodoH hermano_nodHoj = nodHoj.getHermano();

        List<O> valores = new LinkedList<O>();
        for (int i = 0; i < llaves_nodHoj.size(); i++) {
            //si llaves comprendido entre rango lo tomo
            if (llaves_nodHoj.get(i).compareTo(mayor) <= 0) {
                if (menor.compareTo(llaves_nodHoj.get(i)) <= 0) {
                    valores.addAll(oids_nodHoj.get(i));
                }
                //si llegue hasta la ultima llave busco en el hermano
                if (i == (llaves_nodHoj.size() - 1)) {
                    if (hermano_nodHoj != null) {
                        List<O> bh = busquedaLocal(hermano_nodHoj, menor, mayor);
                        valores.addAll(bh);
                    }
                }
            } else {
                //si no esta en el rango salgo
                break;
            }
        }
        return valores;
    }

    public Set<O> busqueda(NodoI nodInt, K menor, K mayor, boolean sup) {
        List<K> llaves_nodInt = nodInt.getLlaves();
        List<NodoI> nodosI_nodInt = nodInt.getNodosI();
        List<NodoH> nodosH_nodInt = nodInt.getNodosH();
        List<PunteroL> punL_nodInt = new LinkedList<PunteroL>();
        Set<O> valores = new HashSet<O>();
        //List<O> valores = new LinkedList<O>();



        ///VERRR
        if (!(nodInt.getPunL() == null)) {
            punL_nodInt = nodInt.getPunL();
        }

        //caso que apunte a nodo interno
        if (nodosH_nodInt.isEmpty()) {

            //analizo el primer puntero
            if (llaves_nodInt.get(0).compareTo(menor) > 0) {
                valores.addAll(busqueda(nodosI_nodInt.get(0), menor, mayor, sup));
            }

            for (int i = 1; i < llaves_nodInt.size(); i++) {
                //si llaveIzq < mayor salgo del for
                if (llaves_nodInt.get(i - 1).compareTo(mayor) > 0) {
                    break;
                }
                //si llaveDer < menor paso proxima iteracion
                if (llaves_nodInt.get(i).compareTo(menor) < 0) {
                    continue;
                } else {
                    valores.addAll(busqueda(nodosI_nodInt.get(i), menor, mayor, sup));
                }
            }

            //analizo el ultimo puntero
            if (llaves_nodInt.get(llaves_nodInt.size() - 1).compareTo(mayor) <= 0) {
                valores.addAll(busqueda(nodosI_nodInt.get(llaves_nodInt.size()), menor, mayor, sup));
            }

            /////////////////////////////////////////////////////////
        } else { //caso que apunte a nodo hoja

            //caso inicial que hay nodo interno vacio
            if (llaves_nodInt.size() == 0) {
                valores.addAll(busqueda(nodosH_nodInt.get(0), menor, mayor, sup));
                return valores;
            }

            //analizo el primer puntero
            if (llaves_nodInt.get(0).compareTo(menor) > 0) {
                valores.addAll(busqueda(nodosH_nodInt.get(0), menor, mayor, sup));

            }

            for (int i = 1; i < llaves_nodInt.size(); i++) {
                //si llaveIzq < mayor salgo del for
                if (llaves_nodInt.get(i - 1).compareTo(mayor) > 0) {
                    break;
                }
                //si llaveDer < menor paso proxima iteracion
                if (llaves_nodInt.get(i).compareTo(menor) < 0) {
                    continue;
                } else {
                    valores.addAll(busqueda(nodosH_nodInt.get(i), menor, mayor, sup));
                }
            }
            //analizo el ultimo puntero
            if (llaves_nodInt.get(llaves_nodInt.size() - 1).compareTo(mayor) <= 0) {
                valores.addAll(busqueda(nodosH_nodInt.get(llaves_nodInt.size()), menor, mayor, sup));
            }
        }

        ///////////////////////////
        //busqueda en punteros L///
        ///////////////////////////
        if (!punL_nodInt.isEmpty()) {
            for (int i = 0; i < punL_nodInt.size(); i++) {
                PunteroL punLx = punL_nodInt.get(i);
                if (((menor.compareTo((K) punLx.getMin()) < 0) && (mayor.compareTo((K) punLx.getMin()) < 0)) || ((menor.compareTo((K) punLx.getMax()) > 0) && (mayor.compareTo((K) punLx.getMax()) > 0))) {
                    //System.out.println(menor + " " + mayor + " entro " + punLx.getMin() + " ko " + (menor.compareTo((K) punLx.getMin()) < 0) + " " + (mayor.compareTo((K) punLx.getMin()) < 0) + " " + (menor.compareTo((K) punLx.getMax()) > 0) + " " + (mayor.compareTo((K) punLx.getMax()) > 0) + " ");
                } else {
                    //System.out.println("entro ok");
                    if (punLx.getSubH() == null) {

                        valores.addAll(busqueda(punLx.getSubI(), menor, mayor, true));
                    } else {

                        valores.addAll(busqueda(punLx.getSubH(), menor, mayor, true));
                    }
                }
            }
        }
        ////////////////////////////////
        ///si se vino de un puntero L///
        ////////////////////////////////
        if (sup) {
            if (nodInt.getPadre() != null) {
                //System.out.println("sup padre i " + nodInt.getPadre().getLlaves());
                valores.addAll(busquedaL(nodInt.getPadre(), menor, mayor, true));
            }
        }
        return valores;
    }

    public Set<O> busquedaL(NodoI nodInt, K menor, K mayor, boolean sup) {
        List<PunteroL> punL_nodInt = nodInt.getPunL();
        Set<O> valores = new HashSet<O>();
        //List<O> valores = new LinkedList<O>();
        if (punL_nodInt != null) {
            for (int i = 0; i < punL_nodInt.size(); i++) {
                PunteroL punLx = punL_nodInt.get(i);
                // veo que no suceda menor, mayor < min o menor, mayor > max
                if (((menor.compareTo((K) punLx.getMin()) < 0) && (mayor.compareTo((K) punLx.getMin()) < 0)) || ((menor.compareTo((K) punLx.getMax()) >= 0) && (mayor.compareTo((K) punLx.getMax()) >= 0))) {
                } else {
                    //si nodo apunta a nodo I
                    if (punLx.getSubH() == null) {

                        valores.addAll(busqueda(punLx.getSubI(), menor, mayor, sup));
                    } else {
                        //si nodo apunta a nodo H
                        valores.addAll(busqueda(punLx.getSubH(), menor, mayor, sup));
                    }
                }
            }
        }
        //busco en los apuntadores superiores
        if (nodInt.getPadre() != null) {
//            System.out.println("sup padre met " + nodInt.getPadre().getLlaves());
            valores.addAll(busquedaL(nodInt.getPadre(), menor, mayor, sup));
        }
        return valores;
    }

    public List<O> busqueda(NodoH nodHoj, K menor, K mayor, boolean sup) {
        List<K> llaves_nodHoj = nodHoj.getLlaves();
        List<List<O>> oids_nodHoj = nodHoj.getOids();
        List<O> valores = new LinkedList<O>();

        for (int i = 0; i < llaves_nodHoj.size(); i++) {
            //si llaves comprendido entre rango lo tomo
            if (llaves_nodHoj.get(i).compareTo(mayor) <= 0) {
                if (menor.compareTo(llaves_nodHoj.get(i)) <= 0) {
                    valores.addAll(oids_nodHoj.get(i));
                }
            }
        }
        if (sup) {
            if (nodHoj.getPadre() != null) {
//                System.out.println("sup padre i " + nodHoj.getPadre().getLlaves());
                valores.addAll(busquedaL(nodHoj.getPadre(), menor, mayor, true));
            }
        }

        return valores;
    }

    private void actMinSupN(NodoI nodInt, K min) {
        if (nodInt.getSupN() != null) {
            PunteroL<K> punLx = null;
            List<PunteroL<K>> listPunL_nodSup = nodInt.getSupN().getPunL();
            //busco puntero a div
            for (int i = 0; i < listPunL_nodSup.size(); i++) {
                if (listPunL_nodSup.get(i).getSubI() == nodInt) {
                    punLx = listPunL_nodSup.get(i);//guardo punteroL a div
                    break;
                }
            }
            //redefino punteros a div y creo punteroN a new
            if (punLx != null) {
                //K max_nodDiv = (K) nodInt.getLlaves().get(nodInt.getLlaves().size() - 1);
                punLx.setMin(min);
            }
        } else {
            if (nodInt.getPadre() != null) {
                actMinSupN(nodInt.getPadre(), min);
            }
        }
    }

    private void actMinSupN(NodoH nodHoj, K min) {
        if (nodHoj.getSupN() != null) {
            PunteroL<K> punLx = null;
            List<PunteroL<K>> listPunL_nodSup = nodHoj.getSupN().getPunL();
            //busco puntero a div
            for (int i = 0; i < listPunL_nodSup.size(); i++) {
                if (listPunL_nodSup.get(i).getSubH() == nodHoj) {
                    punLx = listPunL_nodSup.get(i);//guardo punteroL a div
                    break;
                }
            }
            //redefino punteros a div y creo punteroN a new
            if (punLx != null) {
                //K max_nodDiv = (K) nodInt.getLlaves().get(nodInt.getLlaves().size() - 1);
                punLx.setMin(min);
            }
        } else {
            if (nodHoj.getPadre() != null) {
                actMinSupN(nodHoj.getPadre(), min);
            }
        }
    }

    private void actMaxSupN(NodoI nodInt, K max) {
        if (nodInt.getSupN() != null) {
            PunteroL<K> punLx = null;
            List<PunteroL<K>> listPunL_nodSup = nodInt.getSupN().getPunL();
            //busco puntero a div
            for (int i = 0; i < listPunL_nodSup.size(); i++) {
                if (listPunL_nodSup.get(i).getSubI() == nodInt) {
                    punLx = listPunL_nodSup.get(i);//guardo punteroL a div
                    break;
                }
            }
            //redefino punteros a div y creo punteroN a new
            if (punLx != null) {
                //K max_nodDiv = (K) nodInt.getLlaves().get(nodInt.getLlaves().size() - 1);
                punLx.setMax(max);
            }
        } else {
            if (nodInt.getPadre() != null) {
                actMaxSupN(nodInt.getPadre(), max);
            }
        }
    }

    private void actMaxSupN(NodoH nodHoj, K max) {
        if (nodHoj.getSupN() != null) {
            PunteroL<K> punLx = null;
            List<PunteroL<K>> listPunL_nodSup = nodHoj.getSupN().getPunL();
            //busco puntero a div
            for (int i = 0; i < listPunL_nodSup.size(); i++) {
                if (listPunL_nodSup.get(i).getSubH() == nodHoj) {
                    punLx = listPunL_nodSup.get(i);//guardo punteroL a div
                    break;
                }
            }
            //redefino punteros a div y creo punteroN a new
            if (punLx != null) {
                //K max_nodDiv = (K) nodInt.getLlaves().get(nodInt.getLlaves().size() - 1);
                punLx.setMin(max);
            }
        } else {
            if (nodHoj.getPadre() != null) {
                actMaxSupN(nodHoj.getPadre(), max);
            }
        }
    }

    /**
     ************************************************************************
     ********************************INSERTAR*********************************
     * ************************************************************************
     */
    public void insertar(NodoI nodInt, K llave, O valor) {
        if (minArb == null) {
            minArb = llave;
         
        } else if (llave.compareTo(minArb) < 0) {
            minArb = llave;
            //actMinSupN(nodHoj, llave);
        }
        if (maxArb == null) {
            maxArb = llave;
        } else if (llave.compareTo(maxArb) > 0) {
            maxArb = llave;
            //actMaxSupN(nodHoj, llave);
        }

        //si no hay raiz creo raiz con nodo hoja con valor insertado
        if (raiz == null) {
            //creo lista con valor
            List<O> newOids = new LinkedList<O>();
            newOids.add(valor);

            //creo hoja con llave y lista
            NodoH newHoj = new NodoH();
            newHoj.getLlaves().add(0, llave);
            newHoj.getOids().add(0, newOids);

            //creo raiz y adiciono hoja
            raiz = new NodoI();
            raiz.getNodosH().add(newHoj);
            return;
        }

        List<K> llaves_nodInt = new LinkedList<K>();
        if (nodInt.getLlaves() != null) {
            llaves_nodInt = nodInt.getLlaves();
        }
        List<NodoI> nodosI_nodInt = nodInt.getNodosI();
        List<NodoH> nodosH_nodInt = nodInt.getNodosH();

        //caso que apunte a nodo interno
        if (nodosH_nodInt.isEmpty()) {
            for (int i = 0; i < llaves_nodInt.size(); i++) {
                //si el minimo es menor que K busco en el nodo hijo IzqK
                if (llave.compareTo(llaves_nodInt.get(i)) < 0) {
                    insertar(nodosI_nodInt.get(i), llave, valor);
                    return;
                }
                // si llegue hasta la ultima llave sin que menor<Ki busco en la ultima rama
                if (i == (llaves_nodInt.size() - 1)) {
                    insertar(nodosI_nodInt.get(llaves_nodInt.size()), llave, valor);
                    return;
                }
            }
        } else {
            if (nodosH_nodInt.size() == 1) {
                insertar(nodosH_nodInt.get(0), llave, valor);
            } else {
                for (int i = 0; i < llaves_nodInt.size(); i++) {
                    //si el minimo es menor que K busco en el nodo hijo IzqK
                    if (llave.compareTo(llaves_nodInt.get(i)) < 0) {
                        insertar(nodosH_nodInt.get(i), llave, valor);
                        return;
                    }
                    // si llegue hasta la ultima llave sin que menor<Ki busco en la ultima rama
                    if (i == (llaves_nodInt.size() - 1)) {
                        insertar(nodosH_nodInt.get(i + 1), llave, valor);
                        return;
                    }
                }
            }
        }
    }

    public void insertar(NodoH nodHoj, K llave, O valor) {
        if (minArb == null) {
            minArb = llave;
            
        } else if (llave.compareTo(minArb) < 0) {
            minArb = llave;
            
            //actMinSupN(nodHoj, llave);
        }
        if (maxArb == null) {
            maxArb = llave;
        } else if (llave.compareTo(maxArb) > 0) {
            maxArb = llave;
            //actMaxSupN(nodHoj, llave);
        }


        List<K> llaves_nodHoj = nodHoj.getLlaves();
        List<List<O>> oids_nodHoj = nodHoj.getOids();

        //si vnuevo ya existe adiciono onuevo a la lista de oid
        for (int i = 0; i < llaves_nodHoj.size(); i++) {
            if (llave.compareTo(llaves_nodHoj.get(i)) == 0) {
                if (oids_nodHoj.get(i).indexOf(valor) < 0) {
                    oids_nodHoj.get(i).add(valor);
                }
                return;
            }
        }

        //si vnuevo no existe
        for (int i = 0; i < llaves_nodHoj.size(); i++) {
            //busco posicion de llave
            if (llave.compareTo(llaves_nodHoj.get(i)) < 0) {
                List<O> newOid = new LinkedList<O>();
                newOid.add(valor);
                llaves_nodHoj.add(i, llave);
                oids_nodHoj.add(i, newOid);
                if (llaves_nodHoj.size() > 2 * m) {
                    //desbordamiento
                    dividir(nodHoj);
                }
                return;
            }
        }
        //caso adicion ultimo puntero o nodo vacio
        List<O> newo = new LinkedList<O>();
        newo.add(valor);
        llaves_nodHoj.add(llave);
        oids_nodHoj.add(llaves_nodHoj.size() - 1, newo);
        if (llaves_nodHoj.size() > 2 * m) {
            //desbordamiento
            dividir(nodHoj);
        }
    }

    public void dividir(NodoH nodDiv) {
        //nodo izq            nodDiv===nodIzq
        List<K> llave_nodIzq = new LinkedList<K>();
        List<List<O>> oids_nodIzq = new LinkedList<List<O>>();

        //nodo derecha
        List<K> llave_nodDer = new LinkedList<K>();
        List<List<O>> oids_nodDer = new LinkedList<List<O>>();
        NodoH nodDer = new NodoH();//nuevo nodo

        List<K> llaves_nodDiv = nodDiv.getLlaves();
        List<List<O>> oids_nodDiv = nodDiv.getOids();
        //separar las k y oids en 2
        for (int i = 0; i < llaves_nodDiv.size(); i++) {
            if (i < m) {
                llave_nodIzq.add(llaves_nodDiv.get(i));
                oids_nodIzq.add(oids_nodDiv.get(i));
            } else {
                llave_nodDer.add(llaves_nodDiv.get(i));
                oids_nodDer.add(oids_nodDiv.get(i));
            }
        }

        //---------
        //creo nuevo nodo
        nodDer.setLlaves(llave_nodDer); //le doy lista de llaves der
        nodDer.setOids(oids_nodDer);    //le doy lista de oid der
        nodDer.setHermano(nodDiv.getHermano()); //hermano es el hermano de div
        nodDer.setPadre(nodDiv.getPadre()); //padre es pare de div

        //---------
        //si div tenia supN creo un sup N y se lo asifno a div
        K medio = (K) nodDer.getLlaves().get(0); //para utilizarlo repetidamente
        if (nodDiv.getSupN() != null) {
            PunteroL<K> punLx = null;
            List<PunteroL<K>> listPunL_nodSup = nodDiv.getSupN().getPunL();
            //busco puntero a div
            for (int i = 0; i < listPunL_nodSup.size(); i++) {
                if (listPunL_nodSup.get(i).getSubH() == nodDiv) {
                    punLx = listPunL_nodSup.get(i);//guardo punteroL a div
                    break;
                }
            }
            //redefino punteros a div y creo punteroN a new
            K max_nodDiv = (K) nodDiv.getLlaves().get(nodDiv.getLlaves().size() - 1);
            punLx.setMax(max_nodDiv);

            //noDer apunte a nodSup
            nodDer.setSupN(nodDiv.getSupN());
            //creo un puntero L y se lo doy a nodDer
            // por minimo
            PunteroL<K> newPunL_nodSup = new PunteroL((K) nodDer.getLlaves().get(0), medio, nodDer);
            listPunL_nodSup.add(newPunL_nodSup);
        }

        //---------
        //actualizo llaves valores y hermano nodDiv===nodIzq
        nodDiv.setLlaves(llave_nodIzq); //le doy la lista reducida de llaves
        nodDiv.setOids(oids_nodIzq);   //le doy la lista reducida de oids
        nodDiv.setHermano(nodDer);//le doy el nuevo nodo como hermano

        //---------
        //modificacion en padre
        if (nodDiv.getPadre() != null) {
            //queda actualizar padre
            K info = medio; //nueva llave en padre
            List<K> llaves_padDiv = nodDer.getPadre().getLlaves();
            List<NodoH> hojas_padDiv = nodDer.getPadre().getNodosH();

            //si nodo es vacio 
            if (llaves_padDiv.size() == 0) {
                llaves_padDiv.add(info);
                hojas_padDiv.add(nodDer);
            } else if (info.compareTo(llaves_padDiv.get(llaves_padDiv.size() - 1)) > 0) {
                //si se adiciona llave al final
                llaves_padDiv.add(info);
                hojas_padDiv.add(nodDer);
            } else {
                //si se adiciona en el intermedio
                for (int i = 0; i < llaves_padDiv.size(); i++) {
                    if (info.compareTo(llaves_padDiv.get(i)) < 0) {
                        //pongo la llave y el puntero
                        llaves_padDiv.add(i, info);
                        hojas_padDiv.add(i + 1, nodDer);
                        break;
                    }
                }
            }
            //actualizo padre
            nodDer.getPadre().setLlaves(llaves_padDiv);
            nodDer.getPadre().setNodosH(hojas_padDiv);

            //desbordamiento padre
            if (llaves_padDiv.size() > 2 * m) {
                dividir(nodDer.getPadre());
            }
        } else {
            //si padre=null creo padre
            NodoI<K, O> newRoot = new NodoI<K, O>();
            newRoot.getLlaves().add(medio);
            newRoot.getNodosH().add(nodDiv);
            newRoot.getNodosH().add(nodDer);

            if (raiz.getSupN() != null) {
                PunteroL<K> punLx = null;
                List<PunteroL<K>> listPunL_nodSup = raiz.getSupN().getPunL();
                //busco puntero a div
                for (int i = 0; i < listPunL_nodSup.size(); i++) {
                    if (listPunL_nodSup.get(i).getSubI() == raiz) {
                        punLx = listPunL_nodSup.get(i);//guardo punteroL a div
                        listPunL_nodSup.remove(i);
                        break;
                    }
                }
                anidar(raiz.getSupN(), newRoot);
                newRoot.setSupN(raiz.getSupN());
            }

            newRoot.setPunL(raiz.getPunL());
            if (raiz.getPunL().size() != 0) {
                List lpunL = raiz.getPunL();
                PunteroL pL = null;
                for (int i = 0; i < lpunL.size(); i++) {
                    pL = (PunteroL) lpunL.get(i);
                    if (pL.getSubH() == null) {
                        pL.getSubI().setSupN(newRoot);
                    } else {
                        pL.getSubH().setSupN(newRoot);
                    }

                }
            }
            raiz = newRoot;
            nodDiv.setPadre(raiz);
            nodDer.setPadre(raiz);
        }
    }

    public void dividir(NodoI nodDiv) {
        if (nodDiv.getNodosH().isEmpty()) {
            //nodo izquierdo
            List<K> llaves_nodIzq = new LinkedList<K>();
            List<NodoI<K, O>> nodosI_nodIzq = new LinkedList<NodoI<K, O>>();
            //nodo derecho
            List<K> llaves_nodDer = new LinkedList<K>();
            List<NodoI<K, O>> nodoI_nodDer = new LinkedList<NodoI<K, O>>();

            NodoI nodDer = new NodoI();
            K medio = null; //para utilizarlo repetidamente

            //separo llaves y apuntadores de Div
            List<K> divllaves = nodDiv.getLlaves();
            List<NodoI<K, O>> nodoI_nodDiv = nodDiv.getNodosI();
            for (int i = 0; i < divllaves.size(); i++) {
                if (i < m) {
                    nodoI_nodDiv.get(i).setPadre(nodDiv);
                    llaves_nodIzq.add(divllaves.get(i));
                    nodosI_nodIzq.add(nodoI_nodDiv.get(i));
                } else if (i == m) {
                    nodoI_nodDiv.get(i).setPadre(nodDiv);
                    medio = divllaves.get(i);//pongo llave como valor medio
                    nodosI_nodIzq.add(nodoI_nodDiv.get(i));
                } else {
                    nodoI_nodDiv.get(i).setPadre(nodDer);
                    llaves_nodDer.add(divllaves.get(i));
                    nodoI_nodDer.add(nodoI_nodDiv.get(i));
                }
            }
            nodoI_nodDiv.get(divllaves.size()).setPadre(nodDer);
            nodoI_nodDer.add(nodoI_nodDiv.get(divllaves.size()));

            //---------
            //creo nuevo nodo
            nodDer.setLlaves(llaves_nodDer); //le doy lista de llaves izq
            nodDer.setNodosI(nodoI_nodDer);    //le doy lista de oid izq
            nodDer.setPadre(nodDiv.getPadre()); //padre es pare de div

            //actualizo div
            nodDiv.setLlaves(llaves_nodIzq); //le doy la lista reducida de llaves
            nodDiv.setNodosI(nodosI_nodIzq);   //le doy la lista reducida de oids

            //----trato a supN
            //guardo el sup de div
            NodoI nodSup_nodDiv = nodDiv.getSupN();
            //elimino la referencias en ambos arboles
            if (nodSup_nodDiv != null) {
                List<PunteroL<K>> lisPunL = nodDiv.getSupN().getPunL();
                for (int i = 0; i < lisPunL.size(); i++) {
                    if (lisPunL.get(i).getSubI() == nodDiv) {
                        nodDiv.setSupN(null);
                        lisPunL.remove(i);
                        break;
                    }
                }
            }

            //--------
            //modificacion en padre
            List<K> llave_padDiv = new LinkedList<>();
            List<NodoI> nodoI_padDiv = new LinkedList<>();
            if (nodDiv.getPadre() != null) {
                //queda actualizar padre
                K info = medio; //nueva llave en padre
                llave_padDiv = nodDer.getPadre().getLlaves();
                nodoI_padDiv = nodDer.getPadre().getNodosI();

                //si padre es vacio 
                if (llave_padDiv.size() == 0) {
                    llave_padDiv.add(info);
                    nodoI_padDiv.add(nodDer);
                } else if (info.compareTo(llave_padDiv.get(llave_padDiv.size() - 1)) > 0) {
                    //si se adiciona new llave al final
                    llave_padDiv.add(info);
                    nodoI_padDiv.add(nodDer);
                } else {
                    //si se adiciona en el intermedio
                    for (int i = 0; i < llave_padDiv.size(); i++) {
                        if (info.compareTo(llave_padDiv.get(i)) < 0) {
                            //pongo la llave y el puntero
                            llave_padDiv.add(i, info);
                            nodoI_padDiv.add(i + 1, nodDer);
                            break;
                        }
                    }
                }
                //actualizo padre
                nodDer.getPadre().setLlaves(llave_padDiv);
                nodDer.getPadre().setNodosI(nodoI_padDiv);

            } else {
                NodoI<K, O> newRoot = new NodoI<K, O>();
                newRoot.getLlaves().add(medio);
                newRoot.getNodosI().add(nodDiv);
                newRoot.getNodosI().add(nodDer);

                raiz = newRoot;
                nodDiv.setPadre(raiz);
                nodDer.setPadre(raiz);
            }

            //-----
            //reago punteros entre sup y los 2 nuevos nodos
            if (nodSup_nodDiv != null) {
                anidarD(nodSup_nodDiv, nodDiv);
                anidarD(nodSup_nodDiv, nodDer);
            }

            //--------
            //redistribuir entradas entre los dos nodos
            List<PunteroL<K>> punL_nodDiv = nodDiv.getPunL();
            //reubico punteros L del nodo div inicial
            if (!punL_nodDiv.isEmpty() && punL_nodDiv != null) {
                NodoI nodI_sub = null;
                NodoH nodH_sub = null;
                PunteroL punLx = null;
                nodDiv.setPunL(new LinkedList<>());
                for (int i = 0; i < punL_nodDiv.size(); i++) {
                    punLx = punL_nodDiv.get(i);
                    if (punLx.getSubH() == null) {
                        nodI_sub = punLx.getSubI();
                        nodI_sub.setSupN(null);//sub apunta a null
                        anidar(nodDiv.getPadre(), nodI_sub); // anido padre de div con sub
                    } else {
                        nodH_sub = punLx.getSubH();
                        nodH_sub.setSupN(null);
                        anidar(nodDiv.getPadre(), nodH_sub);
                    }
                    
//                        nodI_sub.setSupN(nodDiv.getPadre());//sub apunta a null
//                        nodDiv.getPadre().getPunL().add(punLx); // anido padre de div con sub
//                    
                }
            }
            if (llave_padDiv.size() > 2 * m) {
                dividir(nodDer.getPadre());
            }
            //////////////////////////////////////////
        } else {  //caso de hijos H

            List<K> llaves_nodIzq = new LinkedList<K>();
            List<NodoH> nodosH_nodIzq = new LinkedList<NodoH>();

            List<K> llaves_nodDer = new LinkedList<K>();
            List<NodoH> nodosH_nodDer = new LinkedList<NodoH>();

            NodoI<K, O> newI = new NodoI<K, O>();
            K medio = null; //para utilizarlo repetidamente

            //----
            //redistribuir llaves
            List<K> llaves_nodDiv = nodDiv.getLlaves();
            List<NodoH<K, O>> nodosH_nodDiv = nodDiv.getNodosH();
            for (int i = 0; i < llaves_nodDiv.size(); i++) {
                if (i < m) {
                    nodosH_nodDiv.get(i).setPadre(nodDiv);
                    llaves_nodIzq.add(llaves_nodDiv.get(i));
                    nodosH_nodIzq.add(nodosH_nodDiv.get(i));

                } else if (i == m) {
                    nodosH_nodDiv.get(i).setPadre(nodDiv);
                    medio = llaves_nodDiv.get(i);//pongo llave como valor medio
                    nodosH_nodIzq.add(nodosH_nodDiv.get(i));

                } else {
                    nodosH_nodDiv.get(i).setPadre(newI);
                    llaves_nodDer.add(llaves_nodDiv.get(i));
                    nodosH_nodDer.add(nodosH_nodDiv.get(i));
                }
            }
            nodosH_nodDiv.get(llaves_nodDiv.size()).setPadre(newI);
            nodosH_nodDer.add(nodosH_nodDiv.get(llaves_nodDiv.size()));

            //creo nuevo nodo
            newI.setLlaves(llaves_nodDer); //le doy lista de llaves izq
            newI.setNodosH(nodosH_nodDer);    //le doy lista de oid izq
            newI.setPadre(nodDiv.getPadre()); //padre es pare de div

            //actualizo nod Div
            nodDiv.setLlaves(llaves_nodIzq); //le doy la lista reducida de llaves
            nodDiv.setNodosH(nodosH_nodIzq);   //le doy la lista reducida de oids

            //--------
            //trato punteros a nodo div
            NodoI nodSup_nodDiv = nodDiv.getSupN();
            if (nodSup_nodDiv != null) {
                List<PunteroL<K>> list_punL = nodDiv.getSupN().getPunL();
                for (int i = 0; i < list_punL.size(); i++) {
                    if (list_punL.get(i).getSubI() == nodDiv) {
                        nodDiv.setSupN(null);
                        list_punL.remove(i);
                        break;
                    }
                }
            }

            List<K> llaves_padDiv = new LinkedList<>();
            List<NodoI> nodosI_padDiv = new LinkedList<>();
            //modificacion en padre
            if (nodDiv.getPadre() != null) {// && div.getPadre().getLlaves().size() != 0) {
                //queda actualizar padre
                K info = medio; //nueva llave en padre
                llaves_padDiv = newI.getPadre().getLlaves();
                nodosI_padDiv = newI.getPadre().getNodosI();
                //si se adiciona new llave al final

                if (llaves_padDiv.size() == 0) {
                    llaves_padDiv.add(info);
                    nodosI_padDiv.add(newI);
                } else if (info.compareTo(llaves_padDiv.get(llaves_padDiv.size() - 1)) > 0) {
                    llaves_padDiv.add(info);
                    nodosI_padDiv.add(newI);
                } else {
                    //si se adiciona en el intermedio
                    for (int i = 0; i < llaves_padDiv.size(); i++) {
                        if (info.compareTo(llaves_padDiv.get(i)) < 0) {
                            //pongo la llave y el puntero
                            llaves_padDiv.add(i, info);
                            nodosI_padDiv.add(i + 1, newI);
                            break;
                        }
                    }
                }
                //actualizo padre
                newI.getPadre().setLlaves(llaves_padDiv);
                newI.getPadre().setNodosI(nodosI_padDiv);
            } else {
                //creo padre
                NodoI<K, O> newRoot = new NodoI<K, O>();
                newRoot.getLlaves().add(medio);
                newRoot.getNodosI().add(nodDiv);
                newRoot.getNodosI().add(newI);

                raiz = newRoot;
                nodDiv.setPadre(raiz);
                newI.setPadre(raiz);
            }//fin mod padre

            //------
            //actualizo puntero Sup
            if (nodSup_nodDiv != null) {
                anidarD(nodSup_nodDiv, nodDiv);
                anidarD(nodSup_nodDiv, newI);
            }

            //----
            //redistribuir entradas entre los dos nodos
            List<PunteroL<K>> divL = nodDiv.getPunL();
            //reubico punteros l del nodo div inicial
            if (!divL.isEmpty() && divL != null) {
                //anidando los hijos con el padre
                NodoI nodI_sup = null;
                NodoH nodH_sup = null;
                PunteroL punLx = null;
                nodDiv.setPunL(new LinkedList<PunteroL<K>>());
                for (int i = 0; i < divL.size(); i++) {
                    punLx = divL.get(i);
                    if (punLx.getSubH() == null) {
                        nodI_sup = punLx.getSubI();
                        nodI_sup.setSupN(null);
                        anidar(nodDiv.getPadre(), nodI_sup);
                    } else {
                        nodH_sup = punLx.getSubH();
                        nodH_sup.setSupN(null);
                        anidar(nodDiv.getPadre(), nodH_sup);
                    }
                }
            }

            if (llaves_padDiv.size() > 2 * m) {
                dividir(newI.getPadre());
            }
        }
    }

    //anal
    /**
     ************************************************************************
     ********************************ELIMINAR*********************************
     * ************************************************************************
     */
    public void eliminar(NodoI nodoInt, K llave, List<O> listOid) {
        NodoH nodHx = buscarNodo(nodoInt, llave);
        List<O> listx = new LinkedList<O>();

        int ndelete = -1;

        if (listOid != null)//linea 7-8-2015 para cuando se eliminan todos los valores asociados a la llave
        {
            for (int i = 0; i < nodHx.getLlaves().size(); i++) {
                if (llave.compareTo((K) nodHx.getLlaves().get(i)) == 0) {

                    listx = (List<O>) nodHx.getOids().get(i);
                    for (int j = listx.size() - 1; j >= 0; j--) {
                        for (int k = listOid.size() - 1; k >= 0; k--) {
                            if(!listx.isEmpty())
                            if (listx.get(j).equals(listOid.get(k))) {
                                listx.remove(j);
                                
                            }
                        }
                    }
                    if(listx.isEmpty()){
                        ndelete = i;
                    }
                    break;
                }

            }
        }
        if (ndelete != -1) {
            //eliminar entrada
            if (listx.isEmpty()) {
                nodHx.getLlaves().remove(ndelete);
                nodHx.getOids().remove(ndelete);

                //si subdesbordamiento
                if (nodHx.getLlaves().isEmpty()) {
//                    limpiarEntradas(raiz);
//                    raiz = null;
                    return;

                    //si subdesbordamiento
                } else if (raiz.getLlaves().size() == 0) {
                } else if (nodHx.getLlaves().size() < m) {
                    //unir con hermano derecho, eliminar entrada padre
                    unir(nodHx);
                }
            }
        }
    }

    public void eliminar(NodoH nodoHoj, K llave, List<O> listOids) {


        List<O> listx = new LinkedList<O>();
        int ndelete = -1;
        for (int i = 0; i < nodoHoj.getLlaves().size(); i++) {
            if (llave.compareTo((K) nodoHoj.getLlaves().get(i)) == 0) {
                ndelete = i;
                listx = (List<O>) nodoHoj.getOids().get(i);
                listx.removeAll(listOids);
                break;
            }
        }

        //eliminar entrada
        if (ndelete != -1) {
            if (listOids.isEmpty() || listx.isEmpty()) {
                nodoHoj.getLlaves().remove(ndelete);
                nodoHoj.getOids().remove(ndelete);

                //si subdesbordamiento
                if (nodoHoj.getLlaves().size() < m) {
                    //unir con hermano derecho, eliminar entrada padre
                    unir(nodoHoj);
                }
            }
        }
    }

    //*********************************************************
    //************************UNIR*****************************
    //*********************************************************
    //REVISAR POROFUNDAMENTE
    public void unir(NodoH nodUnir) {
        NodoH nodHerm = nodUnir.getHermano();
        if (nodHerm != null && nodHerm.getPadre() == nodUnir.getPadre()) {

            NodoI nodSup_nodUnir = null;
            NodoI nodSup_nodHerm = null;
            if (nodUnir.getSupN() != null && (nodHerm.getSupN() != null)) {
                //eliminar punteros L
                nodSup_nodUnir = nodUnir.getSupN();
                List<PunteroL<K>> listSup_nodUnir = nodSup_nodUnir.getPunL();
                for (int i = 0; i < listSup_nodUnir.size(); i++) {


                    if (listSup_nodUnir.get(i).getSubH() == nodUnir) {
                        listSup_nodUnir.remove(i);
                        nodUnir.setSupN(null);
                        break;
                    }
                }
                nodSup_nodHerm = nodHerm.getSupN();
                List<PunteroL<K>> listSup_nodHerm = nodSup_nodHerm.getPunL();
                for (int i = 0; i < listSup_nodHerm.size(); i++) {

                    if (listSup_nodHerm.get(i).getSubH() == nodHerm) {
                        listSup_nodHerm.remove(i);
                        nodHerm.setSupN(null);
                        break;
                    }
                }
            }

            List<K> llaves_nodUnir = nodUnir.getLlaves();
            List<List<O>> listOid_nodUnir = nodUnir.getOids();

            llaves_nodUnir.addAll(nodHerm.getLlaves());
            listOid_nodUnir.addAll(nodHerm.getOids());

            nodUnir.setHermano(nodHerm.getHermano());
            //OJO
            //adiciono a pun N el nuevo nodo
            if (nodSup_nodHerm != null && nodSup_nodUnir != null) {
                anidarD(nodSup_nodHerm, nodUnir);
            }

            List<NodoI> nodosH_padUnir = nodUnir.getPadre().getNodosH();
            List<NodoI> llaves_padunir = nodUnir.getPadre().getLlaves();
            int indHerm = nodosH_padUnir.indexOf(nodHerm);

            if (indHerm > 0) {
                nodosH_padUnir.remove(indHerm);
                llaves_padunir.remove(indHerm - 1);
            }

            if (llaves_nodUnir.size() > 2 * m) {
                dividir(nodUnir);
            } else {
                if (llaves_padunir.size() < m) { //&& lk.size()!=0
                    unir(nodUnir.getPadre());
                }
            }

        } else {
            List<NodoH> nodosH_padUnir = nodUnir.getPadre().getNodosH();
            int indUnir = nodosH_padUnir.indexOf(nodUnir);
            if (indUnir > 0) {
                unir(nodosH_padUnir.get(indUnir - 1));
            }
        }
    }

    public void unir(NodoI nodUnir) {
//        //si nodo no es raiz
        if (nodUnir.getPadre() != null) {
            if (nodUnir.getNodosH().isEmpty()) {
                //NodoI pad = nunir.getPadre();
                List<NodoI> nodosI_padUnir = nodUnir.getPadre().getNodosI();
                List<NodoI> llaves_padUnir = nodUnir.getPadre().getLlaves();
                int indUnir = nodosI_padUnir.indexOf(nodUnir);
                NodoI nodHerm = new NodoI();

                //si nodo no esta referenciado por ultimo puntero padre
                if (indUnir < (nodosI_padUnir.size() - 1)) {
                    nodHerm = nodosI_padUnir.get(indUnir + 1);
                    int indSup_padUnir = -1;
                    if (nodUnir.getPadre().getSupN() != null) {
                        List listPunL_padUnir = nodUnir.getPadre().getSupN().getPunL();
                        PunteroL punLx;
                        indSup_padUnir = 0;
                        for (int i = 0; i < listPunL_padUnir.size(); i++) {
                            punLx = (PunteroL) listPunL_padUnir.get(i);
                            if (punLx.getSubI() == nodUnir.getPadre()) {
                                indSup_padUnir = i;
                                break;
                            }
                        }
                    }
                    List<NodoI> nodosI_nodUnir = nodUnir.getNodosI();
                    List<NodoI> nodosI_nodHerm = nodHerm.getNodosI();

                    //punteros L
                    NodoI nodSup_nodUnir = null;
                    NodoI nodSup_nodHerm = null;
                    //PN
                    if (nodUnir.getSupN() != null && (nodHerm.getSupN() != null)) {
                        nodSup_nodUnir = nodUnir.getSupN();

                        List<PunteroL<K>> listSup_nodUnir = nodSup_nodUnir.getPunL();
                        for (int i = 0; i < listSup_nodUnir.size(); i++) {
                            if (listSup_nodUnir.get(i).getSubI() == nodUnir) {
                                listSup_nodUnir.remove(i);
                                nodUnir.setSupN(null);
                                break;
                            }
                        }

                        nodSup_nodHerm = nodHerm.getSupN();
                        List<PunteroL<K>> listSup_nodHerm = nodSup_nodHerm.getPunL();
                        for (int i = 0; i < listSup_nodHerm.size(); i++) {
                            if (listSup_nodHerm.get(i).getSubI() == nodHerm) {
                                listSup_nodHerm.remove(i);
                                nodHerm.setSupN(null);
                                break;
                            }
                        }

                    } else if (nodUnir.getSupN() != null) {
                        nodSup_nodUnir = nodUnir.getSupN();
                        List<PunteroL<K>> listSup_nodUnir = nodSup_nodUnir.getPunL();
                        for (int i = 0; i < listSup_nodUnir.size(); i++) {
                            if (listSup_nodUnir.get(i).getSubI() == nodUnir) {
                                listSup_nodUnir.remove(i);
                                nodUnir.setSupN(null);
                                break;
                            }
                        }
                    } else if (nodHerm.getSupN() != null) {
                        //trabajo con los punteros
                        nodSup_nodHerm = nodHerm.getSupN();
                        List<PunteroL<K>> listSup_nodHerm = nodSup_nodHerm.getPunL();
                        for (int i = 0; i < listSup_nodHerm.size(); i++) {
                            if (listSup_nodHerm.get(i).getSubI() == nodHerm) {
                                listSup_nodHerm.remove(i);
                                nodHerm.setSupN(null);
                                break;
                            }
                        }
                    }

                    if (nodSup_nodHerm != null && nodSup_nodUnir != null) {
                    } else if (nodSup_nodHerm != null) {
                        for (int i = 0; i < nodosI_nodHerm.size(); i++) {
                            anidar(nodSup_nodHerm, nodosI_nodHerm.get(i));
                            //regla3(nunir);
                        }
                    } else if (nodSup_nodUnir != null) {
                        for (int i = 0; i < nodosI_nodUnir.size(); i++) {
                            anidar(nodSup_nodUnir, nodosI_nodUnir.get(i));
                            //regla3(nunir);
                        }
                    }

                    //conformacion del nuevo nodo
                    List<K> llaves_nodUnir = new LinkedList<K>();
                    List<NodoI> nodoI_nodUnir = new LinkedList<NodoI>();
                    List<PunteroL> listPunL_nodUnir = new LinkedList<PunteroL>();

                    llaves_nodUnir = nodUnir.getLlaves();
                    nodoI_nodUnir = nodUnir.getNodosI();
                    listPunL_nodUnir = nodUnir.getPunL();
                    //puntero
                    if (nodUnir.getPunL() == null) {
                        nodUnir.setPunL(new LinkedList<PunteroL>());
                    }

                    K e = (K) llaves_padUnir.get(indUnir);
                    llaves_nodUnir.add(e);
                    llaves_nodUnir.addAll(nodHerm.getLlaves());
                    nodoI_nodUnir.addAll(nodHerm.getNodosI());


                    if (nodHerm.getPunL() != null) {
                        listPunL_nodUnir.addAll(nodHerm.getPunL());
                        //redefino punteros padres de sub de nh
                        List listPunL_nodHerm = nodHerm.getPunL();
                        PunteroL punLx = null;
                        for (int i = 0; i < listPunL_nodHerm.size(); i++) {
                            punLx = (PunteroL) listPunL_nodHerm.get(i);
                            if (punLx.getSubH() == null) {
                                punLx.getSubI().setSupN(nodUnir);
                            } else {
                                punLx.getSubH().setSupN(nodUnir);
                            }
                        }
                    }

                    indUnir = nodosI_padUnir.indexOf(nodHerm);
                    nodosI_padUnir.remove(indUnir);
                    NodoI nodIx;
                    for (int i = 0; i < nodUnir.getNodosI().size(); i++) {
                        nodIx = (NodoI) nodUnir.getNodosI().get(i);
                        nodIx.setPadre(nodUnir);
                    }

                    if (indUnir > 0) {
                        llaves_padUnir.remove(indUnir - 1);
                    }

                    //11-8-15
                    if (nodUnir.getLlaves().size() > 2 * m) {
                        dividir(nodUnir);
                    } else {
                        //OJO
                        if (nodUnir.getPadre().getLlaves().size() == 0) {
                            //if (nodUnir.getPadre() == null) {
                            if (nodUnir.getPadre().getPunL() != null && !nodUnir.getPadre().getPunL().isEmpty()) {
                                nodUnir.getPunL().addAll(nodUnir.getPadre().getPunL());

                                List listPunL_padUnir = nodUnir.getPadre().getPunL();
                                PunteroL punLx = null;
                                for (int i = 0; i < listPunL_padUnir.size(); i++) {
                                    punLx = (PunteroL) listPunL_padUnir.get(i);
                                    if (punLx.getSubH() == null) {
                                        punLx.getSubI().setSupN(nodUnir);
                                    } else {
                                        punLx.getSubH().setSupN(nodUnir);
                                    }
                                }
                            }
                            //apuntar punteros en ambos sentidos
                            if (nodUnir.getPadre().getSupN() != null) {
                                List punLSup_padUnir = nodUnir.getPadre().getSupN().getPunL();
                                nodUnir.setSupN(nodUnir.getPadre().getSupN());

                                //tomo puntero de localizacion de padre y lo actualizo
                                PunteroL punLx = (PunteroL) punLSup_padUnir.get(indSup_padUnir);
                                punLx.setSubI(nodUnir);
                            }
                            nodUnir.setPadre(null);
                            raiz = nodUnir;
                        } else if (llaves_padUnir.size() < m && nodUnir.getPadre().getPadre() != null) {
                            unir(nodUnir.getPadre());
                        }
                    }

                } else {
                    unir(nodosI_padUnir.get(indUnir - 1));
                }
            } else {
                //caso nodo inter que apunta a nodos hojas
                //NodoI pad = nunir.getPadre();
                List<NodoI> nodosI_padUnir = nodUnir.getPadre().getNodosI();
                List<K> llaves_padUnir = nodUnir.getPadre().getLlaves();
                int indUnir = nodosI_padUnir.indexOf(nodUnir);
                NodoI nodHerm = new NodoI();

                //si nodo no esta referenciado por ultimo puntero padre
                if (indUnir < (nodosI_padUnir.size() - 1)) {
                    nodHerm = nodosI_padUnir.get(indUnir + 1);
                    int indSup_padUnir = -1;
                    if (nodUnir.getPadre().getSupN() != null) {
                        List punL_padUnir = nodUnir.getPadre().getSupN().getPunL();
                        PunteroL punLx;
                        indSup_padUnir = 0;
                        for (int i = 0; i < punL_padUnir.size(); i++) {
                            punLx = (PunteroL) punL_padUnir.get(i);
                            if (punLx.getSubI() == nodUnir.getPadre()) {
                                indSup_padUnir = i;
                                break;
                            }
                        }
                    }
                    List<NodoH> nodosH_nodUnir = nodUnir.getNodosH();
                    List<NodoH> nodosH_nodHerm = nodHerm.getNodosH();

                    NodoI nodSup_nodUnir = null;
                    NodoI nodSup_nodHerm = null;
                    if (nodUnir.getSupN() != null && (nodHerm.getSupN() != null)) {
                        //eliminar punteros L
                        nodSup_nodUnir = nodUnir.getSupN();
                        List<PunteroL<K>> lp1 = nodSup_nodUnir.getPunL();
                        for (int i = 0; i < lp1.size(); i++) {
                            if (lp1.get(i).getSubI() == nodUnir) {
                                lp1.remove(i);
                                nodUnir.setSupN(null);
                                break;
                            }
                        }
                        nodSup_nodHerm = nodHerm.getSupN();
                        List<PunteroL<K>> lp2 = nodSup_nodHerm.getPunL();
                        for (int i = 0; i < lp2.size(); i++) {
                            if (lp2.get(i).getSubI() == nodHerm) {
                                lp2.remove(i);
                                nodHerm.setSupN(null);
                                break;
                            }
                        }

                    } else if (nodUnir.getSupN() != null) {
                        nodSup_nodUnir = nodUnir.getSupN();
                        List<PunteroL<K>> lp1 = nodSup_nodUnir.getPunL();

                        for (int i = 0; i < lp1.size(); i++) {
                            if (lp1.get(i).getSubI() == nodUnir) {
                                lp1.remove(i);
                                nodUnir.setSupN(null);
                                break;
                            }
                        }

                    } else if (nodHerm.getSupN() != null) {
                        nodSup_nodHerm = nodHerm.getSupN();
                        List<PunteroL<K>> lp2 = nodSup_nodHerm.getPunL();
                        for (int i = 0; i < lp2.size(); i++) {
                            if (lp2.get(i).getSubI() == nodHerm) {
                                lp2.remove(i);
                                nodHerm.setSupN(null);
                                break;
                            }
                        }
                    }

                    if (nodSup_nodHerm != null && nodSup_nodUnir != null) {
                    } else if (nodSup_nodHerm != null) {
                        for (int i = 0; i < nodosH_nodHerm.size(); i++) {
                            anidar(nodSup_nodHerm, nodosH_nodHerm.get(i));
                            //regla3(nunir);
                        }
                    } else if (nodSup_nodUnir != null) {
                        for (int i = 0; i < nodosH_nodUnir.size(); i++) {
                            anidar(nodSup_nodUnir, nodosH_nodUnir.get(i));
                            //regla3(nunir);
                        }
                    }
                    List<K> llaves_nodUnir = new LinkedList<K>();
                    List<NodoH> nodH_nodUnir = new LinkedList<NodoH>();

                    llaves_nodUnir = nodUnir.getLlaves();
                    nodH_nodUnir = nodUnir.getNodosH();

                    List<PunteroL> punL_nodUnir = new LinkedList<PunteroL>();
                    if (nodUnir.getPunL() == null) {
                        nodUnir.setPunL(new LinkedList<PunteroL>());
                    }
                    punL_nodUnir = nodUnir.getPunL();

                    K e = (K) llaves_padUnir.get(indUnir);
                    llaves_nodUnir.add(e);
                    llaves_nodUnir.addAll(nodHerm.getLlaves());
                    nodH_nodUnir.addAll(nodHerm.getNodosH());
                    if (nodHerm.getPunL() != null) {
                        punL_nodUnir.addAll(nodHerm.getPunL());
                        List punL_nodHerm = nodHerm.getPunL();
                        PunteroL punLx = null;
                        for (int i = 0; i < punL_nodHerm.size(); i++) {
                            punLx = (PunteroL) punL_nodHerm.get(i);
                            if (punLx.getSubH() == null) {
                                punLx.getSubI().setSupN(nodUnir);
                            } else {
                                punLx.getSubH().setSupN(nodUnir);
                            }
                        }
                    }

                    indUnir = nodosI_padUnir.indexOf(nodHerm);
                    //lk.remove(numi);
                    nodosI_padUnir.remove(indUnir);

                    NodoH nodoHx;
                    for (int i = 0; i < nodUnir.getNodosH().size(); i++) {
                        nodoHx = (NodoH) nodUnir.getNodosH().get(i);
                        nodoHx.setPadre(nodUnir);
                    }
                    if (nodSup_nodHerm != null && nodSup_nodUnir != null) {
                        anidarD(nodSup_nodHerm, nodUnir);
                    }
                    if (indUnir > 0) {
                        llaves_padUnir.remove(indUnir - 1);
                    }

                    if (nodUnir.getLlaves().size() > 2 * m) {
                        dividir(nodUnir);
                    } else {

                        if (nodUnir.getPadre().getLlaves().size() == 0) {
                            //if (nodUnir.getPadre() == null) {
                            if (nodUnir.getPadre().getPunL() != null && !nodUnir.getPadre().getPunL().isEmpty()) {
                                nodUnir.getPunL().addAll(nodUnir.getPadre().getPunL());

                                List punL_padUnir = nodUnir.getPadre().getPunL();
                                PunteroL punLx = null;
                                for (int i = 0; i < punL_padUnir.size(); i++) {
                                    punLx = (PunteroL) punL_padUnir.get(i);
                                    if (punLx.getSubH() == null) {
                                        punLx.getSubI().setSupN(nodUnir);
                                    } else {
                                        punLx.getSubH().setSupN(nodUnir);
                                    }
                                }
                            }
                            if (nodUnir.getPadre().getSupN() != null) {
                                List punLSup_padUnir = nodUnir.getPadre().getSupN().getPunL();
                                nodUnir.setSupN(nodUnir.getPadre().getSupN());

                                //tomo puntero de localizacion de padre y lo actualizo
                                PunteroL punLx = (PunteroL) punLSup_padUnir.get(indSup_padUnir);
                                punLx.setSubI(nodUnir);
                            }
                            nodUnir.setPadre(null);
                            raiz = nodUnir;
                        } else if (llaves_padUnir.size() < m && nodUnir.getPadre().getPadre() != null) {
                            unir(nodUnir.getPadre());
                        }
                    }

                } else {
                    unir(nodosI_padUnir.get(indUnir - 1));
                }
            }
        } else if (raiz.getLlaves().size() == 0) {
            if (raiz.getNodosH().isEmpty()) {
                NodoI NodoIx = (NodoI) raiz.getNodosI().get(0);
                NodoIx.getPunL().addAll(raiz.getPunL());
                if (raiz.getSupN() != null) {
                    NodoIx.setSupN(raiz.getSupN());
                }
                raiz = (NodoI) raiz.getNodosI().get(0);
                if (raiz.getLlaves().size() > 2 * m) {
                    dividir(raiz);
                }
            }
//            else {
//                //ini = (NodoH) raiz.getNodosH().get(0);
//            }
        }
    }

    //verifico si loshijos tienen como puntero supN el mismo de ser asi supN apunta a padre
//    private void regla3(NodoI nSub) {
//        if (true) {
//            return;
//        }
//        if (!nSub.getNodosH().isEmpty()) {
//
//            NodoH uno = (NodoH) nSub.getNodosH().get(0);
//            if (uno.getSupN() != null) {
//                NodoI nSup = uno.getSupN();
//                //NodoH ultimo = (NodoH) nSub.getNodosH().get(nSub.getNodosH().size() - 1);
//                NodoH dos = null;
//                for (int i = 1; i < nSub.getNodosH().size(); i++) {
//                    dos = (NodoH) nSub.getNodosH().get(nSub.getNodosH().size() - 1);
//                    if (uno.getSupN() != dos.getSupN()) {
//                        return;
//                    }
//                }
//
//                NodoH nodH = null;
//                List<PunteroL> puntL = uno.getSupN().getPunL();
//                for (int i = 0; i < nSub.getNodosH().size(); i++) {
//                    nodH = (NodoH) nSub.getNodosH().get(i);
//                    for (int j = 0; j < puntL.size(); j++) {
//                        if (puntL.get(j).getSubH() == nodH) {
//                            puntL.remove(j);
//                            nodH.setSupN(null);
//                            break;
//                        }
//                    }
//                }
//                anidarD(nSup, nSub);
//                //regla3(nSub.getPadre());
//            }
//        } else {
//            if (nSub.getNodosH() == null) {
//                NodoI uno = (NodoI) nSub.getNodosI().get(0);
//                if (uno.getSupN() != null) {
//                    NodoI nSup = uno.getSupN();
//                    //NodoH ultimo = (NodoH) nSub.getNodosH().get(nSub.getNodosH().size() - 1);
//                    NodoI dos = null;
//                    for (int i = 1; i < nSub.getNodosI().size(); i++) {
//                        dos = (NodoI) nSub.getNodosI().get(nSub.getNodosI().size() - 1);
//                        if (uno.getSupN() != dos.getSupN()) {
//                            return;
//                        }
//                    }
//
//                    NodoI nodI = null;
//                    List<PunteroL> puntL = uno.getSupN().getPunL();
//                    for (int i = 0; i < nSub.getNodosI().size(); i++) {
//                        nodI = (NodoI) nSub.getNodosI().get(i);
//                        for (int j = 0; j < puntL.size(); j++) {
//                            if (puntL.get(j).getSubI() == nodI) {
//                                puntL.remove(j);
//                                nodI.setSupN(null);
//                                break;
//                            }
//                        }
//                    }
//                    anidarD(nSup, nSub);
//                    //regla3(nSub.getPadre());
//                }
//            }
//        }
//    }
    private NodoH buscarNodo(NodoI in, K kdelete) {
        List<K> llaves = in.getLlaves();
        List<NodoI> nodosI = in.getNodosI();
        List<NodoH> nodosH = in.getNodosH();

        NodoH<K, O> h = new NodoH<K, O>();
        //caso que apunte a nodo interno
        if (nodosH.isEmpty()) {

            for (int i = 0; i < llaves.size(); i++) {
                //si el minimo es menor que K busco en el nodo hijo IzqK
                if (kdelete.compareTo(llaves.get(i)) < 0) {
                    h = buscarNodo(nodosI.get(i), kdelete);
                    break;
                }

                // si llegue hasta la ultima llave sin que menor<Ki busco en la ultima rama
                if (i == (llaves.size() - 1)) {
                    h = buscarNodo(nodosI.get(i + 1), kdelete);
                }
            }
            /////////////////////////////////////////////////////////
        } else {
            if (nodosH.size() == 1) {
                h = nodosH.get(0);
            } else {
                //caso que apunte a nodo hoja
                for (int i = 0; i < llaves.size(); i++) {

                    //si el minimo es menor que K busco en el nodo hijo IzqK
                    if (kdelete.compareTo(llaves.get(i)) < 0) {
                        h = nodosH.get(i);
                        break;
                    }
                    // si llegue hasta la ultima llave sin que menor<Ki busco en la ultima rama
                    if (i == (llaves.size() - 1)) {
                        h = nodosH.get(i + 1);
                    }
                }
            }
        }
        return h;
    }

    public K minimo(NodoI vmin) {
        K r = null;
        while (vmin.getNodosH().isEmpty()) {
            vmin = (NodoI) vmin.getNodosI().get(0);
        }
        //r = minimo((NodoH) vmin.getNodosH().get(0));
        NodoH nh = (NodoH) vmin.getNodosH().get(0);
        r = (K) nh.getLlaves().get(0);
        
        if(r.equals(minArb)){
            r = minGlob;
        }
        return r;
    }

    public K maximo(NodoI vmax) {
        K r = null;
        while (vmax.getNodosH().isEmpty()) {
            vmax = (NodoI) vmax.getNodosI().get(vmax.getNodosI().size() - 1);
        }
        //r = maximo((NodoH) vmax.getNodosH().get(vmax.getNodosH().size() - 1));
        NodoH nh = (NodoH) vmax.getNodosH().get(vmax.getNodosH().size() - 1);
        if (nh.getHermano() != null) {
            nh = nh.getHermano();
            r = (K) nh.getLlaves().get(0);
        } else {
            r = (K) nh.getLlaves().get(nh.getLlaves().size() - 1);
        }
        
        if(r.equals(maxArb)){
            r = maxGlob;
        }
        return r;
    }

    public K minimo(NodoH vmin) {
        K r = null;
        r = (K) vmin.getLlaves().get(0);
        if(r.equals(minArb)){
            r = minGlob;
        }
        return r;
    }

    public K maximo(NodoH vmax) {
        K r = null;
        if (vmax.getHermano() != null) {
            return minimo(vmax.getHermano());
        }
        r = (K) vmax.getLlaves().get(vmax.getLlaves().size() - 1);
        if(r.equals(maxArb)){
            r = maxGlob;
        }
        return r;
    }
    
    public K minimoS(NodoI vmin) {
        K r = null;
        while (vmin.getNodosH().isEmpty()) {
            vmin = (NodoI) vmin.getNodosI().get(0);
        }
        //r = minimo((NodoH) vmin.getNodosH().get(0));
        NodoH nh = (NodoH) vmin.getNodosH().get(0);
        r = (K) nh.getLlaves().get(0);
        
        if(r.equals(minSub)){
            r = minGlob;
        }
        return r;
    }

    public K maximoS(NodoI vmax) {
        K r = null;
        while (vmax.getNodosH().isEmpty()) {
            vmax = (NodoI) vmax.getNodosI().get(vmax.getNodosI().size() - 1);
        }
        //r = maximo((NodoH) vmax.getNodosH().get(vmax.getNodosH().size() - 1));
        NodoH nh = (NodoH) vmax.getNodosH().get(vmax.getNodosH().size() - 1);
        if (nh.getHermano() != null) {
            nh = nh.getHermano();
            r = (K) nh.getLlaves().get(0);
        } else {
            r = (K) nh.getLlaves().get(nh.getLlaves().size() - 1);
        }
        
        if(r.equals(maxSub)){
            r = maxGlob;
        }
        return r;
    }

    public K minimoS(NodoH vmin) {
        K r = null;
        r = (K) vmin.getLlaves().get(0);
        if(r.equals(minSub)){
            r = minGlob;
        }
        return r;
    }

    public K maximoS(NodoH vmax) {
        K r = null;
        if (vmax.getHermano() != null) {
            return minimo(vmax.getHermano());
        }
        r = (K) vmax.getLlaves().get(vmax.getLlaves().size() - 1);
        if(r.equals(maxSub)){
            r = maxGlob;
        }
        return r;
    }

//    private NodoI cubre(NodoI spN, K minimo, K maximo) {
//        NodoI r = null;
//        K minR = minimo(spN);
//        K maxR = maximo(spN);
////        if (minR == null && maxR == null) {
////            r = spN;
////        }else if (minimo == null) {
////            r = cubre(spN.getPadre(), minimo, maximo);
////
////        }else if (maximo == null) {
////            r = cubre(spN.getPadre(), minimo, maximo);
////
////        } else if (minR == null && maximo.compareTo(maxR) < 0) {
////            r = spN;
////        } else if (minimo.compareTo(minR) >= 0 && maxR == null) {
////            r = spN;
////        } else 
//        if (minimo.compareTo(minR) >= 0 && maximo.compareTo(maxR) < 0) {
//            r = spN;
//        } else if (spN.getPadre() != null) {
//            r = cubre(spN.getPadre(), minimo, maximo);
//
//        } else {
//            r = spN;
//        }
//        return r;
//    }
    /**
     ************************************************************************
     *********************************ANIDAR**********************************
     * ************************************************************************
     */
    public void anidarD(NodoI nSup, NodoI nSub) {
        //si nSup es nodo hoja
        if (rango(nSup, nSub) || nSup.getPadre() == null) {
            anidar(nSup, nSub);
        } else {
            anidarD(nSup.getPadre(), nSub);
        }
    }

    public void anidarD(NodoI nSup, NodoH nSub) {
        //si nSup es nodo hoja
        if (rango(nSup, nSub) || nSup.getPadre() == null) {
            anidar(nSup, nSub);
        } else {
            anidarD(nSup.getPadre(), nSub);
        }
    }
    
    public boolean anidar(NodoI nSup, NodoI nSub) {
        //si nSup es nodo hoja
        if (nSup.getPadre() != null) {
            if (!rango(nSup, nSub)) {
                return false;
            }
        }
        //si nSup apunta a nodos hojas
        if (nSup.getNodosI().isEmpty()) {
            K minSub = minimo(nSub);
            K maxSub = maximo(nSub);
            PunteroL<K> newP = new PunteroL<K>(minSub, maxSub, nSub);
            nSub.setSupN(nSup);
            if (nSup.getPunL() == null) {
                nSup.setPunL(new LinkedList<PunteroL>());
            }
            nSup.getPunL().add(newP);

        } else {
            if (nSub.getNodosH().isEmpty()) {
                List<NodoI<K, O>> hijosN = nSup.getNodosI();
                List<NodoI<K, O>> hijosn = nSub.getNodosI();

                List<NodoI<K, O>> tmphijosIn = new LinkedList<NodoI<K, O>>();
                if (nSub.getNodosH().isEmpty()) {
                    tmphijosIn = new LinkedList<NodoI<K, O>>();
                    NodoI tmpI = null;
                    for (int i = 0; i < nSub.getNodosI().size(); i++) {
                        tmpI = (NodoI) nSub.getNodosI().get(i);
                        tmphijosIn.add(tmpI);
                    }
                }
                //trato anidar n en los hijos de N
                boolean bandera = false;
                for (int i = 0; i < hijosN.size(); i++) {
                    if (rango(hijosN.get(i), nSub)) {
                        anidar(hijosN.get(i), nSub);
                        bandera = true;
                    }
                }

                //si no se pudo lo de arriba, trato de anidar los hijos de n en los hijos de N
                boolean noanid = true;
                if (!bandera) {
                    //puede optimizarse los for trato de anidar en lo hijos de N los hijos de n

                    List<NodoI<K, O>> tmphijosn2 = new LinkedList<NodoI<K, O>>();
                    NodoI tmpH2 = null;
                    for (int i = 0; i < nSub.getNodosI().size(); i++) {
                        tmpH2 = (NodoI) nSub.getNodosI().get(i);
                        tmphijosn2.add(tmpH2);
                    }

                    for (int i = 0; i < hijosN.size(); i++) {
                        for (int j = 0; j < tmphijosn2.size(); j++) {

                            if (rango(hijosN.get(i), tmphijosn2.get(j))) {
                                anidar(hijosN.get(i), tmphijosn2.get(j));
                                tmphijosIn.remove(tmphijosn2.get(j));
                                noanid = false;
                            }
                        }

                        tmphijosn2 = null;
                        tmphijosn2 = new LinkedList<NodoI<K, O>>();
                        for (int j = 0; j < tmphijosIn.size(); j++) {
                            tmpH2 = (NodoI) tmphijosIn.get(j);
                            tmphijosn2.add(tmpH2);
                        }
                    }

                    //si ninguno pudo anidarse, anidar n en N
                    if (noanid) {
                        K minSub = minimo(nSub);
                        K maxSub = maximo(nSub);
                        PunteroL<K> newP = new PunteroL<K>(minSub, maxSub, nSub);
                        nSub.setSupN(nSup);
                        if (nSup.getPunL() == null) {
                            nSup.setPunL(new LinkedList<PunteroL>());
                        }
                        nSup.getPunL().add(newP);

                        //si alguno se anido, anidar en N los hijos de n que no fueron anidados
                    } else {
                        if (tmphijosIn.size() > 0) {
                            for (int i = 0; i < tmphijosIn.size(); i++) {
                                K minSub = minimo(tmphijosIn.get(i));
                                K maxSub = maximo(tmphijosIn.get(i));
                                PunteroL<K> newP = new PunteroL<K>(minSub, maxSub, tmphijosIn.get(i));
                                tmphijosIn.get(i).setSupN(nSup);
                                if (nSup.getPunL() == null) {
                                    nSup.setPunL(new LinkedList<PunteroL>());
                                }
                                nSup.getPunL().add(newP);
                            }
                        }
                    }
                }
                return true;

                //caso hijos hojas de n
            } else {
                List<NodoI<K, O>> hijosN = nSup.getNodosI();
                List<NodoH<K, O>> hijosn = nSub.getNodosH();

                List<NodoH<K, O>> tmphijosn = new LinkedList<NodoH<K, O>>();
                NodoH tmpH = null;
                for (int i = 0; i < nSub.getNodosH().size(); i++) {
                    tmpH = (NodoH) nSub.getNodosH().get(i);
                    tmphijosn.add(tmpH);
                }

                //trato anidar n en los hijos de N
                boolean bandera = false;
                for (int i = 0; i < hijosN.size(); i++) {
                    if (rango(hijosN.get(i), nSub)) {
                        anidar(hijosN.get(i), nSub);
                        bandera = true;
                    }
                }

                //si no se pudo lo de arriba, trato de anidar los hijos de n en los hijos de N
                boolean noanid = true;
                if (!bandera) {
                    //puede optimizarse los for
                    List<NodoH<K, O>> tmphijosn2 = new LinkedList<NodoH<K, O>>();
                    NodoH tmpH2 = null;
                    for (int i = 0; i < nSub.getNodosH().size(); i++) {
                        tmpH2 = (NodoH) nSub.getNodosH().get(i);
                        tmphijosn2.add(tmpH2);
                    }

                    for (int i = 0; i < hijosN.size(); i++) {
                        for (int j = 0; j < tmphijosn2.size(); j++) {
                            if (rango(hijosN.get(i), tmphijosn2.get(j))) {
                                anidar(hijosN.get(i), tmphijosn2.get(j));
                                tmphijosn.remove(tmphijosn2.get(j));
                                noanid = false;
                            }
                        }

                        tmphijosn2 = null;
                        tmphijosn2 = new LinkedList<NodoH<K, O>>();
                        for (int j = 0; j < tmphijosn.size(); j++) {
                            tmpH2 = (NodoH) tmphijosn.get(j);
                            tmphijosn2.add(tmpH2);
                        }
                    }
                    //si ninguno pudo anidarse, anidar n en N
                    if (noanid) {
                        K minSub = minimo(nSub);
                        K maxSub = maximo(nSub);
                        PunteroL<K> newP = new PunteroL<K>(minSub, maxSub, nSub);
                        nSub.setSupN(nSup);
                        if (nSup.getPunL() == null) {
                            nSup.setPunL(new LinkedList<PunteroL>());
                        }
                        nSup.getPunL().add(newP);
                        //si alguno se anido, anidar en N los hijos de n que no fueron anidados
                    } else {
                        if (tmphijosn.size() > 0) {
                            for (int i = 0; i < tmphijosn.size(); i++) {
                                K minSub = minimo(tmphijosn.get(i));
                                K maxSub = maximo(tmphijosn.get(i));
                                PunteroL<K> newP = new PunteroL<K>(minSub, maxSub, tmphijosn.get(i));
                                tmphijosn.get(i).setSupN(nSup);
                                if (nSup.getPunL() == null) {
                                    nSup.setPunL(new LinkedList<PunteroL>());
                                }
                                nSup.getPunL().add(newP);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean anidar(NodoI nSup, NodoH nSub) {
        if (nSup.getPadre() != null && !rango(nSup, nSub)) {
            return false;
        }

        if (!nSup.getNodosH().isEmpty()) {
            K minSub = minimo(nSub);
            K maxSub = maximo(nSub);
            PunteroL<K> newP = new PunteroL<K>(minSub, maxSub, nSub);
            nSub.setSupN(nSup);
            if (nSup.getPunL() == null) {
                nSup.setPunL(new LinkedList<PunteroL>());
            }
            nSup.getPunL().add(newP);
        } else {
            List<NodoI<K, O>> hijosN = nSup.getNodosI();
            //trato anidar n en los hijos de N
            boolean bandera = false;
            for (int i = 0; i < hijosN.size(); i++) {
                if (rango(hijosN.get(i), nSub)) {
                    anidar(hijosN.get(i), nSub);
                    bandera = true;
                }
            }
            if (!bandera) {
                K minSub = minimo(nSub);
                K maxSub = maximo(nSub);
                PunteroL<K> newP = new PunteroL<K>(minSub, maxSub, nSub);
                nSub.setSupN(nSup);
                if (nSup.getPunL() == null) {
                    nSup.setPunL(new LinkedList<PunteroL>());
                }
                nSup.getPunL().add(newP);
                //si alguno se anido, anidar en N los hijos de n que no fueron anidados
            }
        }
        return true;
    }

    public boolean rango(NodoI nSup, NodoI nSub) {
        K minSup = minimoS(nSup);
        K maxSup = maximoS(nSup);
        K minSub = minimo(nSub);
        K maxSub = maximo(nSub);
        if (nSup.getPadre() == null) {
            return true;
        } else {
            if (minSup.compareTo(minSub) <= 0
                    && maxSup.compareTo(maxSub) >= 0) {
                return true;
            }
        }
        return false;
    }

    public boolean rango(NodoI nSup, NodoH nSub) {
        K minSup = minimoS(nSup);
        K maxSup = maximoS(nSup);
        K minSub = minimo(nSub);
        K maxSub = maximo(nSub);
        if (nSup.getPadre() == null) {
            return true;
        } else {
            if (minSup.compareTo(minSub) <= 0
                    && maxSup.compareTo(maxSub) >= 0) {
                return true;
            }
        }
        return false;
    }

    public String enOrden(NodoI in) {
        List<K> llaves = in.getLlaves();
        List<NodoI> nodosI = in.getNodosI();
        List<NodoH> nodosH = in.getNodosH();
        String res = "";
        if (nodosH.isEmpty()) {
            for (int i = 0; i < llaves.size(); i++) {
                res += enOrden(nodosI.get(i));
                res += "* " + llaves.get(i).toString();
            }
            res += enOrden(nodosI.get(llaves.size()));
        } else {
            for (int i = 0; i < llaves.size(); i++) {
                res += enOrden(nodosH.get(i));
                res += "* " + llaves.get(i).toString();
            }
            res += enOrden(nodosH.get(llaves.size()));
        }
        return res;
    }

    public String enOrden(NodoH h) {
        List<K> llaves = h.getLlaves();
        String res = "";
        for (int i = 0; i < llaves.size(); i++) {
            res += "* " + llaves.get(i).toString();
        }
        return res;
    }

    private int nivel() {
        int niv = 1;
        NodoI tmp = raiz;
        if (raiz != null) {
            while (!tmp.getNodosI().isEmpty()) {
                tmp = (NodoI) tmp.getNodosI().get(0);
                niv++;
            }
        }
        return niv;
    }

    @Override
    public void coordenadas() {
        inicializar();
        xorigen = Global.origenx;
        yorigen = Global.origeny;
        niveles = nivel();

        //cantidad de nodos hojas posibles
        int cantidadNodos = (int) Math.pow((2 * m), niveles);

        //dimension de un nodo con su espacio
        int tamannoNodo = 2 * m * (Global.largoValor) + Global.horizontalEspacio;
        largoArbol = Math.max(cantidadNodos * tamannoNodo, 2000);
        altoArbol = (niveles + 1) * (Global.verticalEspacio) + (niveles + 2) * (Global.altoNodo);

        Global.origeny = yorigen;
        //Global.origenx = xorigen + largoArbol + Global.distanciaArboles;
        //Global.largoLienzo += largoArbol + Global.distanciaArboles;
        Global.altoLienzo = Math.max(Global.altoLienzo, altoArbol);

        if (raiz != null) {
            coordenadasH();
            raiz.coordenadas();
        } else {
            Global.origenx = Global.origenx + 200 + Global.distanciaArboles;
            Global.largoLienzo = Global.largoLienzo + 500 + Global.distanciaArboles;
        }
    }

    public void coordenadasH() {
        //localizo el primer nodo hoja
        NodoI tmpI = raiz;
        if (tmpI == null) {
            return;
        }
        while (tmpI.getNodosH().isEmpty()) {
            tmpI = (NodoI) tmpI.getNodosI().get(0);
        }
        NodoH tmpH = (NodoH) tmpI.getNodosH().get(0);

        tmpH.xorigen = xorigen;
        tmpH.yorigen = (niveles) * (Global.verticalEspacio) + (niveles + 1) * (Global.altoNodo);
        tmpH.yorigen = tmpH.yorigen;

        int tmpx = tmpH.xorigen;
        int tmpy = tmpH.yorigen;
        tmpH.coordenadas();

        while (tmpH.getHermano() != null) {
            tmpx += (tmpH.getLlaves().size()) * (Global.largoValor) + Global.horizontalEspacio;
            tmpH = tmpH.getHermano();

            tmpH.xorigen = tmpx;
            tmpH.yorigen = tmpy;

            tmpH.coordenadas();
        }
        tmpx += (tmpH.getLlaves().size()) * (Global.largoValor) + Global.horizontalEspacio;
        if (tmpx - Global.origenx < 200) {
            Global.origenx = Global.origenx + 200 + Global.distanciaArboles;
            Global.largoLienzo = Global.largoLienzo + 500 + Global.distanciaArboles;
        } else {
            Global.origenx = tmpx + Global.distanciaArboles;
            Global.largoLienzo = tmpx + Global.distanciaArboles;
        }
    }

    @Override
    public void pintar(Graphics g) {
        //determinar niveles y dimensiones
        //pintar raiz
        if (raiz != null) {
            raiz.pintar(g);
        }
    }

    public void inicializar() {
        xorigen = 0;
        yorigen = 0;
        largoArbol = 0;
        altoArbol = 0;
        niveles = 0;
    }

    public void limpiarEntradas(NodoI in) {
        List<PunteroL> punL = new LinkedList<PunteroL>();
        ///VERRR
        if (!(in.getPunL() == null)) {
            punL = in.getPunL();
        }
        List<O> b = new LinkedList<O>();
        //caso que apunte a nodo interno
        if (in.getNodosH().isEmpty()) {
            List<NodoI> nodosI = in.getNodosI();
            for (int i = 0; i < nodosI.size(); i++) {
                //si el minimo es menor que K busco en el nodo hijo IzqK
                limpiarEntradas(nodosI.get(i));
            }
            /////////////////////////////////////////////////////////
        } else {
            List<NodoH> nodosH = in.getNodosH();
            //caso que apunte a nodo hoja
            for (int i = 0; i < nodosH.size(); i++) {
                //si el minimo es menor que K busco en el nodo hijo IzqK
                limpiarEntradas(nodosH.get(i));
            }
        }//abrebiar luego de que okkk
        if (!punL.isEmpty()) {
            for (int i = 0; i < punL.size(); i++) {
                PunteroL ln = punL.get(i);
                if (ln.getSubH() == null) {
                    ln.getSubI().setSupN(null);
                    punL.remove(i);
                    i--;
                } else {
                    ln.getSubH().setSupN(null);
                    punL.remove(i);
                    i--;
                }
            }
        }
        //si se vino de un puntero L
        if (in.getSupN() != null) {
            punL = in.getSupN().getPunL();
            if (!punL.isEmpty()) {
                for (int i = 0; i < punL.size(); i++) {
                    PunteroL ln = punL.get(i);
                    if (ln.getSubI() == in) {
                        punL.remove(i);
                        break;
                    }
                }
                in.setSupN(null);
            }
        }
    }

    public void limpiarEntradas(NodoH in) {
        List<PunteroL> punL = new LinkedList<PunteroL>();
        //si se vino de un puntero L
        if (in.getSupN() != null) {
            punL = in.getSupN().getPunL();
            if (!punL.isEmpty()) {
                for (int i = 0; i < punL.size(); i++) {
                    PunteroL ln = punL.get(i);
                    if (ln.getSubH() == in) {
                        punL.remove(i);
                        break;
                    }
                }
                in.setSupN(null);
            }
        }
    }
}
