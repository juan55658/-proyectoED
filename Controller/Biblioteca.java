package Controller;

import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import java.util.ArrayList;

public class Biblioteca {
    ArrayList<Libro> biblioteca;
    ArrayList<Cliente> clientes;
    Boolean condicion;

public Biblioteca() {
    biblioteca = new ArrayList<>();
    clientes = new ArrayList<>();
    condicion = true;

    JOptionPane.showMessageDialog(null, "Bienvenido a la biblioteca c: ");
    menuPrincipal();
}


public void menuPrincipal() {
    while (condicion) {
        int opcion = Integer.parseInt(JOptionPane.showInputDialog("--- Menu --- \n1.Personas \n2.Libros \n3.Salir \nEliga una opcion: "));
        switch (opcion) {
            case 1:
                MenuCliente menuCliente = new MenuCliente();
                return;
            case 2:
                MenuLibros menuLibros = new MenuLibros();
                return;
            case 3:
                JOptionPane.showMessageDialog(null, "Vuelva pronto!");
                condicion = false;
            default:
                JOptionPane.showMessageDialog(null, "Opcion incorrecta.");
        }
    }
}

}


