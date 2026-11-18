package Controller;

import Estructuras.ArbolLibros;
import Estructuras.Cola;
import Model.Cliente;
import Model.Libro;

import javax.swing.*;

// maneja la fila de espera de un libro (cada libro tiene su propia cola)
public class FilaEspera {

    public static void agregar(Cliente cliente, Libro libro) {
        Cola<Cliente> fila = libro.getFilaEspera();
        if (fila.contiene(cliente)) {
            JOptionPane.showMessageDialog(null, "Ya estas en la fila de espera de este libro");
            return;
        }
        fila.encolar(cliente);
        JOptionPane.showMessageDialog(null, "Quedaste en la fila de espera. Tu posicion es: " + fila.getTamano());
    }

    // saca al primero de la fila, devuelve null si no hay nadie esperando
    public static Cliente atenderSiguiente(Libro libro) {
        return libro.getFilaEspera().desencolar();
    }

    public static void mostrar(ArbolLibros libros) {
        try {
            if (libros.estaVacio()) {
                JOptionPane.showMessageDialog(null, "No hay libros por el momento");
                return;
            }
            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn del libro: "));
            Libro libro = libros.buscar(isbnAux);
            if (libro == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
                return;
            }

            Cola<Cliente> fila = libro.getFilaEspera();
            if (fila.estaVacia()) {
                JOptionPane.showMessageDialog(null, "Nadie esta en la fila de espera de este libro");
                return;
            }

            // para recorrer una cola sin perder los datos: sacamos cada uno y lo volvemos a meter al final
            StringBuilder texto = new StringBuilder("Fila de espera de " + libro.getTitulo() + ":\n");
            int cantidad = fila.getTamano();
            for (int i = 1; i <= cantidad; i++) {
                Cliente cliente = fila.desencolar();
                texto.append(i).append(". ").append(cliente.getNombre()).append(" (Id ").append(cliente.getId()).append(")\n");
                fila.encolar(cliente);
            }
            JOptionPane.showMessageDialog(null, texto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
}
