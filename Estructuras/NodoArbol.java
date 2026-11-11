package Estructuras;

import Model.Libro;

public class NodoArbol {
    private Libro dato;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(Libro dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    public Libro getDato() { return dato; }
    public void setDato(Libro dato) { this.dato = dato; }
    public NodoArbol getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoArbol izquierdo) { this.izquierdo = izquierdo; }
    public NodoArbol getDerecho() { return derecho; }
    public void setDerecho(NodoArbol derecho) { this.derecho = derecho; }
}
