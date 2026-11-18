package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Colores, fuentes y componentes que se repiten en todas las pantallas,
// asi cada panel no tiene que configurarlos uno por uno.
public class Estilo {
    public static final Color AZUL = new Color(37, 99, 235);
    public static final Color AZUL_OSCURO = new Color(30, 58, 138);
    public static final Color VERDE = new Color(22, 163, 74);
    public static final Color ROJO = new Color(220, 38, 38);
    public static final Color GRIS = new Color(107, 114, 128);
    public static final Color FONDO = new Color(243, 244, 246);
    public static final Color BORDE = new Color(209, 213, 219);

    public static final Font FUENTE = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font NEGRITA = new Font("SansSerif", Font.BOLD, 14);
    public static final Font TITULO = new Font("SansSerif", Font.BOLD, 18);

    // se llama una sola vez, antes de crear las ventanas
    public static void instalar() {
        UIManager.put("defaultFont", FUENTE);
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "Aceptar");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("Nimbus")) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception e) {
            // si no se puede poner Nimbus se queda el estilo por defecto
        }
    }

    // ---------- textos ----------

    public static JLabel titulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(TITULO);
        label.setForeground(AZUL_OSCURO);
        return label;
    }

    public static JLabel suave(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(GRIS);
        return label;
    }

    // texto de ayuda que se parte en varias lineas segun el ancho que tenga disponible
    public static JTextArea ayuda() {
        JTextArea area = new JTextArea();
        area.setFont(new Font("SansSerif", Font.PLAIN, 13));
        area.setForeground(GRIS);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setFocusable(false);
        area.setOpaque(false);
        area.setBackground(new Color(0, 0, 0, 0));
        area.setBorder(null);
        area.setPreferredSize(new Dimension(10, 56));
        return area;
    }

    // ---------- formularios ----------

    public static JTextField campo() {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE);
        campo.setPreferredSize(new Dimension(180, 32));
        return campo;
    }

    // panel blanco con borde, donde van los formularios
    public static JPanel tarjeta() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        return panel;
    }

    // pone la etiqueta a la izquierda y el campo a la derecha (el panel debe usar GridBagLayout)
    public static void agregarFila(JPanel panel, int fila, String texto, JComponent campo) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = fila;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 0, 5, 12);
        c.gridx = 0;
        panel.add(new JLabel(texto), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 5, 0);
        panel.add(campo, c);
    }

    public static JButton boton(String texto, Color color) {
        return new BotonColor(texto, color);
    }

    // boton plano de color (se pinta a mano para que se vea igual en cualquier computador)
    private static class BotonColor extends JButton {
        private Color color;

        BotonColor(String texto, Color color) {
            super(texto);
            this.color = color;
            setFont(NEGRITA);
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setRolloverEnabled(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color fondo = color;
            if (!isEnabled()) {
                fondo = new Color(209, 213, 219);
            } else if (getModel().isPressed()) {
                fondo = color.darker().darker();
            } else if (getModel().isRollover()) {
                fondo = color.darker();
            }
            g2.setColor(fondo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

            // el texto se dibuja aqui mismo para que se vea igual en cualquier estilo
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(getFont());
            g2.setColor(isEnabled() ? Color.WHITE : GRIS);
            FontMetrics medidas = g2.getFontMetrics();
            int x = (getWidth() - medidas.stringWidth(getText())) / 2;
            int y = (getHeight() - medidas.getHeight()) / 2 + medidas.getAscent();
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
    }

    // ---------- tablas ----------

    // modelo donde las celdas no se pueden editar escribiendo encima
    public static DefaultTableModel modelo(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    public static JTable tabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setFont(FUENTE);
        tabla.setRowHeight(30);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(new Color(191, 219, 254));
        tabla.setSelectionForeground(Color.BLACK);
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setFont(NEGRITA);
        return tabla;
    }

    public static void anchos(JTable tabla, int... anchos) {
        for (int i = 0; i < anchos.length; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    public static void centrar(JTable tabla, int... columnas) {
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        for (int columna : columnas) {
            tabla.getColumnModel().getColumn(columna).setCellRenderer(centro);
        }
    }

    // pinta el texto de verde si es igual a textoVerde y del otro color si no
    public static DefaultTableCellRenderer renderizadorEstado(String textoVerde, Color otroColor) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean seleccionado,
                                                           boolean foco, int fila, int columna) {
                super.getTableCellRendererComponent(tabla, valor, seleccionado, foco, fila, columna);
                setHorizontalAlignment(CENTER);
                setFont(NEGRITA);
                if (!seleccionado) {
                    setForeground(textoVerde.equals(valor) ? VERDE : otroColor);
                }
                return this;
            }
        };
    }
}
