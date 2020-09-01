package arbolgrafico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Ventana extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private int width;
    private int height;
    private JPanel contenedor;
    private JTextField txtExpresion;
    private JTextField txtExpresionPrefija;
    private JTextField txtExpresionInfija;
    private JTextField txtExpresionPostfija;
    private JButton btnGenerarArbol;
    private JPanel pnlArbolPintado;
    private ArbolExpresion simuladorArbol;
    private JScrollPane spArbolPintado;

    public Ventana() {
        simuladorArbol = new ArbolExpresion();
        initComponents();
    }

    private void initComponents() {

        width = 600;
        height = 620;

        setTitle("Arbol de expresión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(width, height);

        contenedor = new JPanel();
        contenedor.setLayout(null);
        contenedor.setBackground(Color.WHITE);
        contenedor.setSize(width, height);

        txtExpresion = new JTextField();
        contenedor.add(txtExpresion).setBounds(10, 10, 200, 30);

        btnGenerarArbol = new JButton();
        btnGenerarArbol.setText("Generar Arbol");
        btnGenerarArbol.addActionListener(this);

        contenedor.add(btnGenerarArbol).setBounds(250, 10, 130, 30);

        spArbolPintado = new JScrollPane();
        spArbolPintado.setBounds(10, 50, 565, 400);
        pnlArbolPintado = new JPanel();
        pnlArbolPintado.setLayout(new BorderLayout());
        pnlArbolPintado.setBackground(Color.WHITE);
        pnlArbolPintado.setPreferredSize(new Dimension(1500, 1000));
        contenedor.add(spArbolPintado);

        spArbolPintado.setViewportView(pnlArbolPintado);

        contenedor.add(new JLabel("Exp. Prefija:")).setBounds(10, 470, 100, 25);
        txtExpresionPrefija = new JTextField();
        contenedor.add(txtExpresionPrefija).setBounds(100, 470, 300, 25);

        contenedor.add(new JLabel("Exp. Infija:")).setBounds(10, 500, 100, 25);
        txtExpresionInfija = new JTextField();
        contenedor.add(txtExpresionInfija).setBounds(100, 500, 300, 25);

        contenedor.add(new JLabel("Exp. Postfija:")).setBounds(10, 530, 100, 25);
        txtExpresionPostfija = new JTextField();
        contenedor.add(txtExpresionPostfija).setBounds(100, 530, 300, 25);

        add(contenedor);
    }

    public void renderizarArbol() {
        pnlArbolPintado.removeAll();
        JPanel arbol = simuladorArbol.getdibujo();
        arbol.setSize(1500, 1000);
        pnlArbolPintado.add(arbol, BorderLayout.CENTER);
        pnlArbolPintado.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();
        if (origen.equals(btnGenerarArbol)) {
            try {
                simuladorArbol.construirArbol(txtExpresion.getText());
                renderizarArbol();
                txtExpresionPrefija.setText(simuladorArbol.getPrefija());
                txtExpresionInfija.setText(simuladorArbol.getInfija());
                txtExpresionPostfija.setText(simuladorArbol.getPostfija());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo generar el arbol de expresión");
            }
        }
    }

}