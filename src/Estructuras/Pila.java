package Estructuras;

import java.util.ArrayList;

// pila LIFO: el ultimo que entra es el primero que sale
public class Pila<T> {
    private Nodo<T> tope;
    private int tamano;

    public Pila() {
        tope = null;
        tamano = 0;
    }

    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamano++;
    }

    // saca el de arriba, devuelve null si la pila esta vacia
    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T dato = tope.getDato();
        tope = tope.getSiguiente();
        tamano--;
        return dato;
    }

    public T verTope() {
        if (estaVacia()) {
            return null;
        }
        return tope.getDato();
    }

    // devuelve los datos empezando por el tope, sin sacarlos de la pila
    public ArrayList<T> aLista() {
        ArrayList<T> lista = new ArrayList<>();
        Nodo<T> aux = tope;
        while (aux != null) {
            lista.add(aux.getDato());
            aux = aux.getSiguiente();
        }
        return lista;
    }

    public boolean estaVacia() { return tope == null; }
    public int getTamano() { return tamano; }
}
