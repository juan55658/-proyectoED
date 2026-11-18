package View;

import Controller.BibliotecaServicio;
import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Pestaña "Préstamos": se escoge un libro en la tabla, un cliente en la lista
// y se pulsa Alquilar o Devolver. A la derecha se ve la fila de espera del libro.
public class PanelPrestamos extends JPanel {
    private BibliotecaServicio servicio;
    private VentanaPrincipal ventana;

    private DefaultTableModel modelo;
    private JTable tabla;
    // la fila i de la tabla es el libro i de esta lista
    private ArrayList<Libro> listaLibros = new ArrayList<>();
    private boolean actualizando = false;

    private JComboBox<String> cmbClientes;
    // el cliente i del combo es el cliente i de esta lista
    private ArrayList<Cliente> listaClientes = new ArrayList<>();

    private DefaultListModel<String> modeloFila;
    private JTextArea areaLibroFila;

    public PanelPrestamos(BibliotecaServicio servicio, VentanaPrincipal ventana) {
        this.servicio = servicio;
        this.ventana = ventana;

        setLayout(new BorderLayout(15, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Estilo.FONDO);
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
        add(crearFila(), BorderLayout.EAST);
        add(crearBarraAcciones(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);
        panel.add(Estilo.titulo("Alquilar y devolver libros"), BorderLayout.NORTH);
        panel.add(Estilo.suave("Paso 1: selecciona un libro.   Paso 2: elige el cliente.   Paso 3: pulsa Alquilar o Devolver."), BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane crearTabla() {
        modelo = Estilo.modelo(new String[]{"ISBN", "Título", "Estado", "Alquilado por", "Devolución", "En fila"});
        tabla = Estilo.tabla(modelo);
        Estilo.anchos(tabla, 80, 220, 100, 150, 110, 60);
        tabla.getColumnModel().getColumn(2).setCellRenderer(Estilo.renderizadorEstado("Disponible", Estilo.ROJO));
        Estilo.centrar(tabla, 4, 5);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !actualizando) {
                mostrarFila();
            }
        });
        return new JScrollPane(tabla);
    }

    // panel de la derecha con la fila de espera del libro seleccionado
    private JPanel crearFila() {
        modeloFila = new DefaultListModel<>();
        JList<String> lista = new JList<>(modeloFila);
        lista.setFont(Estilo.FUENTE);
        lista.setFixedCellHeight(28);
        lista.setFocusable(false);

        areaLibroFila = Estilo.ayuda();
        areaLibroFila.setPreferredSize(new Dimension(10, 40));

        JPanel encabezado = new JPanel(new BorderLayout(0, 4));
        encabezado.setOpaque(false);
        encabezado.add(Estilo.titulo("Fila de espera"), BorderLayout.NORTH);
        encabezado.add(areaLibroFila, BorderLayout.CENTER);

        JPanel tarjeta = Estilo.tarjeta();
        tarjeta.setLayout(new BorderLayout(0, 12));
        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(new JScrollPane(lista), BorderLayout.CENTER);
        tarjeta.setPreferredSize(new Dimension(300, 0));
        return tarjeta;
    }

    private JPanel crearBarraAcciones() {
        cmbClientes = new JComboBox<>();
        cmbClientes.setFont(Estilo.FUENTE);
        cmbClientes.setPreferredSize(new Dimension(300, 34));

        JButton btnAlquilar = Estilo.boton("Alquilar", Estilo.AZUL);
        JButton btnDevolver = Estilo.boton("Devolver", Estilo.VERDE);
        btnAlquilar.setToolTipText("Alquila el libro seleccionado al cliente elegido (15 días)");
        btnDevolver.setToolTipText("Registra la devolución del libro seleccionado");
        btnAlquilar.addActionListener(e -> alquilar());
        btnDevolver.addActionListener(e -> devolver());

        JPanel barra = Estilo.tarjeta();
        barra.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
        barra.add(new JLabel("Cliente:"));
        barra.add(cmbClientes);
        barra.add(btnAlquilar);
        barra.add(btnDevolver);
        return barra;
    }

    // vuelve a cargar la tabla y la lista de clientes
    public void actualizar() {
        Libro libroSeleccionado = getLibro();
        Cliente clienteSeleccionado = getCliente();

        actualizando = true;
        listaLibros = servicio.getLibros();
        modelo.setRowCount(0);
        for (Libro libro : listaLibros) {
            String quien = libro.getAlquiladoA() == null ? "-" : libro.getAlquiladoA().getNombre();
            modelo.addRow(new Object[]{
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.isAlquilado() ? "Alquilado" : "Disponible",
                    quien,
                    libro.isAlquilado() ? libro.getFechaDevolucion().toString() : "-",
                    libro.getFilaEspera().getTamano()});
        }

        listaClientes = servicio.getClientes();
        cmbClientes.removeAllItems();
        for (Cliente cliente : listaClientes) {
            String texto = cliente.getId() + " - " + cliente.getNombre();
            if (!cliente.isEstudiante()) {
                texto += " (no estudiante)";
            }
            cmbClientes.addItem(texto);
        }
        actualizando = false;

        int pos = listaClientes.indexOf(clienteSeleccionado);
        if (pos >= 0) {
            cmbClientes.setSelectedIndex(pos);
        }
        int fila = listaLibros.indexOf(libroSeleccionado);
        if (fila >= 0) {
            tabla.setRowSelectionInterval(fila, fila);
        }
        mostrarFila();
    }

    private Libro getLibro() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= listaLibros.size()) {
            return null;
        }
        return listaLibros.get(fila);
    }

    private Cliente getCliente() {
        int pos = cmbClientes.getSelectedIndex();
        if (pos < 0 || pos >= listaClientes.size()) {
            return null;
        }
        return listaClientes.get(pos);
    }

    // llena la lista de la derecha con la cola del libro seleccionado
    private void mostrarFila() {
        modeloFila.clear();
        Libro libro = getLibro();
        if (libro == null) {
            areaLibroFila.setText("Selecciona un libro para ver quién lo está esperando.");
            return;
        }
        areaLibroFila.setText("Libro: " + libro.getTitulo());

        ArrayList<Cliente> fila = libro.getFilaEspera().aLista();
        if (fila.isEmpty()) {
            modeloFila.addElement("Nadie está esperando este libro");
        }
        int posicion = 1;
        for (Cliente cliente : fila) {
            modeloFila.addElement(posicion + ". " + cliente.getNombre());
            posicion++;
        }
    }

    private void alquilar() {
        Libro libro = getLibro();
        Cliente cliente = getCliente();
        if (libro == null) {
            ventana.mostrarError("Primero selecciona un libro de la tabla.");
            return;
        }
        if (cliente == null) {
            ventana.mostrarError("Primero elige un cliente. Si no hay ninguno, créalo en la pestaña Clientes.");
            return;
        }

        try {
            boolean alquilado = servicio.alquilar(cliente, libro);
            if (alquilado) {
                ventana.actualizarTodo();
                ventana.mostrarMensaje("\"" + libro.getTitulo() + "\" alquilado a " + cliente.getNombre()
                        + ". Fecha de devolución: " + libro.getFechaDevolucion());
            } else {
                // el libro esta ocupado: se ofrece la fila de espera
                int respuesta = JOptionPane.showConfirmDialog(ventana,
                        "\"" + libro.getTitulo() + "\" ya está alquilado.\n¿Quieres que " + cliente.getNombre()
                                + " entre a la fila de espera?",
                        "Libro no disponible", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) {
                    int posicion = servicio.unirseFila(cliente, libro);
                    ventana.actualizarTodo();
                    ventana.mostrarMensaje(cliente.getNombre() + " quedó en la posición " + posicion
                            + " de la fila de \"" + libro.getTitulo() + "\"");
                }
            }
        } catch (IllegalArgumentException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }

    private void devolver() {
        Libro libro = getLibro();
        if (libro == null) {
            ventana.mostrarError("Primero selecciona el libro que se va a devolver.");
            return;
        }

        try {
            String mensaje = servicio.devolver(libro);
            ventana.actualizarTodo();
            ventana.mostrarMensaje("Libro devuelto: \"" + libro.getTitulo() + "\"");
            ventana.mostrarInfo(mensaje);
        } catch (IllegalArgumentException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }
}
