package View;

import Controller.BibliotecaServicio;

import javax.swing.*;
import java.awt.*;

// Ventana principal: un encabezado, las pestañas y una barra abajo donde salen los mensajes.
public class VentanaPrincipal extends JFrame {
    // esto se ejecuta antes de crear la ventana para que el estilo se aplique a todo
    static {
        Estilo.instalar();
    }

    private BibliotecaServicio servicio;
    private JLabel lblEstado;

    private PanelLibros panelLibros;
    private PanelClientes panelClientes;
    private PanelPrestamos panelPrestamos;
    private PanelHistorial panelHistorial;

    public VentanaPrincipal() {
        super("Biblioteca");
        servicio = new BibliotecaServicio();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150, 700);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearPestanas(), BorderLayout.CENTER);
        add(crearBarraEstado(), BorderLayout.SOUTH);

        actualizarTodo();
        setVisible(true);
    }

    private JPanel crearEncabezado() {
        JLabel titulo = new JLabel("Biblioteca");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Gestión de libros, clientes y préstamos");
        subtitulo.setForeground(new Color(191, 219, 254));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Estilo.AZUL_OSCURO);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        panel.add(textos, BorderLayout.WEST);
        return panel;
    }

    private JTabbedPane crearPestanas() {
        panelLibros = new PanelLibros(servicio, this);
        panelClientes = new PanelClientes(servicio, this);
        panelPrestamos = new PanelPrestamos(servicio, this);
        panelHistorial = new PanelHistorial(servicio);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(Estilo.NEGRITA);
        pestanas.addTab("Libros", panelLibros);
        pestanas.addTab("Clientes", panelClientes);
        pestanas.addTab("Préstamos", panelPrestamos);
        pestanas.addTab("Historial", panelHistorial);
        return pestanas;
    }

    private JPanel crearBarraEstado() {
        lblEstado = new JLabel("Bienvenido. Empieza agregando libros en la pestaña Libros y clientes en la pestaña Clientes.");

        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(Estilo.FONDO);
        barra.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Estilo.BORDE),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        barra.add(lblEstado, BorderLayout.CENTER);
        return barra;
    }

    // cuando algo cambia en una pestaña se refrescan todas, asi nunca quedan datos viejos
    public void actualizarTodo() {
        panelLibros.actualizar();
        panelClientes.actualizar();
        panelPrestamos.actualizar();
        panelHistorial.actualizar();
    }

    // mensaje corto en la barra de abajo (cuando todo sale bien)
    public void mostrarMensaje(String texto) {
        lblEstado.setText(texto);
    }

    // ventana de aviso (cuando algo sale mal)
    public void mostrarError(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Atención", JOptionPane.WARNING_MESSAGE);
    }

    public void mostrarInfo(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
