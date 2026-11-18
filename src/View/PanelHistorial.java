package View;

import Controller.BibliotecaServicio;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Pestaña "Historial": muestra las devoluciones. Se guardan en una pila,
// por eso la mas reciente aparece de primera.
public class PanelHistorial extends JPanel {
    private BibliotecaServicio servicio;
    private DefaultListModel<String> modelo;
    private JLabel lblTotal;

    public PanelHistorial(BibliotecaServicio servicio) {
        this.servicio = servicio;

        modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        lista.setFont(Estilo.FUENTE);
        lista.setFixedCellHeight(30);
        lista.setFocusable(false);

        lblTotal = Estilo.suave("");

        JPanel encabezado = new JPanel(new BorderLayout(0, 4));
        encabezado.setOpaque(false);
        encabezado.add(Estilo.titulo("Historial de devoluciones"), BorderLayout.NORTH);
        encabezado.add(Estilo.suave("La devolución más reciente aparece arriba (se guarda en una pila)."), BorderLayout.CENTER);

        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Estilo.FONDO);
        add(encabezado, BorderLayout.NORTH);
        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(lblTotal, BorderLayout.SOUTH);
    }

    public void actualizar() {
        modelo.clear();
        ArrayList<String> historial = servicio.getHistorial();
        if (historial.isEmpty()) {
            modelo.addElement("Todavía no hay devoluciones");
        }
        for (String registro : historial) {
            modelo.addElement(registro);
        }
        lblTotal.setText("Devoluciones registradas: " + historial.size());
    }
}
