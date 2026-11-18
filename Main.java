import View.VentanaPrincipal;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // las ventanas se crean en el hilo de Swing
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
