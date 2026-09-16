package Controller;

import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Biblioteca {
    ArrayList<Libro> biblioteca;
    ArrayList<Cliente> clientes;

    public Biblioteca() {
        biblioteca = new ArrayList<>();
        clientes = new ArrayList<>();

        JOptionPane.showMessageDialog(null, "Bienvenido a la biblioteca c: ");
        MenuPrincipal.menuPrincipal(clientes, biblioteca);
    }

    public static void alquilar(ArrayList<Cliente> clientes, ArrayList<Libro> libros) {
        try {
            if (clientes.isEmpty() || libros.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe haber al menos un cliente y un libro registrados");
                return;
            }

            int idAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del cliente: "));
            Cliente clienteEncontrado = null;
            //Busqueda lineal sencilla
            for (Cliente cliente : clientes) {
                if (cliente.getId() == idAux) {
                    clienteEncontrado = cliente;
                    break;
                }
            }
            if (clienteEncontrado == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el cliente");
                return;
            }

            if (!clienteEncontrado.getEsEstudiante().equalsIgnoreCase("Si")) {
                JOptionPane.showMessageDialog(null, "Solo los estudiantes pueden alquilar libros");
                return;
            }

            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn del libro: "));
            Libro libroEncontrado = null;
            //Busqueda binaria reutilizando codigo
            libroEncontrado = busquedaIsbn(libros,isbnAux);

            if (libroEncontrado == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
                return;
            }

            if (libroEncontrado.isAlquilado()) {
                JOptionPane.showMessageDialog(null, "Este libro ya esta alquilado");
                return;
            }
            // da 15 dias para entregar el libro
            libroEncontrado.setAlquilado(true);
            libroEncontrado.setFechaDevolucion(LocalDate.now().plusDays(15));
            JOptionPane.showMessageDialog(null, "Libro alquilado correctamente. Fecha de devolucion: " + libroEncontrado.getFechaDevolucion());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    public static void devolver(ArrayList<Libro> libros) {
        try {
            if (libros.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay libros por el momento");
                return;
            }

            int isbnAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Isbn del libro a devolver: "));
            Libro libroEncontrado = null;
            //Busqueda binaria reutilizando codigo
            libroEncontrado = busquedaIsbn(libros,isbnAux);

            if (libroEncontrado == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el libro");
                return;
            }

            if (!libroEncontrado.isAlquilado()) {
                JOptionPane.showMessageDialog(null, "Este libro no esta alquilado");
                return;
            }

            LocalDate hoy = LocalDate.now();
            if (hoy.isAfter(libroEncontrado.getFechaDevolucion())) {
                JOptionPane.showMessageDialog(null, "Devolucion tardia. Fecha limite era: " + libroEncontrado.getFechaDevolucion());
            } else {
                JOptionPane.showMessageDialog(null, "Libro devuelto correctamente");
            }

            libroEncontrado.setAlquilado(false);
            libroEncontrado.setFechaDevolucion(null);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    //metodos ordenamiento burbuja y busqueda binaria para reutilizar codigo
    public static void organizarIsbn(ArrayList<Libro> libros) {
        try {

            //usamos ordenamiento de burbuja
            for (int i = 0; i < libros.size() - 1; i++) {
                for (int j = 0; j < libros.size() - 1 - i; j++) {
                    if (libros.get(j).getIsbn() > libros.get(j + 1).getIsbn()) {
                        Libro temp = libros.get(j);
                        libros.set(j, libros.get(j + 1));
                        libros.set(j + 1, temp);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public static Libro busquedaIsbn(ArrayList<Libro> libros,int isbn) {
        int inicio = 0;
        int fin = libros.size() - 1;

        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay libros por el momento");
            return null;
        }
        //organizamos la lista antes de usar busqueda binaria
        organizarIsbn(libros);

        //busqueda binaria
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if(libros.get(medio).getIsbn() == isbn) {
                return libros.get(medio);
            } else if (isbn < libros.get(medio).getIsbn() ) {
                fin = (medio - 1);
            } else if (isbn > libros.get(medio).getIsbn()) {
                inicio = (medio + 1);
            }
        }
        return null;
    }
}