package Controller;

import Estructuras.ArbolLibros;
import Model.Libro;

import javax.swing.*;

public class LibrosMetodos {
    public static void mostrarLibros(ArbolLibros libros) {
        try {
            if (libros.estaVacio()) {
                JOptionPane.showMessageDialog(null,"No hay libros por el momento");
                return;
            }
            // el recorrido inorden del arbol los muestra ordenados por isbn
            JOptionPane.showMessageDialog(null, "Libros (ordenados por isbn): \n" + libros.mostrarEnOrden());
        } catch (Exception E ) {
            JOptionPane.showMessageDialog(null, "Error");
        }

    }
    public static void agregarLibros(ArbolLibros libros) {
        try {
            Libro libro = new Libro();
            if (libros.insertar(libro)) {
                JOptionPane.showMessageDialog(null, "Libro agregado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un libro con ese isbn");
            }
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public static void eliminarLibros(ArbolLibros libros) {
        try {
            if (libros.estaVacio()) {
                JOptionPane.showMessageDialog(null,"No hay libros por el momento");
                return;
            }
            // mostrar los libros para escoger el isbn
            mostrarLibros(libros);
            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn de el libro que desea eliminar: "));
            Libro libro = libros.buscar(isbnAux);
            if (libro == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
                return;
            }
            if (libro.isAlquilado()) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar un libro que esta alquilado");
                return;
            }
            libros.eliminar(isbnAux);
            JOptionPane.showMessageDialog(null, "Libro eliminado correctamente");

        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public static void editarLibros(ArbolLibros libros) {
        try {
            if (libros.estaVacio()) {
                JOptionPane.showMessageDialog(null,"No hay libros por el momento");
                return;
            }
            // mostrar los libros para escoger el isbn
            mostrarLibros(libros);
            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn de el libro que desea editar: "));
            Libro libro = libros.buscar(isbnAux);
            if (libro == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
                return;
            }

            // si cambia el isbn hay que sacarlo del arbol y volver a meterlo para que siga ordenado
            int isbnViejo = libro.getIsbn();
            libros.eliminar(isbnViejo);
            try {
                libro.EditarInformacion();
            } catch (Exception E) {
                JOptionPane.showMessageDialog(null, "No se pudo editar el isbn, se deja el anterior");
                libro.setIsbn(isbnViejo);
            }
            if (!libros.insertar(libro)) {
                JOptionPane.showMessageDialog(null, "Ya existe otro libro con ese isbn, se deja el isbn anterior");
                libro.setIsbn(isbnViejo);
                libros.insertar(libro);
            }
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    public static void estadisticasArbol(ArbolLibros libros) {
        JOptionPane.showMessageDialog(null, "Cantidad de libros: " + libros.contar() + "\nAltura del arbol: " + libros.altura());
    }

}
