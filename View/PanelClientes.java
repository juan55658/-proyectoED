package View;

import Controller.BibliotecaServicio;
import Model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Pestaña "Clientes": funciona igual que la de libros.
// Sin selección el formulario agrega; con un cliente seleccionado edita o elimina.
public class PanelClientes extends JPanel {
    private BibliotecaServicio servicio;
    private VentanaPrincipal ventana;

    private DefaultTableModel modelo;
    private JTable tabla;
    // la fila i de la tabla es el cliente i de esta lista
    private ArrayList<Cliente> listaActual = new ArrayList<>();
    private boolean actualizando = false;

    private JTextField txtNombre, txtDocumento, txtEdad, txtTelefono, txtCorreo;
    private JCheckBox chkEstudiante;
    private JLabel lblFormulario, lblResumen;
    private JTextArea areaAyuda;
    private JButton btnAgregar, btnGuardar, btnEliminar, btnLimpiar;

    public PanelClientes(BibliotecaServicio servicio, VentanaPrincipal ventana) {
        this.servicio = servicio;
        this.ventana = ventana;

        setLayout(new BorderLayout(15, 0));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Estilo.FONDO);
        add(crearTabla(), BorderLayout.CENTER);
        add(crearFormulario(), BorderLayout.EAST);
    }

    private JPanel crearTabla() {
        modelo = Estilo.modelo(new String[]{"Id", "Nombre", "Documento", "Edad", "Teléfono", "Correo", "Estudiante"});
        tabla = Estilo.tabla(modelo);
        Estilo.anchos(tabla, 45, 170, 105, 55, 105, 190, 95);
        tabla.getColumnModel().getColumn(6).setCellRenderer(Estilo.renderizadorEstado("Sí", Estilo.GRIS));
        Estilo.centrar(tabla, 0, 3);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !actualizando) {
                alCambiarSeleccion();
            }
        });

        lblResumen = Estilo.suave("");

        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        panel.add(Estilo.titulo("Clientes registrados"), BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(lblResumen, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearFormulario() {
        txtNombre = Estilo.campo();
        txtDocumento = Estilo.campo();
        txtEdad = Estilo.campo();
        txtTelefono = Estilo.campo();
        txtCorreo = Estilo.campo();
        chkEstudiante = new JCheckBox("Es estudiante (puede alquilar)");
        chkEstudiante.setOpaque(false);

        lblFormulario = Estilo.titulo("Nuevo cliente");
        areaAyuda = Estilo.ayuda();

        JPanel encabezado = new JPanel(new BorderLayout(0, 4));
        encabezado.setOpaque(false);
        encabezado.add(lblFormulario, BorderLayout.NORTH);
        encabezado.add(areaAyuda, BorderLayout.CENTER);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        Estilo.agregarFila(campos, 0, "Nombre", txtNombre);
        Estilo.agregarFila(campos, 1, "Documento", txtDocumento);
        Estilo.agregarFila(campos, 2, "Edad", txtEdad);
        Estilo.agregarFila(campos, 3, "Teléfono", txtTelefono);
        Estilo.agregarFila(campos, 4, "Correo", txtCorreo);
        Estilo.agregarFila(campos, 5, "", chkEstudiante);

        btnAgregar = Estilo.boton("Agregar", Estilo.AZUL);
        btnGuardar = Estilo.boton("Guardar", Estilo.AZUL);
        btnEliminar = Estilo.boton("Eliminar", Estilo.ROJO);
        btnLimpiar = Estilo.boton("Limpiar", Estilo.GRIS);
        btnAgregar.setToolTipText("Agrega un cliente nuevo con los datos del formulario");
        btnGuardar.setToolTipText("Guarda los cambios del cliente seleccionado");
        btnEliminar.setToolTipText("Elimina el cliente seleccionado");
        btnLimpiar.setToolTipText("Borra el formulario y quita la selección");
        btnAgregar.addActionListener(e -> agregar());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // con Enter en cualquier campo se agrega o se guarda, segun el modo
        for (JTextField campo : new JTextField[]{txtNombre, txtDocumento, txtEdad, txtTelefono, txtCorreo}) {
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
        Cliente seleccionado = getSeleccionado();

        actualizando = true;
        listaActual = servicio.getClientes();
        modelo.setRowCount(0);
        for (Cliente cliente : listaActual) {
            modelo.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getDocumento(),
                    cliente.getEdad(),
                    cliente.getTelefono(),
                    cliente.getEmail(),
                    cliente.isEstudiante() ? "Sí" : "No"});
        }
        actualizando = false;

        lblResumen.setText("Clientes: " + listaActual.size() + "   |   Solo los estudiantes pueden alquilar libros");

        int fila = listaActual.indexOf(seleccionado);
        if (fila >= 0) {
            tabla.setRowSelectionInterval(fila, fila);
        } else if (seleccionado != null) {
            limpiarFormulario();
        }
    }

    private Cliente getSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= listaActual.size()) {
            return null;
        }
        return listaActual.get(fila);
    }

    private void alCambiarSeleccion() {
        Cliente cliente = getSeleccionado();
        if (cliente == null) {
            modoNuevo();
        } else {
            txtNombre.setText(cliente.getNombre());
            txtDocumento.setText(cliente.getDocumento());
            txtEdad.setText(String.valueOf(cliente.getEdad()));
            txtTelefono.setText(cliente.getTelefono());
            txtCorreo.setText(cliente.getEmail());
            chkEstudiante.setSelected(cliente.isEstudiante());
            modoEditar();
        }
    }

    private void modoNuevo() {
        lblFormulario.setText("Nuevo cliente");
        areaAyuda.setText("Llena los datos y pulsa Agregar. Para editar o eliminar un cliente, haz clic en él en la tabla.");
        btnAgregar.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void modoEditar() {
        lblFormulario.setText("Editar cliente");
        areaAyuda.setText("Cambia los datos y pulsa Guardar, o pulsa Eliminar. Con Limpiar vuelves a agregar uno nuevo.");
        btnAgregar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void limpiarFormulario() {
        tabla.clearSelection();
        txtNombre.setText("");
        txtDocumento.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        chkEstudiante.setSelected(false);
        modoNuevo();
        txtNombre.requestFocusInWindow();
    }

    // convierte el texto de la edad en numero (si esta vacio devuelve 0 y el servicio avisa)
    private int leerEdad() {
        String texto = txtEdad.getText().trim();
        if (texto.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número entero");
        }
    }

    private void agregar() {
        try {
            servicio.agregarCliente(txtNombre.getText(), txtDocumento.getText(), leerEdad(),
                    txtTelefono.getText(), txtCorreo.getText(), chkEstudiante.isSelected());
            ventana.actualizarTodo();
            ventana.mostrarMensaje("Cliente agregado: " + txtNombre.getText().trim());
            limpiarFormulario();
        } catch (IllegalArgumentException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }

    private void guardar() {
        Cliente cliente = getSeleccionado();
        if (cliente == null) {
            return;
        }
        try {
            servicio.editarCliente(cliente, txtNombre.getText(), txtDocumento.getText(), leerEdad(),
                    txtTelefono.getText(), txtCorreo.getText(), chkEstudiante.isSelected());
            ventana.actualizarTodo();
            ventana.mostrarMensaje("Cambios guardados: " + cliente.getNombre());
        } catch (IllegalArgumentException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }

    private void eliminar() {
        Cliente cliente = getSeleccionado();
        if (cliente == null) {
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(ventana,
                "¿Seguro que quieres eliminar a " + cliente.getNombre() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            servicio.eliminarCliente(cliente);
            ventana.actualizarTodo();
            ventana.mostrarMensaje("Cliente eliminado: " + cliente.getNombre());
        } catch (IllegalArgumentException ex) {
            ventana.mostrarError(ex.getMessage());
        }
    }
}
