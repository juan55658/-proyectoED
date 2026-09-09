package Controller;

import javax.swing.*;

public class MenuLibros {
    public MenuLibros() {
        int opcion = Integer.parseInt(JOptionPane.showInputDialog("Menu libros: \n1.agregar libros \n2.mostrar libros \n3.editar libros" +
                "\n4. eliminar libros \n5.Volver al menu principal "));
        switch (opcion) {
            case 1:
                agregarLibros();
                return;
            case 2:
                mostrarLibros();
                return;
            case 3:
                editarLibros();
                return;
            case 4:
                eliminarLibros();
                return;
            case 5:

            default:
                JOptionPane.showMessageDialog(null, "Opción incorrecta.");
        }
    }
    public void mostrarLibros() {

    }
    public void agregarLibros() {

    }
    public void eliminarLibros() {

    }
    public void editarLibros() {

    }
}
