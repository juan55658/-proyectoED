package Controller;

import Estructuras.ArbolLibros;

import javax.swing.*;

public class MenuLibros {
    public static void menuLibros(ArbolLibros libros) {
        Boolean condicion = true;
        while (condicion) {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog("Menu libros: \n1.agregar libros \n2.mostrar libros \n3.editar libros" +
                    "\n4. eliminar libros \n5.Estadisticas del arbol \n6.Volver al menu principal"));
            switch (opcion) {
                case 1:
                    LibrosMetodos.agregarLibros(libros);
                    break;
                case 2:
                    LibrosMetodos.mostrarLibros(libros);
                    break;
                case 3:
                    LibrosMetodos.editarLibros(libros);
                    break;
                case 4:
                    LibrosMetodos.eliminarLibros(libros);
                    break;
                case 5:
                    LibrosMetodos.estadisticasArbol(libros);
                    break;
                case 6:
                    condicion = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción incorrecta.");
                    break;
            }
        }
    }

}
