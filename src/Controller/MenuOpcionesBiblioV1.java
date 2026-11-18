package Controller;

import Estructuras.ArbolLibros;
import Estructuras.ListaEnlazada;
import Estructuras.Pila;
import Model.Cliente;

import javax.swing.*;

public class MenuOpcionesBiblioV1 {
    public static void MenuOpcionesBiblioteca(ListaEnlazada<Cliente> clientes, ArbolLibros libros, Pila<String> historial) {
        Boolean condicion = true;
        while (condicion) {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog("--- Menu opciones biblioteca --- \n1.alquilar \n2.devolver \n3.ver fila de espera de un libro \n4.ver historial de devoluciones \n5.volver \nEliga una opcion: "));
            switch (opcion) {
                case 1:
                    BibliotecaV1.alquilar(clientes, libros);
                    break;
                case 2:
                    BibliotecaV1.devolver(libros, historial);
                    break;
                case 3:
                    FilaEsperaV2.mostrar(libros);
                    break;
                case 4:
                    BibliotecaV1.mostrarHistorial(historial);
                    break;
                case 5:
                    condicion = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion incorrecta.");
                    break;
            }
        }
    }
}
