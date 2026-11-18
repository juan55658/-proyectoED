package Controller;

import Estructuras.ArbolLibros;
import Estructuras.ListaEnlazada;
import Estructuras.Pila;
import Model.Cliente;

import javax.swing.*;

public class MenuPrincipalV1 {
    public static void menuPrincipal(ListaEnlazada<Cliente> clientes, ArbolLibros libros, Pila<String> historial) {
        Boolean condicion = true;
        while (condicion) {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog("--- MENU PRINCIPAL --- \n1.Personas \n2.Libros \n3. Opciones biblioteca(alquilar,devolver) \n4.Salir \nEliga una opcion: "));
            switch (opcion) {
                case 1:
                    MenuClienteV1.menuCliente(clientes);
                    break;
                case 2:
                    MenuLibrosV1.menuLibros(libros);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null,"Tenga en cuenta que para acceder a las funciones de la biblioteca,necesita haber creado libros y clientes.");
                    MenuOpcionesBiblioV1.MenuOpcionesBiblioteca(clientes, libros, historial);
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
