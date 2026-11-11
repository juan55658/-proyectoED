package Controller;

import Estructuras.ListaEnlazada;
import Estructuras.Nodo;
import Model.Cliente;

import javax.swing.*;

public class ClientesMetodos {
    // contador para que los ids no se repitan aunque se eliminen clientes
    private static int contadorId = 0;

    public static void mostrarClientes(ListaEnlazada<Cliente> clientes) {
        try {
            if (clientes.estaVacia()) {
                JOptionPane.showMessageDialog(null,"No hay clientes por el momento");
                return;
            }
            StringBuilder aux = new StringBuilder();
            aux.append("Clientes: ");
            Nodo<Cliente> actual = clientes.getPrimero();
            while (actual != null) {
                aux.append(actual.getDato().MostrarInformacion());
                aux.append("\n");
                actual = actual.getSiguiente();
            }
            JOptionPane.showMessageDialog(null, aux);
        } catch (Exception E ) {
            JOptionPane.showMessageDialog(null, "Error");
        }

    }
    public static void agregarClientes(ListaEnlazada<Cliente> clientes) {
        try {
            Cliente cliente = new Cliente(contadorId);
            clientes.agregar(cliente);
            contadorId++;
            JOptionPane.showMessageDialog(null, "Cliente agregado correctamente");
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    // busqueda recursiva: revisa el nodo actual y si no es, sigue con el siguiente
    public static Cliente buscarCliente(ListaEnlazada<Cliente> clientes, int id) {
        return buscarClienteRec(clientes.getPrimero(), id);
    }
    private static Cliente buscarClienteRec(Nodo<Cliente> actual, int id) {
        if (actual == null) {
            return null;
        }
        if (actual.getDato().getId() == id) {
            return actual.getDato();
        }
        return buscarClienteRec(actual.getSiguiente(), id);
    }

    public static void eliminarClientes(ListaEnlazada<Cliente> clientes) {
        try {
            if (clientes.estaVacia()) {
                JOptionPane.showMessageDialog(null,"No hay clientes por el momento");
                return;
            }
            // mostrar los clientes para escoger el ID
            mostrarClientes(clientes);
            int idAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id de la persona que desea eliminar: "));
            Cliente cliente = buscarCliente(clientes, idAux);
            if (cliente == null) {
                JOptionPane.showMessageDialog(null, "No se encontro la persona");
                return;
            }
            clientes.eliminar(cliente);
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");

        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public static void editarClientes(ListaEnlazada<Cliente> clientes) {
        try {
            if (clientes.estaVacia()) {
                JOptionPane.showMessageDialog(null,"No hay clientes por el momento");
                return;
            }
            // mostrar los clientes para escoger el ID
            mostrarClientes(clientes);
            int idAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id de la persona que desea editar: "));
            Cliente cliente = buscarCliente(clientes, idAux);
            if (cliente == null) {
                JOptionPane.showMessageDialog(null, "No se encontro la persona");
                return;
            }
            cliente.EditarInformacion();
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
}
