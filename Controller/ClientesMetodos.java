package Controller;

import Model.Cliente;

import javax.swing.*;
import java.util.ArrayList;

public class ClientesMetodos {
    public static void mostrarClientes(ArrayList<Cliente> clientes) {
        try {
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No hay clientes por el momento");
                return;

            }
            StringBuilder aux = new StringBuilder();
            aux.append("Clientes: ");
            for (Cliente cliente : clientes) {
                aux.append(cliente.MostrarInformacion());
                aux.append("\n");
            }
            JOptionPane.showMessageDialog(null, aux);
        } catch (Exception E ) {
            JOptionPane.showMessageDialog(null, "Error");
        }

    }
    public static void agregarClientes(ArrayList<Cliente> clientes) {
        try {
            Cliente cliente = new Cliente(clientes.size());
            clientes.add(cliente);
            JOptionPane.showMessageDialog(null, "Cliente agregado correctamente");
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public static void eliminarClientes(ArrayList<Cliente> clientes) {
        boolean control = false;
        try {
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No hay clientes por el momento");
                return;
            }
            // mostrar los clientes para escoger el ID
            mostrarClientes(clientes);
            int idAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id de la persona que desea eliminar: "));
            for(Cliente cliente : clientes) {
                if (cliente.getId() == idAux) {
                    clientes.remove(cliente);
                }
                break;
            }
            if (!control) {
                JOptionPane.showMessageDialog(null, "No se encontro la persona");
            }

        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public static void editarClientes(ArrayList<Cliente> clientes) {
        boolean control = false;
        try {
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No hay clientes por el momento");
                return;
            }
            // mostrar los clientes para escoger el ID
            mostrarClientes(clientes);
            int idAux = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id de la persona que desea editar: "));
            for(Cliente cliente : clientes) {
                if (cliente.getId() == idAux) {
                    cliente.EditarInformacion();
                    control = true;
                }
                break;
            }
            if (!control) {
                JOptionPane.showMessageDialog(null, "No se encontro la persona");
            }
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
}
