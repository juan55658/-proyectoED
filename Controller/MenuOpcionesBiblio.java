package Controller;

import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import java.util.ArrayList;

public class MenuOpcionesBiblio {
    public static void MenuOpcionesBiblioteca(ArrayList<Cliente> clientes, ArrayList<Libro> libros) {
        Boolean condicion = true;
        while (condicion) {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog("--- Menu opciones biblioteca --- \n1.alquilar \n2.devolver \n3.organizar por isbn \n4. volver \nEliga una opcion: "));
            switch (opcion) {
                case 1:
                    Biblioteca.alquilar(clientes, libros);
                    break;
                case 2:
                    Biblioteca.devolver(libros);
                    break;
                case 3:
                    Biblioteca.organizarIsbn(libros);
                    break;
                case 4:
                    condicion = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion incorrecta.");
                    break;
            }
        }
    }
}