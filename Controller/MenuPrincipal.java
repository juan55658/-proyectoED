package Controller;

import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import java.util.ArrayList;

public class MenuPrincipal {
    public static void menuPrincipal(ArrayList<Cliente> clientes, ArrayList<Libro> libros) {
        Boolean condicion = true;
        while (condicion) {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog("--- Menu --- \n1.Personas \n2.Libros \n3. Opciones biblioteca(alquilar,devolver,organizar por isbn) \n4.Salir \nEliga una opcion: "));
            switch (opcion) {
                case 1:
                    MenuCliente.menuCliente(clientes);
                    break;
                case 2:
                    MenuLibros.menuLibros(libros);
                    break;
                case 3:
                    JOptionPane.showConfirmDialog(null,"Tenga en cuenta que para acceder a las funciones de la biblioteca,necesita haber creado libros y clientes.");
                    MenuOpcionesBiblio.MenuOpcionesBiblioteca(clientes,libros);
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Vuelva pronto!");
                    condicion = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion incorrecta.");
                    break;
            }
        }
    }

}
