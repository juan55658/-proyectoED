package Controller;

import Estructuras.ListaEnlazada;
import Model.Cliente;

import javax.swing.*;

public class MenuClienteV1 {
     public  static void menuCliente(ListaEnlazada<Cliente> clientes) {
         Boolean condicion = true;
         while (condicion) {
             int opcion = Integer.parseInt(JOptionPane.showInputDialog("Menu clientes: \n1.agregar clientes \n2.mostrar clientes \n3.editar clientes" +
                     "\n4. eliminar clientes \n5.Volver al menu principal "));
             switch (opcion) {
                 case 1:
                     ClientesMetodosV1.agregarClientes(clientes);
                     break;
                 case 2:
                     ClientesMetodosV1.mostrarClientes(clientes);
                     break;
                 case 3:
                     ClientesMetodosV1.editarClientes(clientes);
                     break;
                 case 4:
                     ClientesMetodosV1.eliminarClientes(clientes);
                     break;
                 case 5:
                     condicion = false;
                     break;
                 default:
                     JOptionPane.showMessageDialog(null, "Opción incorrecta.");
             }
         }
     }

}
