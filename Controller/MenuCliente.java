package Controller;

import javax.swing.*;

public class MenuCliente {
     public MenuCliente() {
         int opcion = Integer.parseInt(JOptionPane.showInputDialog("Menu clientes: \n1.agregar clientes \n2.mostrar clientes \n3.editar clientes" +
                 "\n4. eliminar clientes \n5.Volver al menu principal "));
         switch (opcion) {
             case 1:
                 agregarClientes();
                 return;
             case 2:
                 mostrarClientes();
                 return;
             case 3:
                 editarClientes();
                 return;
             case 4:
                 eliminarClientes();
                 return;
             case 5:
                 return;
             default:
                 JOptionPane.showMessageDialog(null, "Opcion incorrecta.");
         }
     }
    public void mostrarClientes() {

    }
    public void agregarClientes() {

    }
    public void eliminarClientes() {

    }
    public void editarClientes() {

    }

}
