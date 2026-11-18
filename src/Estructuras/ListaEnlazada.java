package Estructuras;

import java.util.ArrayList;

public class ListaEnlazada<T> {
    private Nodo<T> primero;
    private int tamano;

    public ListaEnlazada() {
        primero = null;
        tamano = 0;
    }

    // agrega al final de la lista
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (primero == null) {
            primero = nuevo;
        } else {
            Nodo<T> aux = primero;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
        tamano++;
    }

    // elimina el nodo que tenga ese dato, devuelve false si no lo encontro
    public boolean eliminar(T dato) {
        if (primero == null) {
            return false;
        }
        // caso especial: es el primero
        if (primero.getDato().equals(dato)) {
            primero = primero.getSiguiente();
            tamano--;
            return true;
        }
        Nodo<T> aux = primero;
        while (aux.getSiguiente() != null) {
            if (aux.getSiguiente().getDato().equals(dato)) {
                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                tamano--;
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }

    // pasa los datos a un ArrayList para poder mostrarlos facil en la interfaz
    public ArrayList<T> aLista() {
        ArrayList<T> lista = new ArrayList<>();
        Nodo<T> aux = primero;
        while (aux != null) {
            lista.add(aux.getDato());
            aux = aux.getSiguiente();
        }
        return lista;
    }

    public Nodo<T> getPrimero() { return primero; }
    public boolean estaVacia() { return primero == null; }
    public int getTamano() { return tamano; }
}
