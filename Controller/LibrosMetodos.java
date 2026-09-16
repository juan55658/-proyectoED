package Controller;

import Model.Libro;

import javax.swing.*;
import java.util.ArrayList;

public class LibrosMetodos {
    public static void mostrarLibros(ArrayList<Libro> libros) {
        try {
            if (libros.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No hay libros por el momento");
                return;
            }
            StringBuilder aux = new StringBuilder();
            aux.append("Libros: ");
            for (Libro libro : libros) {
                aux.append(libro.MostrarInformacion());
                aux.append("\n");
            }
            JOptionPane.showMessageDialog(null, aux);
        } catch (Exception E ) {
            JOptionPane.showMessageDialog(null, "Error");
        }

    }
    public static void agregarLibros(ArrayList<Libro> libros) {
        try {
            Libro libro = new Libro();
            libros.add(libro);
            JOptionPane.showMessageDialog(null, "Libro agregado correctamente");
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public static void eliminarLibros(ArrayList<Libro> libros) {
        boolean control = false;
        try {
            if (libros.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No hay libros por el momento");
                return;
            }
            // mostrar los libros para escoger el ID
            mostrarLibros(libros);
            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn de el libro que desea eliminar: "));
            for(Libro libro : libros) {
                if (libro.getIsbn() == isbnAux) {
                    libros.remove(libro);
                }
                break;
            }
            if (!control) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
            }

        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public static void editarLibros(ArrayList<Libro> libros) {
        boolean control = false;
        try {
            if (libros.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No hay libros por el momento");
                return;
            }
            // mostrar los clientes para escoger el ID
            mostrarLibros(libros);
            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn de el libro que desea editar: "));
            for(Libro libro : libros) {
                if (libro.getIsbn() == isbnAux) {
                    libro.EditarInformacion();
                }
                break;
            }
            if (!control) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
            }
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

}
