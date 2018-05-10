/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;


import arbolesh.ArrayArboles;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
/**
 *
 * @author casa
 */
public class Lienzo extends JComponent {
    public ArrayArboles ab;
    
    public Lienzo() {
        super();
        ab = new ArrayArboles();
    }

    public void pintar(ArrayArboles ab) {
        this.ab = ab;
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        
        
        setPreferredSize(new Dimension(100000, 10000));
        
        //Graphics2D g2 = (Graphics2D) g;
        Global.origenx = 0;
        Global.origeny = 0;

        Global.largoLienzo = 0;
        Global.altoLienzo = 0;
        ab.pintar(g);
        
        
    }
}
