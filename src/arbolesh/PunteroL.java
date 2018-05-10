/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolesh;

/**
 *
 * @author casa
 */
public class PunteroL<K extends Comparable<K>> {
    private K min;
    private K max;
    private NodoI subI;
    private NodoH subH;

    public PunteroL(K min, K max, NodoI subI) {
        this.min = min;
        this.max = max;
        this.subI = subI;
    }

    public PunteroL(K min, K max, NodoH subH) {
        this.min = min;
        this.max = max;
        this.subH = subH;
    }

    public K getMax() {
        return max;
    }

    public void setMax(K max) {
        this.max = max;
    }

    public K getMin() {
        return min;
    }

    public void setMin(K min) {
        this.min = min;
    }

    public NodoH getSubH() {
        return subH;
    }

    public void setSubH(NodoH subH) {
        this.subH = subH;
    }

    public NodoI getSubI() {
        return subI;
    }

    public void setSubI(NodoI subI) {
        this.subI = subI;
    }
}
