package arbolgrafico;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ArbolExpresionGrafico extends JPanel {

    private static final long serialVersionUID = 8174307315556607306L;

    private ArbolExpresion miArbol;
    private HashMap<Nodo, Rectangle> posicionNodos = null;
    private HashMap<Nodo, Dimension> subtreeSizes = null;
    private boolean dirty = true;
    private int parent2child = 20, child2child = 30;
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;

    public ArbolExpresionGrafico(ArbolExpresion miArbol) {
        this.miArbol = miArbol;
        this.setBackground(Color.WHITE);
        posicionNodos = new HashMap<>();
        subtreeSizes = new HashMap<>();
        dirty = true;
        repaint();
    }

    private void calcularPosiciones() {
        posicionNodos.clear();
        subtreeSizes.clear();
        Nodo root = this.miArbol.getRaiz();
        if (root != null) {
            calcularTamañoSubarbol(root);
            calcularPosicion(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    private Dimension calcularTamañoSubarbol(Nodo n) {
        if (n == null)
            return new Dimension(0, 0);

        Dimension ld = calcularTamañoSubarbol(n.getNodoIzquierdo());
        Dimension rd = calcularTamañoSubarbol(n.getNodoDerecho());

        int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int w = ld.width + child2child + rd.width;

        Dimension d = new Dimension(w, h);
        subtreeSizes.put(n, d);

        return d;
    }

    private void calcularPosicion(Nodo n, int left, int right, int top) {
        if (n == null)
            return;

        Dimension ld = (Dimension) subtreeSizes.get(n.getNodoIzquierdo());
        if (ld == null)
            ld = empty;

        Dimension rd = (Dimension) subtreeSizes.get(n.getNodoDerecho());
        if (rd == null)
            rd = empty;

        int center = 0;

        if (right != Integer.MAX_VALUE)
            center = right - rd.width - child2child / 2;
        else if (left != Integer.MAX_VALUE)
            center = left + ld.width + child2child / 2;
        int width = fm.stringWidth(n.getInformacion() + "");

        posicionNodos.put(n, new Rectangle(center - width / 2 - 3, top, width + 6, fm.getHeight()));

        calcularPosicion(n.getNodoIzquierdo(), Integer.MAX_VALUE, center - child2child / 2,
                top + fm.getHeight() + parent2child);
        calcularPosicion(n.getNodoDerecho(), center + child2child / 2, Integer.MAX_VALUE,
                top + fm.getHeight() + parent2child);
    }

    private void dibujarArbol(Graphics2D g, Nodo n, int puntox, int puntoy, int yoffs) {
        if (n == null)
            return;
        Rectangle r = (Rectangle) posicionNodos.get(n);
        g.draw(r);
        g.drawString(n.getInformacion() + "", r.x + 3, r.y + yoffs);

        if (puntox != Integer.MAX_VALUE)

            g.drawLine(puntox, puntoy, (int) (r.x + r.width / 2), r.y);

        dibujarArbol(g, n.getNodoIzquierdo(), (int) (r.x + r.width / 2), r.y + r.height, yoffs);
        dibujarArbol(g, n.getNodoDerecho(), (int) (r.x + r.width / 2), r.y + r.height, yoffs);

    }

    public void paint(Graphics g) {
        super.paint(g);
        fm = g.getFontMetrics();

        if (dirty) {
            calcularPosiciones();
            dirty = false;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        dibujarArbol(g2d, this.miArbol.getRaiz(), Integer.MAX_VALUE, Integer.MAX_VALUE,
                fm.getLeading() + fm.getAscent());
        fm = null;
    }

}
