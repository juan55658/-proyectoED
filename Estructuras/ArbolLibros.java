package Estructuras;

import Model.Libro;

// arbol binario de busqueda, los libros se ordenan por isbn
// (menores a la izquierda, mayores a la derecha)
public class ArbolLibros {
    private NodoArbol raiz;

    public ArbolLibros() {
        raiz = null;
    }

    public boolean estaVacio() { return raiz == null; }

    // devuelve false si ya existe un libro con ese isbn
    public boolean insertar(Libro libro) {
        if (buscar(libro.getIsbn()) != null) {
            return false;
        }
        raiz = insertarRec(raiz, libro);
        return true;
    }

    private NodoArbol insertarRec(NodoArbol actual, Libro libro) {
        if (actual == null) {
            return new NodoArbol(libro);
        }
        if (libro.getIsbn() < actual.getDato().getIsbn()) {
            actual.setIzquierdo(insertarRec(actual.getIzquierdo(), libro));
        } else {
            actual.setDerecho(insertarRec(actual.getDerecho(), libro));
        }
        return actual;
    }

    public Libro buscar(int isbn) {
        return buscarRec(raiz, isbn);
    }

    private Libro buscarRec(NodoArbol actual, int isbn) {
        if (actual == null) {
            return null;
        }
        if (isbn == actual.getDato().getIsbn()) {
            return actual.getDato();
        }
        if (isbn < actual.getDato().getIsbn()) {
            return buscarRec(actual.getIzquierdo(), isbn);
        }
        return buscarRec(actual.getDerecho(), isbn);
    }

    public void eliminar(int isbn) {
        raiz = eliminarRec(raiz, isbn);
    }

    private NodoArbol eliminarRec(NodoArbol actual, int isbn) {
        if (actual == null) {
            return null;
        }
        if (isbn < actual.getDato().getIsbn()) {
            actual.setIzquierdo(eliminarRec(actual.getIzquierdo(), isbn));
        } else if (isbn > actual.getDato().getIsbn()) {
            actual.setDerecho(eliminarRec(actual.getDerecho(), isbn));
        } else {
            // encontramos el nodo, hay 3 casos
            // 1 y 2: sin hijo o con un solo hijo
            if (actual.getIzquierdo() == null) {
                return actual.getDerecho();
            }
            if (actual.getDerecho() == null) {
                return actual.getIzquierdo();
            }
            // 3: dos hijos, lo reemplazamos por el menor del lado derecho
            NodoArbol menor = actual.getDerecho();
            while (menor.getIzquierdo() != null) {
                menor = menor.getIzquierdo();
            }
            actual.setDato(menor.getDato());
            actual.setDerecho(eliminarRec(actual.getDerecho(), menor.getDato().getIsbn()));
        }
        return actual;
    }

    // recorrido inorden: izquierda, nodo, derecha (queda ordenado por isbn)
    public String mostrarEnOrden() {
        StringBuilder texto = new StringBuilder();
        enOrdenRec(raiz, texto);
        return texto.toString();
    }

    private void enOrdenRec(NodoArbol actual, StringBuilder texto) {
        if (actual == null) {
            return;
        }
        enOrdenRec(actual.getIzquierdo(), texto);
        texto.append(actual.getDato().MostrarInformacion()).append("\n");
        enOrdenRec(actual.getDerecho(), texto);
    }

    public int contar() {
        return contarRec(raiz);
    }

    private int contarRec(NodoArbol actual) {
        if (actual == null) {
            return 0;
        }
        return 1 + contarRec(actual.getIzquierdo()) + contarRec(actual.getDerecho());
    }

    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoArbol actual) {
        if (actual == null) {
            return 0;
        }
        return 1 + Math.max(alturaRec(actual.getIzquierdo()), alturaRec(actual.getDerecho()));
    }
}
