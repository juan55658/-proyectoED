package Controller;

import Estructuras.ArbolLibros;
import Estructuras.ListaEnlazada;
import Estructuras.Pila;
import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import java.time.LocalDate;

public class Biblioteca {
    ListaEnlazada<Cliente> clientes;
    ArbolLibros libros;
    // pila con las devoluciones, la ultima devolucion queda arriba
    Pila<String> historial;

    public Biblioteca() {
        clientes = new ListaEnlazada<>();
        libros = new ArbolLibros();
        historial = new Pila<>();

        JOptionPane.showMessageDialog(null, "Bienvenido a la biblioteca c: ");
        MenuPrincipal.menuPrincipal(clientes, libros, historial);
    }

    public static void alquilar(ListaEnlazada<Cliente> clientes, ArbolLibros libros) {
        try {
            if (clientes.estaVacia() || libros.estaVacio()) {
                JOptionPane.showMessageDialog(null, "Debe haber al menos un cliente y un libro registrados");
                return;
            }

            int idAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del cliente: "));
            Cliente clienteEncontrado = ClientesMetodos.buscarCliente(clientes, idAux);
            if (clienteEncontrado == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el cliente");
                return;
            }

            if (!clienteEncontrado.getEsEstudiante().equalsIgnoreCase("Si")) {
                JOptionPane.showMessageDialog(null, "Solo los estudiantes pueden alquilar libros");
                return;
            }

            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn del libro: "));
            Libro libroEncontrado = libros.buscar(isbnAux);
            if (libroEncontrado == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
                return;
            }

            if (libroEncontrado.isAlquilado()) {
                if (libroEncontrado.getAlquiladoA() == clienteEncontrado) {
                    JOptionPane.showMessageDialog(null, "Ya tienes este libro alquilado");
                    return;
                }
                // si esta ocupado le ofrecemos entrar a la fila de espera
                int respuesta = JOptionPane.showConfirmDialog(null,
                        "Este libro ya esta alquilado. Desea unirse a la fila de espera?",
                        "Fila de espera", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    FilaEspera.agregar(clienteEncontrado, libroEncontrado);
                }
                return;
            }

            prestar(libroEncontrado, clienteEncontrado);
            JOptionPane.showMessageDialog(null, "Libro alquilado correctamente. Fecha de devolucion: " + libroEncontrado.getFechaDevolucion());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    public static void devolver(ArbolLibros libros, Pila<String> historial) {
        try {
            if (libros.estaVacio()) {
                JOptionPane.showMessageDialog(null, "No hay libros por el momento");
                return;
            }

            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn del libro a devolver: "));
            Libro libroEncontrado = libros.buscar(isbnAux);
            if (libroEncontrado == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
                return;
            }

            if (!libroEncontrado.isAlquilado()) {
                JOptionPane.showMessageDialog(null, "Este libro no esta alquilado");
                return;
            }

            LocalDate hoy = LocalDate.now();
            String registro = libroEncontrado.getTitulo() + " (Isbn " + libroEncontrado.getIsbn() + ") - devuelto el " + hoy;
            if (hoy.isAfter(libroEncontrado.getFechaDevolucion())) {
                JOptionPane.showMessageDialog(null, "Devolucion tardia. Fecha limite era: " + libroEncontrado.getFechaDevolucion());
                registro += " (tardia)";
            } else {
                JOptionPane.showMessageDialog(null, "Libro devuelto correctamente");
            }
            historial.apilar(registro);

            libroEncontrado.setAlquilado(false);
            libroEncontrado.setFechaDevolucion(null);
            libroEncontrado.setAlquiladoA(null);

            // si hay alguien esperando, el libro pasa al primero de la fila
            Cliente siguiente = FilaEspera.atenderSiguiente(libroEncontrado);
            if (siguiente != null) {
                prestar(libroEncontrado, siguiente);
                JOptionPane.showMessageDialog(null, "El libro paso a " + siguiente.getNombre() + " que era el primero en la fila. Fecha de devolucion: " + libroEncontrado.getFechaDevolucion());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    // deja el libro alquilado a un cliente por 15 dias
    private static void prestar(Libro libro, Cliente cliente) {
        libro.setAlquilado(true);
        libro.setAlquiladoA(cliente);
        libro.setFechaDevolucion(LocalDate.now().plusDays(15));
    }

    public static void mostrarHistorial(Pila<String> historial) {
        if (historial.estaVacia()) {
            JOptionPane.showMessageDialog(null, "Todavia no hay devoluciones");
            return;
        }
        // para ver toda la pila la vaciamos pasando todo a una pila auxiliar y despues la devolvemos
        Pila<String> auxiliar = new Pila<>();
        StringBuilder texto = new StringBuilder("Historial de devoluciones (la mas reciente primero):\n");
        while (!historial.estaVacia()) {
            String registro = historial.desapilar();
            texto.append(registro).append("\n");
            auxiliar.apilar(registro);
        }
        while (!auxiliar.estaVacia()) {
            historial.apilar(auxiliar.desapilar());
        }
        JOptionPane.showMessageDialog(null, texto);
    }
}
