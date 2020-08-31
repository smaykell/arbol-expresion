package arbolgrafico;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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
    private JButton btnGenerarArbol;
    private JPanel pnlArbolPintado;
    private ArbodeExpresiones simuladorArbol;
    private JScrollPane spArbolPintado;

    public Ventana() {
        simuladorArbol = new ArbodeExpresiones();
        initComponents();
    }

    private void initComponents() {
        width = 600;
        height = 600;

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
        spArbolPintado.setBounds(10, 50, 565, 500);
        pnlArbolPintado = new JPanel();
        pnlArbolPintado.setLayout(new GridLayout(1, 1));
        pnlArbolPintado.setBackground(Color.WHITE);
        pnlArbolPintado.setPreferredSize(new Dimension(565, 500));
        contenedor.add(spArbolPintado);

        spArbolPintado.setViewportView(pnlArbolPintado);

        add(contenedor);
    }

    public void renderizarArbol() {
        pnlArbolPintado.removeAll();
        pnlArbolPintado.add(simuladorArbol.getdibujo()).setBounds(0, 0, 565, 500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();
        if (origen.equals(btnGenerarArbol)) {
            try {
                simuladorArbol.construirArbol(txtExpresion.getText());
                renderizarArbol();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo generar el arbol de expresión");
            }
        }
    }

}