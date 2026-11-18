package View;

import Controller.BibliotecaServicio;
import Model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Pestaña "Libros": tabla a la izquierda y formulario a la derecha.
// Si no hay ningun libro seleccionado el formulario sirve para agregar;
// si se selecciona uno de la tabla sirve para editarlo o eliminarlo.
public class PanelLibros extends JPanel {
    private BibliotecaServicio servicio;
    private VentanaPrincipal ventana;

    private DefaultTableModel modelo;
    private JTable tabla;
    // la fila i de la tabla es el libro i de esta lista
    private ArrayList<Libro> listaActual = new ArrayList<>();
    private boolean actualizando = false;

    private JTextField txtIsbn, txtTitulo, txtAutor;
    private JLabel lblFormulario, lblResumen;
    private JTextArea areaAyuda;
    private JButton btnAgregar, btnGuardar, btnEliminar, btnLimpiar;

    public PanelLibros(BibliotecaServicio servicio, VentanaPrincipal ventana) {
        this.servicio = servicio;
        this.ventana = ventana;

        setLayout(new BorderLayout(15, 0));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Estilo.FONDO);
        add(crearTabla(), BorderLayout.CENTER);
        add(crearFormulario(), BorderLayout.EAST);
    }

    private JPanel crearTabla() {
        modelo = Estilo.modelo(new String[]{"ISBN", "Título", "Autor", "Estado", "Devolución", "En fila"});
        tabla = Estilo.tabla(modelo);
        Estilo.anchos(tabla, 90, 210, 200, 100, 110, 70);
        tabla.getColumnModel().getColumn(3).setCellRenderer(Estilo.renderizadorEstado("Disponible", Estilo.ROJO));
        Estilo.centrar(tabla, 4, 5);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !actualizando) {
                alCambiarSeleccion();
            }
        });

        lblResumen = Estilo.suave("");

        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        panel.add(Estilo.titulo("Libros registrados"), BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(lblResumen, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearFormulario() {
        txtIsbn = Estilo.campo();
        txtTitulo = Estilo.campo();
        txtAutor = Estilo.campo();

        lblFormulario = Estilo.titulo("Nuevo libro");
        areaAyuda = Estilo.ayuda();

        JPanel encabezado = new JPanel(new BorderLayout(0, 4));
        encabezado.setOpaque(false);
        encabezado.add(lblFormulario, BorderLayout.NORTH);
        encabezado.add(areaAyuda, BorderLayout.CENTER);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        Estilo.agregarFila(campos, 0, "ISBN", txtIsbn);
        Estilo.agregarFila(campos, 1, "Título", txtTitulo);
        Estilo.agregarFila(campos, 2, "Autor", txtAutor);

        btnAgregar = Estilo.boton("Agregar", Estilo.AZUL);
        btnGuardar = Estilo.boton("Guardar", Estilo.AZUL);
        btnEliminar = Estilo.boton("Eliminar", Estilo.ROJO);
        btnLimpiar = Estilo.boton("Limpiar", Estilo.GRIS);
        btnAgregar.setToolTipText("Agrega un libro nuevo con los datos del formulario");
        btnGuardar.setToolTipText("Guarda los cambios del libro seleccionado");
        btnEliminar.setToolTipText("Elimina el libro seleccionado");
        btnLimpiar.setToolTipText("Borra el formulario y quita la selección");
        btnAgregar.addActionListener(e -> agregar());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // con Enter en cualquier campo se agrega o se guarda, segun el modo
        for (JTextField campo : new JTextField[]{txtIsbn, txtTitulo, txtAutor}) {
            campo.addActionListener(e -> {
                if (btnAgregar.isEnabled()) {
                    agregar();
                } else {
                    guardar();
                }
            });
        }

        JPanel botones = new JPanel(new GridLayout(2, 2, 8, 8));
        botones.setOpaque(false);
        botones.add(btnAgregar);
        botones.add(btnGuardar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        JPanel tarjeta = Estilo.tarjeta();
        tarjeta.setLayout(new BorderLayout(0, 12));
        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(campos, BorderLayout.CENTER);
        tarjeta.add(botones, BorderLayout.SOUTH);

        JPanel derecha = new JPanel(new BorderLayout());
        derecha.setOpaque(false);
        derecha.setPreferredSize(new Dimension(340, 0));
        derecha.add(tarjeta, BorderLayout.NORTH);

        modoNuevo();
        return derecha;
    }

    // vuelve a cargar la tabla con los datos actuales
    public void actualizar() {
        Libro seleccionado = getSeleccionado();

        actualizando = true;
        listaActual = servicio.getLibros();
        modelo.setRowCount(0);
        for (Libro libro : listaActual) {
            modelo.addRow(new Object[]{
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.isAlquilado() ? "Alquilado" : "Disponible",
                    libro.isAlquilado() ? libro.getFechaDevolucion().toString() : "-",
                    libro.getFilaEspera().getTamano()});
        }
        actualizando = false;

        lblResumen.setText("Libros: " + servicio.cantidadLibros() + "   |   Altura del árbol: " + servicio.alturaArbol());

        // si el libro seleccionado sigue existiendo se vuelve a seleccionar
        int fila = listaActual.indexOf(seleccionado);
        if (fila >= 0) {
            tabla.setRowSelectionInterval(fila, fila);
        } else if (seleccionado != null) {
            limpiarFormulario();
        }
    }

    private Libro getSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= listaActual.size()) {
            return null;
        }
        return listaActual.get(fila);
    }

    private void alCambiarSeleccion() {
        Libro libro = getSeleccionado();
        if (libro == null) {
            modoNuevo();
        } else {
            txtIsbn.setText(String.valueOf(libro.getIsbn()));
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            modoEditar();
        }
    }

    private void modoNuevo() {
        lblFormulario.setText("Nuevo libro");
        areaAyuda.setText("Llena los datos y pulsa Agregar. Para editar o eliminar un libro, haz clic en él en la tabla.");
        btnAgregar.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void modoEditar() {
        lblFormulario.setText("Editar libro");
        areaAyuda.setText("Cambia los datos y pulsa Guardar, o pulsa Eliminar. Con Limpiar vuelves a agregar uno nuevo.");
        btnAgregar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void limpiarFormulario() {
        tabla.clearSelection();
        txtIsbn.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        modoNuevo();
        txtIsbn.requestFocusInWindow();
    }

    // convierte el texto del ISBN en numero (si esta vacio devuelve 0 y el servicio avisa)
    private int leerIsbn() {
        String texto = txtIsbn.getText().trim();
        if (texto.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ISBN debe ser un número entero, sin letras ni espacios");
        }
    }

    private void agregar() {
        try {
            servicio.agregarLibro(txtTitulo.getText(), txtAutor.getText(), leerIsbn());
            ventana.actualizarTodo();
            ventana.mostrarMensaje("Libro agregado: " + txtTitulo.getText().trim());
            limpiarFormulario();
        } catch (IllegalArgumentException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }

    private void guardar() {
        Libro libro = getSeleccionado();
        if (libro == null) {
            return;
        }
        try {
            servicio.editarLibro(libro, txtTitulo.getText(), txtAutor.getText(), leerIsbn());
            ventana.actualizarTodo();
            ventana.mostrarMensaje("Cambios guardados: " + libro.getTitulo());
        } catch (IllegalArgumentException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }

    private void eliminar() {
        Libro libro = getSeleccionado();
        if (libro == null) {
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(ventana,
                "¿Seguro que quieres eliminar \"" + libro.getTitulo() + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            servicio.eliminarLibro(libro);
            ventana.actualizarTodo();
            ventana.mostrarMensaje("Libro eliminado: " + libro.getTitulo());
        } catch (IllegalArgumentException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }
}
