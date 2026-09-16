package Controller;

import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import java.util.ArrayList;

public class MenuLibros {
    public static void menuLibros(ArrayList<Libro> libros) {
        Boolean condicion = true;
        while (condicion) {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog("Menu libros: \n1.agregar libros \n2.mostrar libros \n3.editar libros" +
                    "\n4. eliminar libros \n5.Volver al menu principal"));
            switch (opcion) {
                case 1:
                    LibrosMetodos.agregarLibros(libros);
                    break;
                case 2:
                    LibrosMetodos.mostrarLibros(libros);
                    break;
                case 3:
                    LibrosMetodos.editarLibros(libros);
                    break;
                case 4:
                    LibrosMetodos.eliminarLibros(libros);
                    break;
                case 5:
                    condicion = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción incorrecta.");
                    break;
            }
        }
    }

}
