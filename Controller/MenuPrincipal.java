package Controller;

import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import java.util.ArrayList;

public class MenuPrincipal {
    public static void menuPrincipal(ArrayList<Cliente> clientes, ArrayList<Libro> libros) {
        Boolean condicion = true;
        while (condicion) {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog("--- Menu --- \n1.Personas \n2.Libros \n3.Salir \nEliga una opcion: "));
            switch (opcion) {
                case 1:
                    MenuCliente.menuCliente(clientes);
                    break;
                case 2:
                    MenuLibros.menuLibros(libros);
                    break;
                case 3:
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
