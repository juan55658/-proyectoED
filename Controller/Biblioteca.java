package Controller;

import Model.Cliente;
import Model.Libro;

import javax.swing.*;
import java.util.ArrayList;

public class Biblioteca {
    ArrayList<Libro> biblioteca;
    ArrayList<Cliente> clientes;


public Biblioteca() {
    biblioteca = new ArrayList<>();
    clientes = new ArrayList<>();

    JOptionPane.showMessageDialog(null, "Bienvenido a la biblioteca c: ");
    MenuPrincipal.menuPrincipal(clientes,biblioteca);
}


}


