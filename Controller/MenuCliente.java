package Controller;

import Model.Cliente;

import javax.swing.*;
import java.util.ArrayList;

public class MenuCliente {
     public  static void menuCliente(ArrayList<Cliente> clientes) {
         Boolean condicion = true;
         while (condicion) {
             int opcion = Integer.parseInt(JOptionPane.showInputDialog("Menu clientes: \n1.agregar clientes \n2.mostrar clientes \n3.editar clientes" +
                     "\n4. eliminar clientes \n5.Volver al menu principal "));
             switch (opcion) {
                 case 1:
                     ClientesMetodos.agregarClientes(clientes);
                     break;
                 case 2:
                     ClientesMetodos.mostrarClientes(clientes);
                     break;
                 case 3:
                     ClientesMetodos.editarClientes(clientes);
                     break;
                 case 4:
                     ClientesMetodos.eliminarClientes(clientes);
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
